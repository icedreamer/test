package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageView;
import com.tlys.exe.model.ExeDcarStat;

/**
 * ������̬��ѯDao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeDcarStatDao extends _GenericDao<ExeDcarStat> {

	/**
	 * ������̬��ѯ����ѯ����Ϊ-������Ա���������ʹ����ҵ���û����ݷ�Χ�ڣ�����ǲ��������ջ��� �򷢻������û����ݷ�Χ��
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public PageView<ExeDcarStat> listDcarStat(final ExeDcarStatSearchField field, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca = query(ca, field);
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("evt_time"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}
	
	/**
	 * 2015-12-10 ���ӣ� ��������������������
	 * 
	 * ������̬��ѯ����ѯ����Ϊ-������Ա���������ʹ����ҵ���û����ݷ�Χ�ڣ�����ǲ��������ջ��� �򷢻������û����ݷ�Χ��
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public PageView<ExeDcarStat> listDcarStatLx(final ExeDcarStatSearchField field, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca = queryLx(ca, field);
				ca.addOrder(Order.asc("car_no"));
				//ca.addOrder(Order.desc("evt_time"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * ȡ�ó�����̬��ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCount(final ExeDcarStatSearchField field) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca = query(ca, field);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}
	
	/**
	 * 2015-12-10 ��������
	 * ȡ�ó�����̬��ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListDcarStatCountLx(final ExeDcarStatSearchField field) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca = queryLx(ca, field);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}


	/**
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> exportDcarStat(final ExeDcarStatSearchField field) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				query(ca, field);
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("evt_time"));
				return ca.list();
			}
		});
	}
	
	/**
	 * 2015-12-10 ������������������������
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> exportDcarStatLx(final ExeDcarStatSearchField field) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				queryLx(ca, field);
				ca.addOrder(Order.asc("car_no"));
				//ca.addOrder(Order.desc("evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * ������Ԥ�Ƶ����ʶ��
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> exportEstarr() {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions
						.or(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")),
								Restrictions.sqlRestriction(CommUtils.getCorpIds("shipper_entpr_id"))), Restrictions
								.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_owner_id")), Restrictions
										.sqlRestriction(CommUtils.getCorpIds("car_user_id")))));
				ca.add(Restrictions.eq("estarr_flag", "1"));
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * ���ݲ�ѯ�������criteria�ĺϳɡ�
	 * 
	 * @param ca
	 * @param field
	 * @return
	 */
	public Criteria query(Criteria ca, ExeDcarStatSearchField field) {
		// ��ѯ����
		if (null != field.getZbc_flag() && !field.getZbc_flag().equals("")) {
			ca.add(Restrictions.eq("zbc_flag", field.getZbc_flag()));
			if ("1".equals(field.getZbc_flag()))// ������Ա���������ʹ����ҵ���û����ݷ�Χ��
			{
				// ca.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_user_id")));
				// CAR_OWNER_FLAG=1����car_owne_id������Ȩ�� or
				// car_use_flag=1����car_user_id������Ȩ�� or�ջ��˻򷢻��˶�Ӧ��ҵid������Ȩ�޷�Χ��
				// ca.add(Restrictions.sqlRestriction("((car_owner_flag=1 and "
				// + CommUtils.getCorpIds("car_owner_id")
				// + ") or (car_user_flag=1 and " +
				// CommUtils.getCorpIds("car_user_id") + ") or ("
				// + CommUtils.getCorpIds("con_entpr_id") + " or " +
				// CommUtils.getCorpIds("shipper_entpr_id")
				// + "))"));
				ca.add(Restrictions.sqlRestriction("((" + CommUtils.getCorpIds("car_owner_id") + ") or ( "
						+ CommUtils.getCorpIds("car_user_id") + ")  or (" + CommUtils.getCorpIds("con_entpr_id")
						+ " or " + CommUtils.getCorpIds("shipper_entpr_id") + "))"));
			} else if ("0".equals(field.getZbc_flag())) {// ����ǲ��������ջ��˻򷢻������û����ݷ�Χ��
				ca.add(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")), Restrictions
						.sqlRestriction(CommUtils.getCorpIds("shipper_entpr_id"))));
			}
		}
		// /�ջ��ˣ�CON_NAME��ģ����ѯ
		if (null != field.getCon_name() && !field.getCon_name().equals("")) {
			ca.add(Restrictions.like("con_name", "%".concat(field.getCon_name()).concat("%")));
		}
		// �����ˣ�SHIPPER_NAME��ģ����ѯ
		if (null != field.getShipper_name() && !field.getShipper_name().equals("")) {
			ca.add(Restrictions.like("shipper_name", "%".concat(field.getShipper_name()).concat("%")));
		}
		// ʼ��վ�����պ���ģ����ѯ
		if (null != field.getCdy_org_stn() && !field.getCdy_org_stn().equals("")) {
			ca.add(Restrictions.like("cdy_o_stn_name", "%".concat(field.getCdy_org_stn()).concat("%")));
		}
		// �յ�վ�����պ���ģ����ѯ
		if (null != field.getDest_stn() && !field.getDest_stn().equals("")) {
			ca.add(Restrictions.like("dest_stn_name", "%".concat(field.getDest_stn()).concat("%")));
		}

		// ��Ʒ��𣺣���Ϊͳ������ͳ��)��GOODS_TYPE��1��ͳ����0����ͳ��,in��ѯ
		if (null != field.getGoods_type() && !field.getGoods_type().equals("")) {
			String[] goods_typeArr = field.getGoods_type().split(",");
			ca.add(Restrictions.in("goods_type", goods_typeArr));
		}
		// ����,�����Ƕ೵�ţ�Ҳ�������������ŶΣ�����9999991,9999992 9999991-9999995
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
		// ����ʹ����ҵ��CAR_USER_ID
		if (null != field.getCar_user_id() && !field.getCar_user_id().equals("") && field.getZbc_flag() != "0") {
			String[] car_user_idArr = field.getCar_user_id().split(",");
			ca.add(Restrictions.in("car_user_id", car_user_idArr));
		}
		// ���ͣ��б�ѡ��Ĭ��Ϊȫ��)��CAR_TYPE��in��ѯ
		if (null != field.getCar_type() && !field.getCar_type().equals("")) {
			String[] car_typeArr = field.getCar_type().split(",");
			ca.add(Restrictions.in("car_type", car_typeArr));
		}
		// �ؿ�ѡ���б�ѡ��Ĭ��Ϊȫ��)��LE_CODE,in��ѯ
		if (null != field.getLe_code() && !field.getLe_code().equals("")) {
			String[] le_codeArr = field.getLe_code().split(",");
			ca.add(Restrictions.in("le_code", le_codeArr));
		}
		// ����Ʒ�����б�ѡ��Ĭ��Ϊȫ��)��CDY_CODE��in��ѯ
		if (null != field.getCdy_code() && !field.getCdy_code().equals("")) {
			String[] cdy_codeArr = field.getCdy_code().split(",");
			ca.add(Restrictions.in("dicGoods.dm", cdy_codeArr));
		}
		// ��װ���ʣ��б�ѡ��Ĭ��Ϊȫ��)��CAR_MEDIUM_ID��in��ѯ
		if (null != field.getCar_medium_id() && !field.getCar_medium_id().equals("")) {
			String[] car_medium_idArr = field.getCar_medium_id().split(",");
			ca.add(Restrictions.in("car_medium_id", car_medium_idArr));
		}

		// ���ж�car_rent_flag�Ƿ�Ϊ1�����Ϊ1���ж�corpid�Ƿ�Ϊ00000000��������������������ҵ�û���Ȼ����һ�����ѯ��corpid=car_user_flag
		// and CAR_USER_FLAG='1' || corpid=CAR_OWNER_ID and
		// car_owner_flag='1'
		// ca.add(Expression.or(Expression.eq("car_user_flag", "1"),
		// Expression.eq("car", value)));
		// ������CAR_RENT_FLAG,in��ѯ
		if (null != field.getCar_rent_flag() && !field.getCar_rent_flag().equals("")) {
			if (field.getCar_rent_flag().equals("1"))
				ca.add(Restrictions.and(Restrictions.eq("car_rent_flag", "1"), Restrictions.or(Restrictions.and(
						Restrictions.eq("car_user_id", field.getCorpid()), Restrictions.eq("car_user_flag", "1")),
						Restrictions.and(Restrictions.eq("car_owner_id", field.getCorpid()), Restrictions.eq(
								"car_owner_flag", "1")))));
			else {
				ca.add(Restrictions.eq("car_rent_flag", field.getCar_rent_flag()));
			}
		}
		return ca;
	}
	
	/**
	 * 2015-12-10 ��������������������֧��ģ����ѯ
	 * 
	 * ���ݲ�ѯ�������criteria�ĺϳɡ�
	 * 
	 * @param ca
	 * @param field
	 * @return
	 */
	public Criteria queryLx(Criteria ca, ExeDcarStatSearchField field) {
		// ��ѯ����
		if (null != field.getZbc_flag() && !field.getZbc_flag().equals("")) {
			ca.add(Restrictions.eq("zbc_flag", field.getZbc_flag()));
			if ("1".equals(field.getZbc_flag()))// ������Ա���������ʹ����ҵ���û����ݷ�Χ��
			{
				// ca.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("car_user_id")));
				// CAR_OWNER_FLAG=1����car_owne_id������Ȩ�� or
				// car_use_flag=1����car_user_id������Ȩ�� or�ջ��˻򷢻��˶�Ӧ��ҵid������Ȩ�޷�Χ��
				// ca.add(Restrictions.sqlRestriction("((car_owner_flag=1 and "
				// + CommUtils.getCorpIds("car_owner_id")
				// + ") or (car_user_flag=1 and " +
				// CommUtils.getCorpIds("car_user_id") + ") or ("
				// + CommUtils.getCorpIds("con_entpr_id") + " or " +
				// CommUtils.getCorpIds("shipper_entpr_id")
				// + "))"));
				ca.add(Restrictions.sqlRestriction("((" + CommUtils.getCorpIds("car_owner_id") + ") or ( "
						+ CommUtils.getCorpIds("car_user_id") + ")  or (" + CommUtils.getCorpIds("con_entpr_id")
						+ " or " + CommUtils.getCorpIds("shipper_entpr_id") + "))"));
			} else if ("0".equals(field.getZbc_flag())) {// ����ǲ��������ջ��˻򷢻������û����ݷ�Χ��
				ca.add(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")), Restrictions
						.sqlRestriction(CommUtils.getCorpIds("shipper_entpr_id"))));
			}
		}
		// /�ջ��ˣ�CON_NAME��ģ����ѯ
		if (null != field.getCon_name() && !field.getCon_name().equals("")) {
			ca.add(Restrictions.like("con_name", "%".concat(field.getCon_name()).concat("%")));
		}
		// �����ˣ�SHIPPER_NAME��ģ����ѯ
		if (null != field.getShipper_name() && !field.getShipper_name().equals("")) {
			ca.add(Restrictions.like("shipper_name", "%".concat(field.getShipper_name()).concat("%")));
		}
		// ʼ��վ�����պ���ģ����ѯ
		if (null != field.getCdy_org_stn() && !field.getCdy_org_stn().equals("")) {
			ca.add(Restrictions.like("cdy_o_stn_name", "%".concat(field.getCdy_org_stn()).concat("%")));
		}
		// �յ�վ�����պ���ģ����ѯ
		if (null != field.getDest_stn() && !field.getDest_stn().equals("")) {
			ca.add(Restrictions.like("dest_stn_name", "%".concat(field.getDest_stn()).concat("%")));
		}

		// ��Ʒ��𣺣���Ϊͳ������ͳ��)��GOODS_TYPE��1��ͳ����0����ͳ��,in��ѯ
		if (null != field.getGoods_type() && !field.getGoods_type().equals("")) {
			String[] goods_typeArr = field.getGoods_type().split(",");
			ca.add(Restrictions.in("goods_type", goods_typeArr));
		}
		// ����,�����Ƕ೵�ţ�Ҳ�������������ŶΣ�����9999991,9999992 9999991-9999995
		if (null != field.getCar_no() && !field.getCar_no().equals("")) {
			if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.like("car_no", "%".concat(field.getCar_no()).concat("%")));
			else if (field.getCar_no().indexOf(",") > -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.in("car_no", field.getCar_no().split(",")));
			else if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") > -1) {
				ca.add(Restrictions.between("car_no", field.getCar_no().substring(0, field.getCar_no().indexOf("-")),
						field.getCar_no().substring(field.getCar_no().indexOf("-") + 1)));
			}
		}
		// ����ʹ����ҵ��CAR_USER_ID
		if (null != field.getCar_user_id() && !field.getCar_user_id().equals("") && field.getZbc_flag() != "0") {
			String[] car_user_idArr = field.getCar_user_id().split(",");
			ca.add(Restrictions.in("car_user_id", car_user_idArr));
		}
		// ���ͣ��б�ѡ��Ĭ��Ϊȫ��)��CAR_TYPE��in��ѯ
		if (null != field.getCar_type() && !field.getCar_type().equals("")) {
			String[] car_typeArr = field.getCar_type().split(",");
			ca.add(Restrictions.in("car_type", car_typeArr));
		}
		// �ؿ�ѡ���б�ѡ��Ĭ��Ϊȫ��)��LE_CODE,in��ѯ
		if (null != field.getLe_code() && !field.getLe_code().equals("")) {
			String[] le_codeArr = field.getLe_code().split(",");
			ca.add(Restrictions.in("le_code", le_codeArr));
		}
		// ����Ʒ�����б�ѡ��Ĭ��Ϊȫ��)��CDY_CODE��in��ѯ
		if (null != field.getCdy_code() && !field.getCdy_code().equals("")) {
			String[] cdy_codeArr = field.getCdy_code().split(",");
			ca.add(Restrictions.in("dicGoods.dm", cdy_codeArr));
		}
		// ��װ���ʣ��б�ѡ��Ĭ��Ϊȫ��)��CAR_MEDIUM_ID��in��ѯ
		if (null != field.getCar_medium_id() && !field.getCar_medium_id().equals("")) {
			String[] car_medium_idArr = field.getCar_medium_id().split(",");
			ca.add(Restrictions.in("car_medium_id", car_medium_idArr));
		}

		// ���ж�car_rent_flag�Ƿ�Ϊ1�����Ϊ1���ж�corpid�Ƿ�Ϊ00000000��������������������ҵ�û���Ȼ����һ�����ѯ��corpid=car_user_flag
		// and CAR_USER_FLAG='1' || corpid=CAR_OWNER_ID and
		// car_owner_flag='1'
		// ca.add(Expression.or(Expression.eq("car_user_flag", "1"),
		// Expression.eq("car", value)));
		// ������CAR_RENT_FLAG,in��ѯ
		if (null != field.getCar_rent_flag() && !field.getCar_rent_flag().equals("")) {
			if (field.getCar_rent_flag().equals("1"))
				ca.add(Restrictions.and(Restrictions.eq("car_rent_flag", "1"), Restrictions.or(Restrictions.and(
						Restrictions.eq("car_user_id", field.getCorpid()), Restrictions.eq("car_user_flag", "1")),
						Restrictions.and(Restrictions.eq("car_owner_id", field.getCorpid()), Restrictions.eq(
								"car_owner_flag", "1")))));
			else {
				ca.add(Restrictions.eq("car_rent_flag", field.getCar_rent_flag()));
			}
		}
		return ca;
	}

	/**
	 * ���ݳ��Ų�ѯ������ŵ���Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeDcarStat loadExeDcarStat(String car_no) {
		return load(car_no);
	}

	/**
	 * webservice���ã����ݳ��Ų�ѯʱ�����Ӳ�ѯ��������������ǹ�������/�����˶�Ӧ��ҵID�ֶε�ֵ������һ����Ϊ�ա��������ҵ�Ա��������������ơ�and
	 * ((zbc_flag=��0�� and (CON_ENTPR_ID is not null or SHIPPER_ENTPR_ID is not
	 * null)) or (zbc_flag=��1��))
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeDcarStat loadExeDcarStat2(final String car_no) {
		return (ExeDcarStat) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions.eq("car_no", car_no));
				ca
						.add(Restrictions
								.sqlRestriction("((zbc_flag='0' and (con_entpr_id is not null or shipper_entpr_id is not null)) or (zbc_flag='1'))"));
				return ca.uniqueResult();
			}
		});
	}

	/**
	 * ���ص�ǰ�û���Ԥ�Ƶ����¼�ļ�ʱͳ�������鶯̬������ESTARR_FLAGΪ1������
	 * 
	 */
	public Integer getEstarr_flagCount() {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				queryEstarr(ca);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * ȡ��������Ԥ�Ƶ���ʱ�����Ϣ
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageView<ExeDcarStat> getEstarr_flagRec(final String pageUrl, final int totalRecord, final int currentPage,
			final int pageSize) {
		return (PageView<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				queryEstarr(ca);
				ca.addOrder(Order.desc("evt_time"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * �����ջ���������ҵID���߳���ʹ����ҵID������,con_entpr_id or shipper_entpr_id or car_owner_id
	 * or car_user_id
	 * 
	 * @param ca
	 * @return
	 */
	public Criteria queryEstarr(Criteria ca) {
		// ca.add(Restrictions.eq("zbc_flag", "1"));
		// ca.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")));
		ca.add(Restrictions.or(Restrictions.or(Restrictions.sqlRestriction(CommUtils.getCorpIds("con_entpr_id")),
				Restrictions.sqlRestriction(CommUtils.getCorpIds("shipper_entpr_id"))), Restrictions.or(Restrictions
				.sqlRestriction(CommUtils.getCorpIds("car_owner_id")), Restrictions.sqlRestriction(CommUtils
				.getCorpIds("car_user_id")))));
		ca.add(Restrictions.eq("estarr_flag", "1"));
		return ca;
	}

	/**
	 * ����ֲ���ѯ������ϸ��Ϣ,
	 * 
	 * @param flag,�������ѯ���ǹ�˾��ѯ��AΪ����CΪ��˾������Ļ�car_user_id��Ӧ���ݿ���USER_AREA_ID����˾�Ļ�
	 *            car_user_id��Ӧ���ݿ���CAR_USER_ID
	 * @param car_user_id
	 * @param le_code
	 *            1�س���0�ճ���2δ֪��3ȫ��
	 * @param cur_adm����ǰ·��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> getAreaDistDetail(final String flag, final String area_id, final String car_user_id,
			final String le_code, final String cur_adm) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions.eq("zbc_flag", "1"));
				if (flag.equals("A")) {
					if (!area_id.equals("00000000"))
						ca.add(Restrictions.eq("user_area_id", area_id));
				}
				if (flag.equals("C")) {
					if (!car_user_id.equals("00000000"))
						ca.add(Restrictions.eq("car_user_id", car_user_id));
				}
				if (!le_code.equals("3"))
					ca.add(Restrictions.eq("le_code", le_code));
				if (!cur_adm.equals("ALL"))
					ca.add(Restrictions.eq("cur_adm", cur_adm));
				ca.addOrder(Order.asc("car_no"));
				return ca.list();
			}
		});
	}

	/**
	 * ��ʱԤ��ȡ����ϸ��Ϣ
	 * 
	 * @param medium_id��װ����
	 * @param entpr_idʹ����ҵ
	 * @param s_date��ʼ����
	 * @param e_date��ֹ����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> getOverdueDetail(final String medium_id, final String entpr_id, final int s_date,
			final int e_date) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions.eq("zbc_flag", "1"));
				if (!"".equals(medium_id) && null != medium_id)
					ca.add(Restrictions.eq("car_medium_id", medium_id));
				ca.add(Restrictions.eq("car_user_id", entpr_id));
				// if (!"".equals(s_date))
				// ca.add(Restrictions
				// .sqlRestriction("to_char(evt_time,'yyyy-MM-dd') >= ? ",
				// s_date,
				// Hibernate.STRING));
				// ca.add(Restrictions.sqlRestriction("to_char(evt_time,'yyyy-MM-dd')
				// <= ? ",
				// e_date, Hibernate.STRING));
				String car_on_trainArr[] = new String[] { "0", "1" };
				ca.add(Restrictions.in("car_on_train", car_on_trainArr));
				ca.add(Restrictions.sqlRestriction("(sysdate - evt_time)*24>=?", s_date, Hibernate.INTEGER));
				ca.add(Restrictions.sqlRestriction("(sysdate - evt_time)*24<=?", e_date, Hibernate.INTEGER));
				return ca.list();
			}
		});
	}

	/**
	 * վ���ֳ��ֲ���ϸ
	 * 
	 * @param stn_codeվ�����룬zbc_flag�Ա���������
	 * @param pageUrl
	 * @param totalRecord
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> zqxcDistDetail(final String stn_code, final String zbc_flag) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions.eq("rpt_stn_code", stn_code));
				if (zbc_flag.equals("Q"))
					ca.add(Restrictions.eq("zbc_flag", "1"));
				else if (zbc_flag.equals("T"))
					ca.add(Restrictions.eq("zbc_flag", "0"));
				ca.add(Restrictions.eq("car_on_train", "2"));
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * ר����ͣ��վ��ϸ
	 * 
	 * @param medium_id
	 * @param entpr_id
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeDcarStat> getZyxDetail(final String entpr_id, final String s_date, final String e_date) {
		return (List<ExeDcarStat>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeDcarStat.class);
				ca.add(Restrictions.eq("zbc_flag", "1"));
				ca.add(Restrictions.eq("car_user_id", entpr_id));
				ca.add(Restrictions.sqlRestriction("to_char(evt_time,'yyyy-MM-dd') >= ? ", s_date, Hibernate.STRING));
				ca.add(Restrictions.sqlRestriction("to_char(evt_time,'yyyy-MM-dd') <= ? ", e_date, Hibernate.STRING));
				return ca.list();
			}
		});
	}
	
	/**
	 * ���Ա���̨�˱༭�Ա�����Ϣ���"�Ƿ񱨷�"��ѡ��"��"ɾ��tb_zbc_exe_dcarstat�ñ��Ӧ���ŵ���Ϣ��
	 * @param carNo
	 */
	
	public void delZbcExeDcarstatInfo(final String carNo){
		  final String sql = "delete from ExeDcarStat where car_no = ? ";
		  getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(sql);
				System.out.print("=========delZbcExeDcarstatInfo============sql:"+sql);
				query.setString(0, carNo);
				return query.executeUpdate();
			}
		});
	}
}
