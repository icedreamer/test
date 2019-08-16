package com.tlys.exe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tlys.exe.model.ExeOverdue;
import com.tlys.exe.model.ExeStncar;
import com.tlys.exe.model.ExeStncarId;
import com.tlys.exe.model.ExeTransport;

/**
 * AEI统计Dao
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class ExeAeiDao extends HibernateDaoSupport {

	/**
	 * 取得站区现车分布的数据，list，包含object数组，站区代码、标识（自备车、部署车)、数量
	 * 从车辆动态表里查询CAR_ON_TRAIN=2的数据
	 * 
	 * @return
	 */

	public List<Object[]> getZqxcDist() {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "	select rpt_stn_code, zbc_flag, count(*) cnt  from (select d.car_no, d.zbc_flag, d.rpt_stn_code from TB_ZBC_EXE_DCARSTAT d where d.CAR_ON_TRAIN = '2') group by rpt_stn_code, zbc_flag";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[3];
				obj[0] = rs.getString("rpt_stn_code");
				obj[1] = rs.getString("zbc_flag");
				obj[2] = rs.getInt("cnt");
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 取得站区进出车辆货物信息匹配统计表数据
	 * 
	 * @return
	 */
	public List<Object[]> getZqjcclList(String s_date, String e_date, String zbc_flag) {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = " select match_flag,in_out_flag,stn_name, count(*) cnt from "
				+ "(select c.match_flag, t.in_out_flag, c.stn_name from tb_zbc_exe_stncar c,tb_zbc_exe_stntrn t where c.rptname=t.rptname and t.in_out_flag is not null";
		if (null != s_date && !s_date.equals("")) {
			sql += " and to_char(c.arr_time,'yyyy-MM-dd')>='" + s_date + "'";
		}
		if (null != e_date && !e_date.equals("")) {
			sql += " and to_char(c.arr_time,'yyyy-MM-dd')<='" + e_date + "'";
		}
		if (null != zbc_flag && !zbc_flag.equals("")) {
			if (zbc_flag.equals("1"))// 自备车
				sql += " and c.car_flag='Q'";
			else if (zbc_flag.equals("2"))// 部属车
				sql += " and c.car_flag='T'";
		}
		sql += " ) group by match_flag,in_out_flag,stn_name";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[4];
				obj[0] = rs.getString("stn_name");
				obj[1] = rs.getInt("in_out_flag");
				obj[2] = rs.getInt("match_flag");
				obj[3] = rs.getInt("cnt");
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 站区AEI设备监控
	 * 
	 * @param date
	 * @return
	 */
	public List<Object[]> getEquipMonitor(String date) {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "select count(*) cnt,stn_name from TB_ZBC_EXE_STNMRPT where to_char(in_db_time,'yyyy-MM-dd')=? group by STN_NAME";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getString("stn_name");
				obj[1] = rs.getInt("cnt");
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 取得总运行时间统计表
	 * 
	 * @return
	 */
	public List<Object[]> getZbclylRuntime(String s_date, String e_date) {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "select  a.run_time,b.corpid from (";
		sql += " select sum(run_time) run_time, corpid";
		sql += " from (select car_no,";
		sql += "     run_time,";
		sql += "      (select corpid from tb_zbc_equ_car where  ISEXPIRE=0 and carno = t.car_no) corpid";
		sql += "   from tb_zbc_exe_st_run_time t";
		sql += "  where stat_date between ? and ?)";
		sql += " group by corpid) a,(select corpid from tb_zbc_dic_sinocorp)b where a.corpid(+)=b.corpid";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_date);
			pstmt.setString(2, e_date);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getString("corpid");
				obj[1] = rs.getInt("run_time");
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 取得各个公司的自备车总数
	 * 
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public List<Object[]> getZbclylByCorp() {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "select count(*) cnt,corpid from tb_zbc_equ_car where ISEXPIRE=0 group by corpid";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getString("corpid");
				obj[1] = rs.getInt("cnt");
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String getTableName() {
		String sqlString = "select table_name from dba_tables where owner='HXZBC'";
		String table = "";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sqlString);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				table += rs.getString("table_name") + ",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return table;
	}

	/**
	 * 专用线停靠查询
	 * 
	 * @param entpr_id
	 * @param over_id
	 * @param over_type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeOverdue> listOverdue(final String entpr_id, final String over_id, final String over_type) {
		return (List<ExeOverdue>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeOverdue.class);
				// 超时标识
				if (null != over_type && !over_type.equals("")) {
					ca.add(Restrictions.eq("id.over_type", over_type));
				}
				// 企业id
				if (null != entpr_id && !entpr_id.equals("")) {
					String[] entpr_idArr = entpr_id.split(",");
					ca.add(Restrictions.in("id.entpr_id", entpr_idArr));
				}

				// 超时时间段id
				if (null != over_id && !over_id.equals("")) {
					String[] over_idArr = over_id.split(",");
					ca.add(Restrictions.in("id.over_id", over_idArr));
				}

				ca.add(Restrictions.eq("id.zbc_flag", "1"));
				ca.addOrder(Order.asc("id.entpr_id"));
				ca.addOrder(Order.asc("id.over_id"));
				return ca.list();
			}
		});
	}

	/**
	 * 站区进出车辆货物信息匹配统计表明细
	 * 
	 * @param in_out_flag
	 * @param match
	 * @param stn_code
	 * @param zbc_flag
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeStncar> zqjcclStatDetail(final String in_out_flag, final String match, final String stn_code,
			final String zbc_flag, final String s_date, final String e_date) {
		List<ExeStncar> list = new ArrayList<ExeStncar>();
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		String sql = " select c.*,p.* from tb_zbc_exe_stncar c,tb_zbc_exe_stntrn t,tb_zbc_exe_transport p where  to_char(c.arr_time, 'yyyy-MM-dd') >= ?";
		sql += "  and to_char(c.arr_time, 'yyyy-MM-dd') <= ?";
		sql += " and c.stn_name=? and c.match_flag=? ";
		sql += " and c.rptname=t.rptname and t.in_out_flag is not null";
		sql += " and t.in_out_flag=? and c.wb_id=p.wb_id order by c.arr_time,c.car_position";
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s_date);
			pstmt.setString(2, e_date);
			pstmt.setString(3, stn_code);
			pstmt.setString(4, match);
			pstmt.setString(5, in_out_flag);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ExeStncar car = new ExeStncar();
				car.setTrain_nbr(rs.getString("train_nbr"));
				car.setStn_name(rs.getString("stn_name"));
				car.setArr_time(rs.getTimestamp("arr_time"));
				car.setTrain_dir(rs.getString("train_dir"));
				car.setCar_flag(rs.getString("car_flag"));
				car.setCar_kind(rs.getString("car_kind"));
				car.setCar_type(rs.getString("car_type"));
				car.setCar_no(rs.getString("car_no"));
				car.setCar_length(rs.getInt("car_length"));
				car.setMade_factory(rs.getString("made_factory"));
				car.setUncar_ident(rs.getString("uncar_ident"));
				ExeStncarId id = new ExeStncarId();
				id.setRptname(rs.getString("rptname"));
				id.setCar_position(rs.getInt("car_position"));
				car.setId(id);
				car.setWb_id(rs.getString("wb_id"));
				car.setMatch_flag(rs.getString("match_flag"));
				ExeTransport transport = new ExeTransport();
				transport.setWb_nbr(rs.getString("wb_nbr"));
				transport.setCdy_name(rs.getString("cdy_name"));
				transport.setGoods_type(rs.getString("goods_type"));
				transport.setCar_cap_wgt(rs.getDouble("car_cap_wgt"));
				transport.setCar_actual_wgt(rs.getDouble("car_actual_wgt"));
				transport.setCdy_o_stn_name(rs.getString("cdy_o_stn_name"));
				transport.setDest_stn_name(rs.getString("dest_stn_name"));
				transport.setWb_date(rs.getDate("wb_date"));
				transport.setShipper_name(rs.getString("shipper_name"));
				transport.setCon_name(rs.getString("con_name"));
				car.setTransport(transport);
				list.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
