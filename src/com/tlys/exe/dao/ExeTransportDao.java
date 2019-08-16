package com.tlys.exe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageView;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeTransport;

/**
 * 车辆运输管理Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeTransportDao extends _GenericDao<ExeTransport> {
	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 根据车号、时间段查询货物运输信息
	 * 
	 * @param car_no
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public PageView<ExeTransport> listTransport(final ExeTransportSearchField field, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				query(ca, field);
				ca.addOrder(Order.desc("wb_date"));
				ca.addOrder(Order.asc("shipper_name"));
				// ca.setFetchMode("dicGoods", FetchMode.EAGER);
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * 取得查询运输信息总数
	 * 
	 * @param field
	 * @return
	 */
	public int getListTransportCount(final ExeTransportSearchField field) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				query(ca, field);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 取得运输统计计费载重和实际载重的合计数
	 * 
	 * @param field
	 * @return
	 */
	public double getCap_wgt(final ExeTransportSearchField field) {
		return (Double) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				query(ca, field);
				ca.setProjection(Projections.sum("car_cap_wgt"));
				Object cap_wgt = (Double) ca.uniqueResult();
				// ca.setProjection(Projections.sum("car_actual_wgt"));
				// double actual_wgt = (Double) ca.uniqueResult();
				return cap_wgt == null || cap_wgt.toString().equals("") ? 0 : Double.parseDouble(cap_wgt.toString());
			}
		});

	}

	public double getActual_wgt(final ExeTransportSearchField field) {
		return (Double) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				query(ca, field);
				ca.setProjection(Projections.sum("car_actual_wgt"));
				Object car_actual_wgt = (Double) ca.uniqueResult();
				// ca.setProjection(Projections.sum("car_actual_wgt"));
				// double actual_wgt = (Double) ca.uniqueResult();
				return car_actual_wgt == null || car_actual_wgt.toString().equals("") ? 0 : Double
						.parseDouble(car_actual_wgt.toString());
			}
		});

	}

	/**
	 * 导出excel时查询
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeTransport> exportTransport(final ExeTransportSearchField field) {
		return (List<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				query(ca, field);
				ca.addOrder(Order.desc("wb_date"));
				ca.addOrder(Order.asc("shipper_name"));
				return ca.list();
			}
		});
	}

	public Criteria query(Criteria ca, ExeTransportSearchField field) {
		// 查询类型
		if (null != field.getZbc_flag() && !field.getZbc_flag().equals("")) {
			ca.add(Restrictions.eq("zbc_flag", field.getZbc_flag()));
			if ("1".equals(field.getZbc_flag()))// 如果是自备车，车辆使用企业在用户数据范围内
			{
				ca.add(Restrictions.sqlRestriction("((" + CommUtils.getCorpIds("car_owner_id") + ") or ( "
						+ CommUtils.getCorpIds("car_user_id") + ")  or (" + CommUtils.getCorpIds("con_entpr_id")
						+ " or " + CommUtils.getCorpIds("shipper_entpr_id") + "))"));
				// ca.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_user_id")));
			} else if ("0".equals(field.getZbc_flag())) {// 如果是部属车，收货人或发货人在用户数据范围内
				ca.add(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")), Restrictions
						.sqlRestriction(CommUtils.getCorpIds("shipper_entpr_id"))));
			}
		}
		// 发货人模糊查询
		if (null != field.getShipper_name() && !field.getShipper_name().equals("")) {
			ca.add(Restrictions.like("shipper_name", "%" + field.getShipper_name() + "%"));
		}
		// 发货人企业in查询
		if (null != field.getShipper_entpr_id() && !field.getShipper_entpr_id().equals("")) {
			String[] shipper_entpr_idArr = field.getShipper_entpr_id().split(",");
			ca.add(Restrictions.in("shipper_entpr_id", shipper_entpr_idArr));
		}
		// 收货人模糊查询
		if (null != field.getCon_name() && !field.getCon_name().equals("")) {
			ca.add(Restrictions.like("con_name", "%" + field.getCon_name() + "%"));
		}
		// 收货人企业in查询
		if (null != field.getCon_entpr_id() && !field.getCon_entpr_id().equals("")) {
			String[] con_entpr_idArr = field.getCon_entpr_id().split(",");
			ca.add(Restrictions.in("con_entpr_id", con_entpr_idArr));
		}
		// 始发站模糊查询
		if (null != field.getCdy_org_stn() && !field.getCdy_org_stn().equals("")) {
			ca.add(Restrictions.like("cdy_o_stn_name", "%" + field.getCdy_org_stn() + "%"));
		}
		// 终到站模糊查询
		if (null != field.getDest_stn() && !field.getDest_stn().equals("")) {
			ca.add(Restrictions.like("dest_stn_name", "%" + field.getDest_stn() + "%"));
		}

		// 产品类别in查询
		if (null != field.getGoods_type() && !field.getGoods_type().equals("")) {
			String[] goods_typeArr = field.getGoods_type().split(",");
			ca.add(Restrictions.in("goods_type", goods_typeArr));
		}
		// 货物品名in查询
		if (null != field.getCdy_code() && !field.getCdy_code().equals("")) {
			String[] cdy_codeArr = field.getCdy_code().split(",");
			ca.add(Restrictions.in("cdy_code", cdy_codeArr));
		}
		// 车号,可以是多车号，也可以是连续车号段，比如9999991,9999992 9999991-9999995
		if (null != field.getCar_no() && !field.getCar_no().equals("")) {
			if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.eq("car_no", field.getCar_no()));
			else if (field.getCar_no().indexOf(",") > -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.in("car_no", field.getCar_no().split(",")));
			else if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") > -1) {
				ca.add(Restrictions.between("car_no", field.getCar_no().substring(0, field.getCar_no().indexOf("-")),
						field.getCar_no().substring(field.getCar_no().indexOf("-") + 1)));
			}
		}

		// 制票时间段

		if (null != field.getWb_date_s() && !field.getWb_date_s().equals("")) {
			ca.add(Restrictions.between("wb_date_str", field.getWb_date_s(), field.getWb_date_e()));
		}

		// 信息来源in查询
		if (null != field.getMsg_type() && !field.getMsg_type().equals("")) {
			String[] msg_typeArr = field.getMsg_type().split(",");
			ca.add(Restrictions.in("msg_type", msg_typeArr));
		}
		return ca;
	}

	/**
	 * 根据货票号取得货票信息
	 * 
	 * @param wb_nbr
	 * @return
	 */
	public ExeTransport getExtTransport(Long rec_id) {
		return (ExeTransport) this.load(rec_id);
	}

	/**
	 * 人工增加或修改货物运输信息，包括补录货物实际载重量
	 * 
	 * @param exeTransport
	 */
	public void saveExeTransport(ExeTransport exeTransport) {
		this.saveOrUpdate(exeTransport);
	}

	public void updateExeTransport(ExeTransport exeTransport) {
		getHibernateTemplate().update(exeTransport);
	}

	/**
	 * 人工删除货物运输信息
	 * 
	 * @param wb_nbr
	 */
	public void deleteExeTransport(Long rec_id) {
		this.delete(getExtTransport(rec_id));
	}

	/**
	 * 按照货票日期、发站代码、货票号查运输信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeTransport> queryExeTransport(final Date wb_date, final String cdy_org_stn, final String wb_nbr) {
		return (List<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				ca.add(Restrictions.eq("cdy_org_stn", cdy_org_stn));
				ca.add(Restrictions.eq("wb_nbr", wb_nbr));
				ca.add(Restrictions.sqlRestriction("to_char(wb_date,'yyyy-MM-dd') =?", StringUtil.dateToString(wb_date,
						"yyyy-MM-dd"), Hibernate.STRING));
				return ca.list();
			}
		});
	}

	/**
	 * 根据货票标示wb_id来查询货票信息
	 * 
	 * @param wb_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeTransport> queryExeTransport(final String wb_id) {
		return (List<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				ca.add(Restrictions.eq("wb_id", wb_id));
				return ca.list();
			}
		});

	}

	/**
	 * 按照车号、发站代码、货票号查运输信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ExeTransport queryExeTransport(final String car_no, final String cdy_org_stn, final String wb_nbr) {
		return (ExeTransport) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				ca.add(Restrictions.eq("cdy_org_stn", cdy_org_stn));
				ca.add(Restrictions.eq("wb_nbr", wb_nbr));
				ca.add(Restrictions.eq("car_no", car_no));
				return ca.uniqueResult();
			}
		});

	}

	/**
	 * 按照车号、发站代码、货票日期起址 查运输信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ExeTransport queryExeTransport(final String car_no, final String cdy_org_stn, final String s_date,
			final String e_date) {
		List<ExeTransport> list = (List<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				ca.add(Restrictions.eq("car_no", car_no));
				ca.add(Restrictions.eq("cdy_org_stn", cdy_org_stn));
				ca.add(Restrictions.sqlRestriction("to_char(wb_date,'yyyy-MM-dd') >=?", s_date, Hibernate.STRING));
				ca.add(Restrictions.sqlRestriction("to_char(wb_date,'yyyy-MM-dd') <=?", e_date, Hibernate.STRING));
				ca.addOrder(Order.desc("wb_date"));
				return ca.list();
			}
		});
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 按照车号、始发站、发货日期范围、
	 * 
	 * @param car_no
	 * @param cdy_org_stn
	 * @param wb_nbr
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public ExeTransport queryExeTransport(final String car_no, final String cdy_org_stn, final String wb_nbr,
			final String s_date, final String e_date) {
		List<ExeTransport> list = (List<ExeTransport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeTransport.class);
				ca.add(Restrictions.eq("car_no", car_no));
				ca.add(Restrictions.eq("cdy_org_stn", cdy_org_stn));
				ca.add(Restrictions.between("wb_date_str", s_date, e_date));
				ca.addOrder(Order.desc("wb_date"));
				return ca.list();
			}
		});
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 更新实际运量,同时修改计费载重增改用户ID 计费载重增改时间
	 * 
	 * @return
	 */
	public Integer updateAct_wgt(final String car_no, final String cdy_org_stn, final String wb_nbr,
			final String wb_date, final Double car_actual_wgt) throws Exception {
		final String hqlString = "update ExeTransport set car_actual_wgt=:car_actual_wgt,cap_wgt_editor='LIS',capw_edit_time=sysdate where car_no=:car_no and cdy_org_stn=:cdy_org_stn and wb_nbr=:wb_nbr and to_char(wb_date,'yyyy-mm-dd')=:wb_date";
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				s.beginTransaction();
				Query query = s.createQuery(hqlString);
				query.setDouble("car_actual_wgt", car_actual_wgt);
				query.setString("car_no", car_no);
				query.setString("cdy_org_stn", cdy_org_stn);
				query.setString("wb_nbr", wb_nbr);
				query.setString("wb_date", wb_date);
				int flag = query.executeUpdate();
				s.getTransaction().commit();
				return flag;
			}
		});
	}

	/**
	 * 更新实际运量,同时修改计费载重增改用户ID 计费载重增改时间
	 * 
	 * @return
	 */
	public int updateAct_wgt(Long rec_id, Double car_actual_wgt) {
		int flag = 0;
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("update tb_zbc_exe_transport set car_actual_wgt=?,cap_wgt_editor='LIS',capw_edit_time=sysdate  where rec_id=?");
			pstmt.setDouble(1, car_actual_wgt);
			pstmt.setLong(2, rec_id);
			flag = pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
			logger.error("修改实际载重webservice时修改实际载重数量报错：rec_id=" + rec_id + "&car_actual_wgt=" + car_actual_wgt);
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 给修改实际运量的增加日志
	 * 
	 * @param car_no
	 * @param wb_date
	 * @param cdy_org_stn
	 * @param car_actual_wgt
	 * @param wb_nbr
	 * @param action_time
	 * @param success_flag
	 * @param success_descript
	 *            wb_date_actual 实际货票日期，取到的话
	 */
	public void addWebServiceOperaLog(String car_no, Date wb_date, Date wb_date_actual, String cdy_org_stn,
			Double car_actual_wgt, String wb_nbr, String success_flag, String success_descript, String loginId,
			String note) {
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("insert into tb_zbc_exe_lisinterface(car_no,wb_date,cdy_org_stn,car_actual_wgt,wb_nbr,success_flag,success_descript,loginid,wb_date_actual,note) values(?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,sysdate,?)");
			pstmt.setString(1, car_no);
			pstmt.setString(2, StringUtil.dateToString(wb_date, "yyyy-MM-dd"));
			pstmt.setString(3, cdy_org_stn);
			pstmt.setDouble(4, car_actual_wgt);
			pstmt.setString(5, wb_nbr);
			pstmt.setString(6, success_flag);
			pstmt.setString(7, success_descript);
			pstmt.setString(8, loginId);
			pstmt.setString(9, note);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("修改实际载重WebService时记录日志报错：car_no=" + car_no + "&wb_date="
					+ StringUtil.dateToString(wb_date, "yyyy-MM-dd") + "&cdy_org_stn=" + cdy_org_stn
					+ "&car_actual_wgt=" + car_no + "&wb_nbr=" + wb_nbr + "&success_flag=" + success_flag
					+ "&success_descript=" + success_descript);
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在webservice中新增货物运输信息
	 * 
	 * @param exeTransport
	 * @return
	 */
	public int saveTransportInWebService(ExeTransport exeTransport) {
		int flag = 0;
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("insert into tb_zbc_exe_transport(rec_id,car_no,zbc_flag,le_code,cdy_org_stn,cdy_o_stn_name,org_adm,wb_id,wb_nbr,wb_date,car_actual_wgt,dest_stn,dest_stn_name,dest_adm,con_name,shipper_name,msg_type,msg_status,hp_editor,CAP_WGT_EDITOR,CAPW_EDIT_TIME,wb_date_str)"
							+ " values(seq_tb_zbc_exe_transport.nextval,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,sysdate,?)");
			pstmt.setString(1, exeTransport.getCar_no());
			pstmt.setString(2, exeTransport.getZbc_flag());
			pstmt.setInt(3, 1);
			pstmt.setString(4, exeTransport.getCdy_org_stn());
			pstmt.setString(5, exeTransport.getCdy_o_stn_name());
			pstmt.setString(6, exeTransport.getOrg_adm());
			pstmt.setString(7, exeTransport.getWb_id());
			pstmt.setString(8, exeTransport.getWb_nbr());
			pstmt.setString(9, StringUtil.dateToString(exeTransport.getWb_date(), "yyyy-MM-dd"));
			pstmt.setDouble(10, exeTransport.getCar_actual_wgt());
			pstmt.setString(11, exeTransport.getDest_stn());
			pstmt.setString(12, exeTransport.getDest_stn_name());
			pstmt.setString(13, exeTransport.getDest_adm());
			pstmt.setString(14, exeTransport.getCon_name());
			pstmt.setString(15, exeTransport.getShipper_name());
			pstmt.setString(16, exeTransport.getMsg_type());
			pstmt.setString(17, exeTransport.getMsg_status());
			pstmt.setString(18, "LIS");
			pstmt.setString(19, "LIS");
			pstmt.setString(20, StringUtil.dateToString(exeTransport.getWb_date(), "yyyy-MM-dd"));
			flag = pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("在WebService中新增货物运输信息时报错");
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
