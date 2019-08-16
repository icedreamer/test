package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeGcarEvta;

/**
 * 轨迹查询Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeGcarEvtaDao extends _GenericDao<ExeGcarEvta> {

	/**
	 * 根据车号、时间段查询轨迹信息
	 * 
	 * @param car_no
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public List<ExeGcarEvta> listGcarEvta(final String car_no, final String s_date, final String e_date) {
		return (List<ExeGcarEvta>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeGcarEvta.class);
				// 车号
				if (null != car_no && !car_no.equals("")) {
					ca.add(Restrictions.eq("id.car_no", car_no));
				}
				// 时间段
				// if (null != s_date && !s_date.equals("")) {
				// ca.add(Restrictions
				// .sqlRestriction("to_char(a_d_time,'yyyy-MM-dd') >= ? ",
				// s_date, Hibernate.STRING));
				// }
				// if (null != e_date && !e_date.equals("")) {
				// ca.add(Restrictions
				// .sqlRestriction("to_char(a_d_time,'yyyy-MM-dd') <= ? ",
				// e_date, Hibernate.STRING));
				// }
				if (null != s_date && !s_date.equals("")) {
					ca.add(Restrictions.between("evt_date_str", s_date, e_date));
				}
				ca.addOrder(Order.desc("id.evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * 取得车辆轨迹
	 * 
	 * @param field
	 * @return
	 */
	public int getListGcarEvtaCount(final String car_no, final String s_date, final String e_date) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeGcarEvta.class);
				// 车号
				if (null != car_no && !car_no.equals("")) {
					ca.add(Restrictions.eq("id.car_no", car_no));
				}
				// 时间段
				// if (null != s_date && !s_date.equals("")) {
				// ca.add(Restrictions
				// .sqlRestriction("to_char(a_d_time,'yyyy-MM-dd') >= ? ",
				// s_date, Hibernate.STRING));
				// }
				// if (null != e_date && !e_date.equals("")) {
				// ca.add(Restrictions
				// .sqlRestriction("to_char(a_d_time,'yyyy-MM-dd') <= ? ",
				// e_date, Hibernate.STRING));
				// }
				if (null != s_date && !s_date.equals("")) {
					ca.add(Restrictions.between("evt_date_str", s_date, e_date));
				}
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 根据车号起始日期查询轨迹，不需要分页
	 * 
	 * @param car_no
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public List<ExeGcarEvta> getGcarEvta(final String car_no, final String s_date, final String e_date) {
		return (List<ExeGcarEvta>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeGcarEvta.class);
				// 车号
				if (null != car_no && !car_no.equals("")) {
					ca.add(Restrictions.eq("id.car_no", car_no));
				}
				// 时间段
				// if (null != s_date && !s_date.equals("")) {
				// ca.add(Restrictions.sqlRestriction("to_char(a_d_time,'yyyy-MM-dd
				// hh24:mi:ss') >= ? ", s_date,
				// Hibernate.STRING));
				// }
				// if (null != e_date && !e_date.equals("")) {
				// ca.add(Restrictions.sqlRestriction("to_char(a_d_time,'yyyy-MM-dd
				// hh24:mi:ss') <= ? ", e_date,
				// Hibernate.STRING));
				// }
				if (null != s_date && !s_date.equals("")) {
					ca.add(Restrictions.between("evt_date_str", s_date, e_date));
				}
				ca.addOrder(Order.desc("id.evt_time"));
				return ca.list();
			}
		});
	}

	/**
	 * 根据车号起始日期查询轨迹， 再增加查询限制条件：如果是国铁车收/发货人对应企业ID字段的值至少有一个不为空。如果是企业自备车不做此类限制。and
	 * ((zbc_flag=’0’ and (CON_ENTPR_ID is not null or SHIPPER_ENTPR_ID is not
	 * null)) or (zbc_flag=’1’))
	 * 
	 * @param car_no
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public List<ExeGcarEvta> getGcarEvta2(final String car_no, final String s_date, final String e_date) {
		return (List<ExeGcarEvta>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeGcarEvta.class);
				// 车号
				if (null != car_no && !car_no.equals("")) {
					ca.add(Restrictions.eq("id.car_no", car_no));
				}
				// 时间段
				// if (null != s_date && !s_date.equals("")) {
				// ca.add(Restrictions.sqlRestriction("to_char(a_d_time,'yyyy-MM-dd
				// hh24:mi:ss') >= ? ", s_date,
				// Hibernate.STRING));
				// }
				// if (null != e_date && !e_date.equals("")) {
				// ca.add(Restrictions.sqlRestriction("to_char(a_d_time,'yyyy-MM-dd
				// hh24:mi:ss') <= ? ", e_date,
				// Hibernate.STRING));
				// }

				if (null != s_date && !s_date.equals("")) {
					ca.add(Restrictions.between("evt_date_str", s_date, e_date));
				}
				ca
						.add(Restrictions
								.sqlRestriction("((zbc_flag='0' and (con_entpr_id is not null or shipper_entpr_id is not null)) or (zbc_flag='1'))"));
				ca.addOrder(Order.desc("a_d_time"));
				return ca.list();
			}
		});
	}

	/**
	 * 根据车号查询这个车号的详细信息
	 * 
	 * @param car_no
	 * @return
	 */
	public ExeDcarStat getGcarEvta(String car_no) {
		return (ExeDcarStat) this.getHibernateTemplate().load(ExeDcarStat.class, car_no);
	}
}
