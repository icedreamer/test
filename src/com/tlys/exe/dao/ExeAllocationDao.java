package com.tlys.exe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.model.ExeAllocation;
import com.tlys.exe.model.ExeAllocationId;

/**
 * 调配信息Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeAllocationDao extends _GenericDao<ExeAllocation> {

	/**
	 * 新增记录
	 * 
	 * @return
	 */
	public void addorUpdAll(List<ExeAllocation> allocList) {
		getHibernateTemplate().saveOrUpdateAll(allocList);
	}

	public void saveExeAllocation(ExeAllocation exeAllocation) {
		this.saveOrUpdate(exeAllocation);
	}

	/**
	 * 更新记录
	 * 
	 * @param object
	 * @param CAR_NO
	 * @param CONTRACT_NO
	 */
	public void updateEntity(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	/**
	 * 更新记录
	 * 
	 * @param CAR_LESSEE
	 * @param TANK_MAINT_DEPT
	 * @param CAR_MAINT_DEPT
	 * @param RENT_START_DATE
	 * @param RENT_END_DATE
	 * @param CONTRACT_NO
	 * @param CREATE_USER
	 * @param ei
	 */

	public void updateEntity(String car_lessee, String tankMaintDept, String carMaintDept, Date rentStartDate,
			Date rentEndDate, String CONTRACT_NO, String updateUser, Date updateDate, ExeAllocationId ei) {
		ExeAllocation ea = (ExeAllocation) this.getHibernateTemplate().get(ExeAllocation.class, ei);
		DicSinocorp dsc = new DicSinocorp();
		dsc.setCorpid(car_lessee);
		ea.setCar_lessee(dsc);
		DicTankrepcorp drp = new DicTankrepcorp();
		drp.setRepcorpid(tankMaintDept);
		ea.setTank_maint_dept(drp);

		DicRwdepartment drp1 = new DicRwdepartment();
		drp1.setRwdepaid(carMaintDept);
		ea.setCar_maint_dept(drp1);

		ea.setRent_start_date(rentStartDate);
		ea.setRent_end_date(rentEndDate);
		ea.setUpdate_date(updateDate);
		ea.setUpdate_user(updateUser);

		ExeAllocationId newEai = new ExeAllocationId(ei.getCar_no(), CONTRACT_NO);
		ea.setId(newEai);
		this.getHibernateTemplate().update(ea);

	}

	/**
	 * 修改调配信息提交状态
	 * 
	 * @param status
	 * @param modiDate
	 * @param ei
	 */
	public void updateStatus(String status, Date modiDate, ExeAllocationId ei) {
		ExeAllocation ea = (ExeAllocation) this.getHibernateTemplate().get(ExeAllocation.class, ei);

		// 状态为提交时，更新提交日期
		if ("1".equals(status)) {
			ea.setSubmit_date(modiDate);
		}
		// 否则，更新修改日期
		else
			ea.setUpdate_date(modiDate);
		ea.setSubmit_status(status);
		this.getHibernateTemplate().update(ea);
	}

	/**
	 * 取得调配信息
	 * 
	 * @param CarNo
	 * @param ContactNo
	 * @return
	 */
	public ExeAllocation findExeAllocation(String car_no, String contract_no) {
		ExeAllocationId eai = new ExeAllocationId(car_no, contract_no);
		return findExeAllocation(eai);

	}

	public ExeAllocation findExeAllocation(ExeAllocationId eai) {
		return (ExeAllocation) this.load(eai);
	}

	public List<ExeAllocation> findExeAllocations() {
		return super.findAll();
	}

	/**
	 * 多个租赁方及租用方
	 * 
	 * @param esf
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public PageView<ExeAllocation> findExeAllocations(final ExeAllocSearchField esf, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeAllocation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAllocation.class);
				query(ca, esf);
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * 取得调配信息总数
	 * 
	 * @param field
	 * @return
	 */
	public int getExeAllocationCount(final ExeAllocSearchField esf) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAllocation.class);
				query(ca, esf);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	public Criteria query(Criteria ca, ExeAllocSearchField esf) {
		if (null != esf) {
			// 车号及合同号
			if (null != esf.getCarNo() && !"".equals(esf.getCarNo())) {
				ca.add(Restrictions.eq("id.car_no", esf.getCarNo()));
			}
			if (null != esf.getContractNo() && !"".equals(esf.getContractNo())) {
				ca.add(Restrictions.eq("id.contract_no", esf.getContractNo()));
			}
			// 充装介质
			if (null != esf.getCar_medium_id() && !"".equals(esf.getCar_medium_id())) {
				String[] carMedium_idArr = esf.getCar_medium_id().split(",");
				ca.add(Restrictions.in("goodscategory.goodsid", carMedium_idArr));
			}

			// 租赁人：CAR_LESSOR 此处为多个
			if (null != esf.getCarLessor() && !"".equals(esf.getCarLessor())) {
				String[] CAR_LESSORArr = esf.getCarLessor().split(",");
				ca.add(Restrictions.in("car_lessor.corpid", CAR_LESSORArr));

			}
			// 租用人：CAR_LESSEE 此处为多个
			if (null != esf.getCarLessee() && !"".equals(esf.getCarLessee())) {
				String[] CAR_LESSEEArr = esf.getCarLessee().split(",");
				ca.add(Restrictions.in("car_lessee.corpid", CAR_LESSEEArr));
			}

			ca.add(Restrictions.or(Restrictions.sqlRestriction("to_char(rent_Start_Date,'yyyy-MM-dd') >= ? ", esf
					.getRentStartDate(), Hibernate.STRING), Restrictions.sqlRestriction(
					"to_char(rent_End_Date,'yyyy-MM-dd') <= ? ", esf.getRentEndDate(), Hibernate.STRING)));

			// 时间段
			// if (null != esf.getRentStartDate() &&
			// !"".equals(esf.getRentStartDate())) {
			// ca.add(Restrictions.sqlRestriction("to_char(rent_Start_Date,'yyyy-MM-dd')
			// >= ? ", esf
			// .getRentStartDate(), Hibernate.STRING));
			// }
			// if (null != esf.getRentEndDate() &&
			// !"".equals(esf.getRentEndDate())) {
			// ca.add(Restrictions.sqlRestriction("to_char(rent_End_Date,'yyyy-MM-dd')
			// <= ? ", esf.getRentEndDate(),
			// Hibernate.STRING));
			// }
			// 当前用户只能查询租赁方或租用方是在当前用户数据权限里的企业列表的信息
			ca.add(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_lessor")), Restrictions
					.sqlRestriction(CommUtils.getCorpIds("car_lessee"))));
		}
		return ca;
	}

	/**
	 * 根据车号及合同号删除实体
	 * 
	 * @param entityids
	 */
	public void delete(String CarNo, String ContractNo) {
		final String hql = "delete from ExeAllocation where id.car_no = '" + CarNo + "' and id.car_no='" + ContractNo
				+ "'";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 根据Id删除调配记录
	 * 
	 * @param eai
	 */
	public void delete(ExeAllocationId eai) {
		super.delete(eai);
	}

	/**
	 * 调配信息统计 租用方或租赁方在当前用户数据权限内
	 * 
	 * @return
	 */
	public List<ExeAllocationStatVO> statisticsAllocation() {
		List<ExeAllocationStatVO> list = new ArrayList<ExeAllocationStatVO>();
		ConnectionProvider cp = null;
		Connection conn = null;
		String statDate = StringUtil.getOptSystemDate(-15);
		try {
			cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
			conn = cp.getConnection();
			String sqlString = "select count(*) num,car_lessor,(select shortname from tb_zbc_dic_sinocorp s where s.corpid=t.car_lessor) car_lessor_name,"
					+ "car_lessee,(select shortname from tb_zbc_dic_sinocorp s where s.corpid=t.car_lessee) car_lessee_name,car_medium_id,"
					+ "(select goodsname from tb_zbc_dic_goodscategory g where g.goodsid=t.car_medium_id) car_medium_name "
					+ "from tb_zbc_exe_allocation t where submit_status=1 and ("
					+ CommUtils.getCorpIds("car_lessee")
					+ " or "
					+ CommUtils.getCorpIds("car_lessor")
					+ ") "
					+ " and to_char(RENT_END_DATE,'yyyy-MM-dd')>? group by car_lessor,car_lessee,car_medium_id order by car_lessor";
			PreparedStatement pstmt = conn.prepareStatement(sqlString);
			pstmt.setString(1, statDate);
			ResultSet rs = pstmt.executeQuery();
			ExeAllocationStatVO voTotal = new ExeAllocationStatVO();
			voTotal.setLessor("合计");
			while (rs.next()) {
				ExeAllocationStatVO vo = new ExeAllocationStatVO();
				vo.setLessor_id(rs.getString("car_lessor"));
				vo.setLessor(rs.getString("car_lessor_name"));
				vo.setLessee_id(rs.getString("car_lessee"));
				vo.setLessee(rs.getString("car_lessee_name"));
				vo.setMedium_id(rs.getString("car_medium_id"));
				vo.setMedium(rs.getString("car_medium_name"));
				vo.setNum(rs.getInt("num"));
				list.add(vo);
				voTotal.setNum(voTotal.getNum() + vo.getNum());
			}
			list.add(voTotal);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 调配信息统计界面查询详细
	 * 
	 * @param lessor_id
	 * @param lessee_id
	 * @param medium_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeAllocation> listAllocationsStatDetail(final String lessor_id, final String lessee_id,
			final String medium_id) {
		return (List<ExeAllocation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAllocation.class);
				ca.add(Restrictions.eq("submit_status", "1"));
				ca.add(Restrictions.eq("car_lessor.corpid", lessor_id));
				ca.add(Restrictions.eq("car_lessee.corpid", lessee_id));
				ca.add(Restrictions.eq("goodscategory.goodsid", medium_id));
				ca.addOrder(Order.asc("id.car_no"));
				return ca.list();
			}
		});
	}

	/**
	 * 取得该租赁方为该用户的所有合同号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllContract_no(String corpId) {
		return getHibernateTemplate().find(
				"select distinct(id.contract_no) from ExeAllocation where car_lessor.corpid=?", corpId);
	}

	/**
	 * 根据合同号取得这个合同号的所有信息
	 * 
	 * @param contract_no
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeAllocation> getAllocationByContract_no(final String contract_no, final String car_no) {
		return (List<ExeAllocation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAllocation.class);
				ca.add(Restrictions.eq("id.contract_no", contract_no));
				ca.add(Restrictions.eq("id.car_no", car_no));
				return ca.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<ExeAllocation> getAllocationByContract_no(final String contract_no) {
		return (List<ExeAllocation>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAllocation.class);
				ca.add(Restrictions.eq("id.contract_no", contract_no));
				return ca.list();
			}
		});
	}
}
