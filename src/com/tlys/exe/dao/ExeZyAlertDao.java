package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageView;
import com.tlys.exe.model.ExeZyAlert;
import com.tlys.exe.model.ExeZyAlertId;

@Repository
public class ExeZyAlertDao extends _GenericDao<ExeZyAlert> {

	/**
	 * ����Ԥ����ѯ����ѯ����Ϊ-������Ա���������ʹ����ҵ���û����ݷ�Χ�ڣ�����ǲ��������ջ��� �򷢻������û����ݷ�Χ��
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public PageView<ExeZyAlert> listZyAlert(final ExeZyAlertSearchField field, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeZyAlert>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeZyAlert.class);
				ca = query(ca, field);
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("id.evt_time"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * ȡ������Ԥ����ѯ����
	 * 
	 * @param field
	 * @return
	 */
	public int getListZyAlertCount(final ExeZyAlertSearchField field) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeZyAlert.class);
				ca = query(ca, field);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
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
	public Criteria query(Criteria ca, ExeZyAlertSearchField field) {
		// Ĭ��ȫ�����Ա���
		ca.add(Restrictions.sqlRestriction("((" + CommUtils.getCorpIds("car_owner_id") + ") or ( "
				+ CommUtils.getCorpIds("car_user_id") + ")  or (" + CommUtils.getCorpIds("con_entpr_id") + " or "
				+ CommUtils.getCorpIds("shipper_entpr_id") + "))"));
		// ��ѯ����
		if (null != field.getOper_flag() && !field.getOper_flag().equals("")) {
			String[] oper_flagArr = field.getOper_flag().split(",");
			ca.add(Restrictions.in("oper_flag", oper_flagArr));
		}

		// ʼ��վ�����պ���ģ����ѯ
		if (null != field.getCdy_org_stn() && !field.getCdy_org_stn().equals("")) {
			ca.add(Restrictions.like("cdy_o_stn_name", "%".concat(field.getCdy_org_stn()).concat("%")));
		}
		// �յ�վ�����պ���ģ����ѯ
		if (null != field.getDest_stn() && !field.getDest_stn().equals("")) {
			ca.add(Restrictions.like("dest_stn_name", "%".concat(field.getDest_stn()).concat("%")));
		}

		// ����,�����Ƕ೵�ţ�Ҳ�������������ŶΣ�����9999991,9999992 9999991-9999995
		if (null != field.getCar_no() && !field.getCar_no().equals("")) {
			if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.eq("id.car_no", field.getCar_no()));
			else if (field.getCar_no().indexOf(",") > -1 && field.getCar_no().indexOf("-") == -1)
				ca.add(Restrictions.in("id.car_no", field.getCar_no().split(",")));
			else if (field.getCar_no().indexOf(",") == -1 && field.getCar_no().indexOf("-") > -1) {
				ca.add(Restrictions.between("id.car_no",
						field.getCar_no().substring(0, field.getCar_no().indexOf("-")), field.getCar_no().substring(
								field.getCar_no().indexOf("-") + 1)));
			}
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

		// Ԥ��ԭ��
		if (null != field.getPbl_reason() && !field.getPbl_reason().equals("")) {
			String[] pbl_reasonArr = field.getPbl_reason().split(",");
			ca.add(Restrictions.in("pbl_reason", pbl_reasonArr));
		}

		if (null != field.getS_date() && !field.getS_date().equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(rpt_time,'yyyy-MM-dd') >= ? ", field.getS_date(),
					Hibernate.STRING));
		}
		if (null != field.getE_date() && !field.getE_date().equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(rpt_time,'yyyy-MM-dd') <= ? ", field.getE_date(),
					Hibernate.STRING));
		}

		// String car_on_trainArr[] = new String[] { "0", "1" };
		// ca.add(Restrictions.in("car_on_train", car_on_trainArr));
		return ca;
	}

	/**
	 * ����excelʱ��ѯ
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeZyAlert> exportZyAlert(final ExeZyAlertSearchField field) {
		return (List<ExeZyAlert>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeZyAlert.class);
				query(ca, field);
				ca.addOrder(Order.asc("car_user_id"));
				ca.addOrder(Order.desc("id.evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * ���ݳ��Ų�ѯ������ŵ���Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeZyAlert loadExeZyAlert(String car_no, Date evt_time) {
		ExeZyAlertId id = new ExeZyAlertId(car_no, evt_time);
		return load(id);
	}
}
