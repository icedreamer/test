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
 * ������ϢDao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeAllocationDao extends _GenericDao<ExeAllocation> {

	/**
	 * ������¼
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
	 * ���¼�¼
	 * 
	 * @param object
	 * @param CAR_NO
	 * @param CONTRACT_NO
	 */
	public void updateEntity(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	/**
	 * ���¼�¼
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
	 * �޸ĵ�����Ϣ�ύ״̬
	 * 
	 * @param status
	 * @param modiDate
	 * @param ei
	 */
	public void updateStatus(String status, Date modiDate, ExeAllocationId ei) {
		ExeAllocation ea = (ExeAllocation) this.getHibernateTemplate().get(ExeAllocation.class, ei);

		// ״̬Ϊ�ύʱ�������ύ����
		if ("1".equals(status)) {
			ea.setSubmit_date(modiDate);
		}
		// ���򣬸����޸�����
		else
			ea.setUpdate_date(modiDate);
		ea.setSubmit_status(status);
		this.getHibernateTemplate().update(ea);
	}

	/**
	 * ȡ�õ�����Ϣ
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
	 * ������޷������÷�
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
	 * ȡ�õ�����Ϣ����
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
			// ���ż���ͬ��
			if (null != esf.getCarNo() && !"".equals(esf.getCarNo())) {
				ca.add(Restrictions.eq("id.car_no", esf.getCarNo()));
			}
			if (null != esf.getContractNo() && !"".equals(esf.getContractNo())) {
				ca.add(Restrictions.eq("id.contract_no", esf.getContractNo()));
			}
			// ��װ����
			if (null != esf.getCar_medium_id() && !"".equals(esf.getCar_medium_id())) {
				String[] carMedium_idArr = esf.getCar_medium_id().split(",");
				ca.add(Restrictions.in("goodscategory.goodsid", carMedium_idArr));
			}

			// �����ˣ�CAR_LESSOR �˴�Ϊ���
			if (null != esf.getCarLessor() && !"".equals(esf.getCarLessor())) {
				String[] CAR_LESSORArr = esf.getCarLessor().split(",");
				ca.add(Restrictions.in("car_lessor.corpid", CAR_LESSORArr));

			}
			// �����ˣ�CAR_LESSEE �˴�Ϊ���
			if (null != esf.getCarLessee() && !"".equals(esf.getCarLessee())) {
				String[] CAR_LESSEEArr = esf.getCarLessee().split(",");
				ca.add(Restrictions.in("car_lessee.corpid", CAR_LESSEEArr));
			}

			ca.add(Restrictions.or(Restrictions.sqlRestriction("to_char(rent_Start_Date,'yyyy-MM-dd') >= ? ", esf
					.getRentStartDate(), Hibernate.STRING), Restrictions.sqlRestriction(
					"to_char(rent_End_Date,'yyyy-MM-dd') <= ? ", esf.getRentEndDate(), Hibernate.STRING)));

			// ʱ���
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
			// ��ǰ�û�ֻ�ܲ�ѯ���޷������÷����ڵ�ǰ�û�����Ȩ�������ҵ�б����Ϣ
			ca.add(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_lessor")), Restrictions
					.sqlRestriction(CommUtils.getCorpIds("car_lessee"))));
		}
		return ca;
	}

	/**
	 * ���ݳ��ż���ͬ��ɾ��ʵ��
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
	 * ����Idɾ�������¼
	 * 
	 * @param eai
	 */
	public void delete(ExeAllocationId eai) {
		super.delete(eai);
	}

	/**
	 * ������Ϣͳ�� ���÷������޷��ڵ�ǰ�û�����Ȩ����
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
			voTotal.setLessor("�ϼ�");
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
	 * ������Ϣͳ�ƽ����ѯ��ϸ
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
	 * ȡ�ø����޷�Ϊ���û������к�ͬ��
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllContract_no(String corpId) {
		return getHibernateTemplate().find(
				"select distinct(id.contract_no) from ExeAllocation where car_lessor.corpid=?", corpId);
	}

	/**
	 * ���ݺ�ͬ��ȡ�������ͬ�ŵ�������Ϣ
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
