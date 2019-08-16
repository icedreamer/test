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
	 * 运行预警查询，查询类型为-如果是自备车，车辆使用企业在用户数据范围内，如果是部属车，收货人 或发货人在用户数据范围内
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
	 * 取得运行预警查询数量
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
	 * 根据查询条件完成criteria的合成。
	 * 
	 * @param ca
	 * @param field
	 * @return
	 */
	public Criteria query(Criteria ca, ExeZyAlertSearchField field) {
		// 默认全部是自备车
		ca.add(Restrictions.sqlRestriction("((" + CommUtils.getCorpIds("car_owner_id") + ") or ( "
				+ CommUtils.getCorpIds("car_user_id") + ")  or (" + CommUtils.getCorpIds("con_entpr_id") + " or "
				+ CommUtils.getCorpIds("shipper_entpr_id") + "))"));
		// 查询类型
		if (null != field.getOper_flag() && !field.getOper_flag().equals("")) {
			String[] oper_flagArr = field.getOper_flag().split(",");
			ca.add(Restrictions.in("oper_flag", oper_flagArr));
		}

		// 始发站：按照汉字模糊查询
		if (null != field.getCdy_org_stn() && !field.getCdy_org_stn().equals("")) {
			ca.add(Restrictions.like("cdy_o_stn_name", "%".concat(field.getCdy_org_stn()).concat("%")));
		}
		// 终到站：按照汉字模糊查询
		if (null != field.getDest_stn() && !field.getDest_stn().equals("")) {
			ca.add(Restrictions.like("dest_stn_name", "%".concat(field.getDest_stn()).concat("%")));
		}

		// 车号,可以是多车号，也可以是连续车号段，比如9999991,9999992 9999991-9999995
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
		// 货物品名（列表选择，默认为全部)：CDY_CODE，in查询
		if (null != field.getCdy_code() && !field.getCdy_code().equals("")) {
			String[] cdy_codeArr = field.getCdy_code().split(",");
			ca.add(Restrictions.in("dicGoods.dm", cdy_codeArr));
		}
		// 充装介质（列表选择，默认为全部)：CAR_MEDIUM_ID，in查询
		if (null != field.getCar_medium_id() && !field.getCar_medium_id().equals("")) {
			String[] car_medium_idArr = field.getCar_medium_id().split(",");
			ca.add(Restrictions.in("car_medium_id", car_medium_idArr));
		}

		// 预警原因
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
	 * 导出excel时查询
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
	 * 根据车号查询这个车号的信息
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeZyAlert loadExeZyAlert(String car_no, Date evt_time) {
		ExeZyAlertId id = new ExeZyAlertId(car_no, evt_time);
		return load(id);
	}
}
