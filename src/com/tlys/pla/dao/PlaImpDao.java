package com.tlys.pla.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.pla.model.PlaMupstransport;


/**
 * @author sunshanh
 * 
 */
@Repository
public class PlaImpDao extends _GenericDao<PlaMupstransport> {
	
	/**
	 * 执行SQL语句(备份)
	 * @param sql
	 * @return
	 */
	public int executeSql_bak(final String sql) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				int re = s.createSQLQuery(sql).executeUpdate();
				return re;
			}
		});
	}
	/**
	 * 执行SQL语句
	 * @param sql
	 * @return
	 */
	public int executeSql(final String sql) {
		if(null==sql||"".equals(sql)){
			return 0;
		}
		  return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	int doInsertCnt=0;
	            	Connection con = null;
	            	Statement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			cstmt = con.createStatement() ;
	        			cstmt.executeUpdate(sql); 
		        		doInsertCnt=1;
	        		} catch (Exception e) {
	        			log.error("Do ExeSql Error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				log.error("Close sctmt and con Error in Do ExeSql Error.", e);
	        			}
	        		}
	        		return doInsertCnt;
	            }
	        });
	}
	/**
	 * 批量执行SQL语句
	 * @param sql
	 * @return
	 */
	public int executeSqlArray(final String[] sql) {
		if(null==sql||sql.length==0){
			return 0;
		}
		  return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	int doInsertCnt=0;
	            	Connection con = null;
	            	Statement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			 cstmt = con.createStatement() ;
		        		for (int i = 0; i < sql.length; i++) {
		        			if(null!=sql[i]&&!"".equals(sql[i])){
		        				cstmt.addBatch(sql[i]);   
		        			}
						}
		        		cstmt.executeBatch();   
		        		con.commit();  
		        		doInsertCnt=sql.length;
	        		} catch (Exception e) {
	        			log.error("Do Insert Error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				log.error("Close sctmt and con Error in Do Insert Error.", e);
	        			}
	        		}
	        		return doInsertCnt;
	            }
	        });
	}
	/**
	 * 执行SQl语句带有Count的 ,SQL语句
	 * @param sql
	 * @return
	 */
	public int executeCountSql(final String sql) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				BigDecimal re = (BigDecimal)s.createSQLQuery(sql).uniqueResult();
				return re.intValue();
			}
		});
	}
	
	/**
	 * 执行存储过程 P_ZBC_GET_PLANDICDATA
	 */
	public Object[] initPlanDicDataReceiver(final String[] _para) {//customerName,receiverName
		if(null==_para||_para.length<2){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[4];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_GET_PLANDICDATA(?,?,?,?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.setString(2, _para[1]);
	        			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
	        			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
	        			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getString(3);// 返回值描述
	        			o[1] = cstmt.getString(4);// 返回值描述
	        			o[2] = cstmt.getInt(5);//.getObject(5);// 返回值代码
	        			o[3] = cstmt.getString(6);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_GET_PLANDICDATA error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}

	        		return null;
	            	
	            	
//	            	SQLQuery sqlQuery = session.createSQLQuery("{call P_ZBC_GET_PLANDICDATA(?,?,?,?,?,?)}");
//	            	sqlQuery.setString(1,_para[0]);
//	            	sqlQuery.setString(2,_para[1]);
//	            	sqlQuery.executeUpdate();
//	            	List list = sqlQuery.list();
//	            	Object[] obj = (Object[]) list.get(0);
//	                return obj;
	            }
	        });
	} 
	/**
	 * 执行存储过程 P_ZBC_GET_PLANDICDATA
	 */
	public Object[] initPlanDataAtImpComplate(final String[] _para) {//customerName,receiverName
		if(null==_para||_para.length<1){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[2];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_PLAN_GENAMUP_OLDPALNID(?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getInt(2);//.getObject(5);// 返回值代码
	        			o[1] = cstmt.getString(3);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_PLAN_GENAMUP_OLDPALNID error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}

	        		return null;

	            }
	        });
	} 

	/**
	 * 执行存储过程 P_ZBC_PLAN_PADJ_MSPSTRANSPORT
	 * 4个输入参数：企业ID，月份，调整次数，调整人
	 * 预调整计划开始进行计划的调整前，需要进行预调整，
	 * 请调用存储过程P_ZBC_PLAN_PADJ_MSPSTRANSPORT，
	 * 此过程的调用是在用户选定好要导入的文件并且对数据校验都完成，
	 * 且没有写入数据库前
	 */
	public Object[] initPlanPadjMspstransport(final String[] _para) {
		if(null==_para||_para.length<4){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[2];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_PLAN_PADJ_MSPSTRANSPORT(?,?,?,?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.setString(2, _para[1]);
	        			cstmt.setInt(3,Integer.parseInt(_para[2]));
	        			cstmt.setString(4, _para[3]);
	        			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getInt(5);//.getObject(5);// 返回值代码
	        			o[1] = cstmt.getString(6);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_PLAN_PADJ_MSPSTRANSPORT error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}
	        		return null;
	            }
	        });
	}
	/**
	 * 执行存储过程 P_ZBC_PLAN_GEN_MSPSTRANSPORT 
	 * 1个输入参数:计划月份
	 * 导入非统销计划后，调用过程存储过程P_ZBC_PLAN_GEN_MSPSTRANSPORT生成数据，
	 * 该过程会将新增的计划数据和减少的计划数据与原计划合并
	 */
	public Object[] initPlanGenMspstransport(final String[] _para) {
		if(null==_para||_para.length<1){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[2];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_PLAN_GEN_MSPSTRANSPORT(?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getInt(2);//.getObject(5);// 返回值代码
	        			o[1] = cstmt.getString(3);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_PLAN_GEN_MSPSTRANSPORT error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}
	        		return null;
	            }
	        });
	}
	
	/**
	 * 执行存储过程 P_ZBC_PLAN_PADJ_MUPSTRANSPORT 
	 * 4个输入参数：区域ID，月份，调整次数，调整人
	 * 预调整计划开始进行计划的调整前，需要进行预调整，
	 * 请调用存储过程P_ZBC_PLAN_PADJ_MUPSTRANSPORT，
	 * 此过程的调用是在用户选定好要导入的文件并且对数据校验都完成，
	 * 且没有写入数据库前
	 */
	public Object[] initPlanPadjMupstransport(final String[] _para) {
		if(null==_para||_para.length<4){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[2];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_PLAN_PADJ_MUPSTRANSPORT(?,?,?,?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.setString(2, _para[1]);
	        			cstmt.setInt(3,Integer.parseInt(_para[2]));
	        			cstmt.setString(4, _para[3]);
	        			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getInt(5);//.getObject(5);// 返回值代码
	        			o[1] = cstmt.getString(6);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_PLAN_PADJ_MSPSTRANSPORT error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}
	        		return null;
	            }
	        });
	}
	/**
	 * 执行存储过程 P_ZBC_PLAN_GEN_MUPSTRANSPORT 
	 * 1个输入参数:计划月份
	 * 导入非统销计划后，调用过程存储过程P_ZBC_PLAN_GEN_MUPSTRANSPORT生成数据，
	 * 该过程会将新增的计划数据和减少的计划数据与原计划合并
	 */
	public Object[] initPlanGenMupstransport(final String[] _para) {
		if(null==_para||_para.length<1){
			return null;
		}
		  return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session) throws HibernateException, SQLException {
	            	Object o[] = new Object[2];
	            	Connection con = null;
	        		CallableStatement cstmt = null;
	        		try {
	        			con = session.connection();
	        			//con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	        			String procedure = "{call P_ZBC_PLAN_GEN_MUPSTRANSPORT(?,?,?) }";
	        			cstmt = con.prepareCall(procedure);
	        			cstmt.setString(1, _para[0]);
	        			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
	        			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
	        			cstmt.execute();
	        			o[0] = cstmt.getInt(2);//.getObject(5);// 返回值代码
	        			o[1] = cstmt.getString(3);// 返回值描述
	        			return o;
	        		} catch (Exception e) {
	        			log.error("call P_ZBC_PLAN_GEN_MSPSTRANSPORT error.", e);
	        		} finally {
	        			try {
	        				if (cstmt != null) {
	        					cstmt.close();
	        				}
	        				if (null != con) {
								con.close();
							}
	        			} catch (Exception e) {
	        				// TODO: handle exception
	        			}
	        		}
	        		return null;
	            }
	        });
	}
	
	
	
	
	
	/**
	 * 0获取检查是否允许上传语句
	 * @param tbName
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public String checkStatus(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp){
		StringBuffer sb=new StringBuffer();
		if("plaMupstransportdet".equals(tbName)){
			sb.append("select count(*) from TB_ZBC_PLA_MUPSTRANSPORT a  ");
			sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and AREAID='"+currArea+"' ");
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORT
			sb.append("select count(*) from TB_ZBC_PLA_AMUPSTRANSPORT a  ");
			sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and AREAID='"+currArea+"' ");
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORT
			//sb.append("select count(*) from TB_ZBC_PLA_MSPSTRANSPORT a  ");
			//sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and CORPID='"+currCorp+"' ");
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORT
			//sb.append("select count(*) from TB_ZBC_PLA_AMSPSTRANSPORT a  ");
			//sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and CORPID='"+currCorp+"' ");
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORT2
			//sb.append("select count(*) from TB_ZBC_PLA_MSPSTRANSPORT2 a  ");
			//sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and CORPID='"+currCorp+"' ");
		}else if("plaMupstransportdet2".equals(tbName)){//TB_ZBC_PLA_MUPSTRANSPORT2
			//sb.append("select count(*) from TB_ZBC_PLA_MUPSTRANSPORT2 a  ");
			//sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and AREAID='"+currArea+"' ");
		}else if("plaYuprtamount".equals(tbName)){//TB_ZBC_PLA_YUPRTAMOUNT
			//sb.append("");
		}else{
			//sb.append("");
		}
		return sb.toString();
	}
	/**
	 * 1获取主表删除语句
	 * @param tbName
	 * @param _mth
	 * @param _area
	 * @return
	 */
	public String getTBMainDelete(final String tbName,final String _year,final String _mth,final String _area,final String _corp,final String _planno){
		StringBuffer sb=new StringBuffer();
		if("plaMupstransportdet".equals(tbName)){
			sb.append("delete from TB_ZBC_PLA_MUPSTRANSPORT ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMUPSTRANSPORT ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORT ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMSPSTRANSPORT ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORT2 ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaMupstransportdet2".equals(tbName)){
				sb.append("delete from TB_ZBC_PLA_MUPSTRANSPORT2 ");
				sb.append(" where PLANNO='"+_planno+"' ");
				//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaYuprtamount".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET

		}else{
			//sb.append("");
		}
		return sb.toString();
	}
	/**
	 * 2获取明细表删除语句
	 * @param tbName
	 * @param _mth
	 * @param _area
	 * @return
	 */
	public String getTBMxDelete(final String tbName,final String _year,final String _mth,final String _area,final String _corp,final String _planno){
		StringBuffer sb=new StringBuffer();
		if("plaMupstransportdet".equals(tbName)){
			sb.append("delete from TB_ZBC_PLA_MUPSTRANSPORTDET ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMUPSTRANSPORTDET ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORTDET ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMSPSTRANSPORTDET ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORTDET2 ");
			sb.append(" where PLANNO='"+_planno+"' ");
			//sb.append(" where month='"+_mth+"' and corpid='"+_corp+"' ");
		}else if("plaMupstransportdet2".equals(tbName)){
				sb.append("delete from TB_ZBC_PLA_MUPSTRANSPORTDET2 ");
				sb.append(" where PLANNO='"+_planno+"' ");
				//sb.append(" where month='"+_mth+"' and areaid='"+_area+"' ");
		}else if("plaYuprtamount".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_YUPRTAMOUNT ");
			sb.append(" where year='"+_year+"' ");
		}else{
			//sb.append("");
		}
		return sb.toString();
	}
	/**
	 * 3获取主表插入语句
	 * @param tbName
	 * @return
	 */
	public String getTBMainInsert(final String tbName,final String _year,final String _mth,final String _area,final String _corp,final String planNo,final String importerID){
		StringBuffer sb=new StringBuffer();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss"); 
		java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
		String _currTime= formatter.format(currentTime); //将日期时间格式化 
		if("plaMupstransportdet".equals(tbName)){
			sb.append("insert into TB_ZBC_PLA_MUPSTRANSPORT (");
			sb.append("PLANNO,AREAID,MONTH,CREATOR,CREATEDTIME,STATUS");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_area+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(")");		
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORTDET
			sb.append("insert into TB_ZBC_PLA_AMUPSTRANSPORT (");
			sb.append("PLANNO,AREAID,MONTH,CREATOR,CREATEDTIME,STATUS");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_area+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(")");	
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("insert into TB_ZBC_PLA_MSPSTRANSPORT (");
			sb.append("PLANNO,CORPID,MONTH,CREATOR,CREATEDTIME,STATUS");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_corp+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(")");	
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			sb.append("insert into TB_ZBC_PLA_AMSPSTRANSPORT (");
			sb.append("PLANNO,CORPID,MONTH,CREATOR,CREATEDTIME,STATUS");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_corp+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(")");	
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("insert into TB_ZBC_PLA_MSPSTRANSPORT2 (");
			sb.append("PLANNO,CORPID,MONTH,CREATOR,CREATEDTIME,STATUS,ADJUSTER,ADJUSTNUMBER,ADJUSTTIME");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_corp+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(",'"+importerID+"'");
			sb.append(","+planNo.substring(planNo.length()-2,planNo.length())+"");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(")");	
		}else if("plaMupstransportdet2".equals(tbName)){
			sb.append("insert into TB_ZBC_PLA_MUPSTRANSPORT2 (");
			sb.append("PLANNO,AREAID,MONTH,CREATOR,CREATEDTIME,STATUS,ADJUSTER,ADJUSTNUMBER,ADJUSTTIME");
			sb.append(") values (");
			sb.append("'"+planNo+"'");
			sb.append(",'"+_area+"'");
			sb.append(",'"+_mth+"'");
			sb.append(",'"+importerID+"'");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(",'0'");
			sb.append(",'"+importerID+"'");
			sb.append(","+planNo.substring(planNo.length()-2,planNo.length())+"");
			sb.append(",to_date('"+_currTime+"','YYYYMMDD HH24:MI:SS')");
			sb.append(")");	
		}else if("plaYuprtamount".equals(tbName)){//TB_ZBC_PLA_YUPRTAMOUNT

		}else{
			//sb.append("");
		}
		return sb.toString();
	}
	/**
	 * 5获取明细表删除重复数据语句
	 * @return
	 */
	public String getTBMxDeleteMore(final String tbName,final String _year,final String _mth,final String _area,final String _corp,final String _planno){
		StringBuffer sb=new StringBuffer();
		if("plaMupstransportdet".equals(tbName)){
			sb.append("delete from tb_zbc_pla_mupstransportdet a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from tb_zbc_pla_mupstransportdet b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");			
			sb.append(" and b.PLANNO='"+_planno+"' ");
			//sb.append(" and b.month='"+_mth+"' and b.areaID='"+_area+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
			//sb.append(" and a.month='"+_mth+"' and a.areaID='"+_area+"' ");
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMUPSTRANSPORTDET a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from TB_ZBC_PLA_AMUPSTRANSPORTDET b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");
			sb.append(" and b.PLANNO='"+_planno+"' ");
			//sb.append(" and b.month='"+_mth+"' and b.areaID='"+_area+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
			//sb.append(" and a.month='"+_mth+"' and a.areaID='"+_area+"' ");
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORTDET a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from TB_ZBC_PLA_MSPSTRANSPORTDET b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");
			sb.append(" and b.PLANNO='"+_planno+"' ");
			//sb.append(" and b.month='"+_mth+"' and b.corpID='"+_corp+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
			//sb.append(" and a.month='"+_mth+"' and a.corpID='"+_corp+"' ");
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_AMSPSTRANSPORTDET a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from TB_ZBC_PLA_AMSPSTRANSPORTDET b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");
			sb.append(" and b.PLANNO='"+_planno+"' ");
			//sb.append(" and b.month='"+_mth+"' and b.corpID='"+_corp+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
			//sb.append(" and a.month='"+_mth+"' and a.corpID='"+_corp+"' ");
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			sb.append("delete from TB_ZBC_PLA_MSPSTRANSPORTDET2 a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from TB_ZBC_PLA_MSPSTRANSPORTDET2 b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");
			sb.append(" and b.PLANNO='"+_planno+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
		}else if("plaMupstransportdet2".equals(tbName)){
			sb.append("delete from tb_zbc_pla_mupstransportdet2 a  ");
			sb.append(" where rowid !=( ");
			sb.append(" select max(rowid) from tb_zbc_pla_mupstransportdet2 b  ");
			sb.append(" where a.month=b.month  ");
			sb.append(" and a.corpid=b.corpid  ");
			sb.append(" and a.senderid=b.senderid ");
			sb.append(" and a.startstationid=b.startstationid ");
			sb.append(" and a.customerid=b.customerid ");
			sb.append(" and a.receiverid=b.receiverid ");
			sb.append(" and a.endstationid=b.endstationid ");
			sb.append(" and a.productcategoryid=b.productcategoryid ");
			sb.append(" and a.productsecondid=b.productsecondid ");
			sb.append(" and a.carkindid=b.carkindid ");			
			sb.append(" and b.PLANNO='"+_planno+"' ");
			//sb.append(" and b.month='"+_mth+"' and b.areaID='"+_area+"' ");
			sb.append(" ) ");
			sb.append(" and a.PLANNO='"+_planno+"' ");
			//sb.append(" and a.month='"+_mth+"' and a.areaID='"+_area+"' ");
		}else if("plaYuprtamount".equals(tbName)){//TB_ZBC_PLA_YUPRTAMOUNT

		}else{
			//sb.append("");
		}
		return sb.toString();
	}


	/**
	 * 0获取最大调整次数
	 * @param tbName
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public int getMaxAdjustNumber(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp){
		StringBuffer sb=new StringBuffer();
		if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORT2
			sb.append("select nvl(max(ADJUSTNUMBER),0) from TB_ZBC_PLA_MSPSTRANSPORT2 where month='"+currMth+"' and corpid='"+currCorp+"'  ");
		}else if("plaMupstransportdet2".equals(tbName)){//TB_ZBC_PLA_MUPSTRANSPORT2
			sb.append("select nvl(max(ADJUSTNUMBER),0) from TB_ZBC_PLA_MUPSTRANSPORT2 where month='"+currMth+"' and areaid='"+currArea+"'  ");
		}else{
			//sb.append("");
		}
		if(null!=sb&&!"".equals(sb.toString())){
			return executeCountSql(sb.toString());
		}
		return 0;
	}
	/**
	 * 0获取最后一次调整计划，提交状态:0没提交，大于0已经提交
	 * @param tbName
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public int getLastAdjustStauts(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp){
		StringBuffer sb=new StringBuffer();
		int res=0;
		if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORT2
			sb.append("select count(*) from TB_ZBC_PLA_MSPSTRANSPORT2 a  ");
			sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and CORPID='"+currCorp+"' ");
			sb.append(" and ADJUSTNUMBER=(select max(ADJUSTNUMBER) from TB_ZBC_PLA_MSPSTRANSPORT2 where month='"+currMth+"' and corpid='"+currCorp+"')  ");
		}else if("plaMupstransportdet2".equals(tbName)){//TB_ZBC_PLA_MUPSTRANSPORT2
			sb.append("select count(*) from TB_ZBC_PLA_MUPSTRANSPORT2 a  ");
			sb.append(" where STATUS in ('1','3') and MONTH='"+currMth+"' and AREAID='"+currArea+"' ");
			sb.append(" and ADJUSTNUMBER=(select max(ADJUSTNUMBER) from TB_ZBC_PLA_MUPSTRANSPORT2 where month='"+currMth+"' and AREAID='"+currArea+"')  ");
		}else{
			//sb.append("");
		}
		if(null!=sb&&!"".equals(sb.toString())){
			res= executeCountSql(sb.toString());
		}
		return res;
	}
}
