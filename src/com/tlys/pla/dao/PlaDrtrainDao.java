package com.tlys.pla.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaDrtrain;

@Repository
public class PlaDrtrainDao extends _GenericDao<PlaDrtrain> {

	public void findPlaDrtrain(final PlaDrtrain plaDrtrain, final PageCtr<PlaDrtrain> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(PlaDrtrain.class);
				buildCriteria(plaDrtrain, criteria);
				criteria.addOrder(Order.desc("indbtime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public List<PlaDrtrain> findPlaDrtrain(final PlaDrtrain plaDrtrain) {
		return (List<PlaDrtrain>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(PlaDrtrain.class);
				buildCriteria(plaDrtrain, criteria);
				criteria.addOrder(Order.desc("indbtime"));

				return criteria.list();
			}
		});
	}

	public int getPlaDrtrainCount(final PlaDrtrain plaDrtrain) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(PlaDrtrain.class);
				buildCriteria(plaDrtrain, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				return null == o ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	private void buildCriteria(PlaDrtrain plaDrtrain, Criteria criteria) {
		if (null == plaDrtrain) {
			return;
		}
		String month = plaDrtrain.getMonth();
		String loaddate = plaDrtrain.getLoaddate();
		String acceptcarno = plaDrtrain.getAcceptcarno();
		String checkstatus = plaDrtrain.getCheckstatus();
		String corpids = plaDrtrain.getCorpids();
		String startstationid = plaDrtrain.getStartstationid();
		String endstationid = plaDrtrain.getEndstationid();
		String sendername = plaDrtrain.getSendername();
		String receivername = plaDrtrain.getReceivername();
		String rwkindid = plaDrtrain.getRwkindid();
		String rwkindname = plaDrtrain.getRwkindname();
		String kind = plaDrtrain.getKind();
		String kinds = plaDrtrain.getKinds();
		String checkstatusids = plaDrtrain.getCheckstatusids();
		if (null != month && !"".equals(month)) {
			criteria.add(Restrictions.eq("month", month));
		}
		if (corpids != null && !corpids.equals("")) {
			criteria.add(Restrictions.in("corpid", corpids.split(",")));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}

		if (loaddate != null && !loaddate.equals("")) {
			criteria.add(Restrictions.eq("loaddate", loaddate));
		}

		if (null != acceptcarno && !"".equals(acceptcarno)) {
			criteria.add(Restrictions.eq("acceptcarno", acceptcarno));
		}
		if (null != checkstatus && !"".equals(checkstatus)) {
			criteria.add(Restrictions.eq("checkstatus", checkstatus));
		}
		if (null != startstationid && !"".equals(startstationid)) {
			criteria.add(Restrictions.eq("startstationid", startstationid));
		}
		if (null != endstationid && !"".equals(endstationid)) {
			criteria.add(Restrictions.eq("endstationid", endstationid));
		}
		if (null != sendername && !"".equals(sendername)) {
			criteria.add(Restrictions.like("sendername", "%" + sendername + "%"));
		}
		if(null != receivername && !"".equals(receivername)){
			criteria.add(Restrictions.like("receivername", "%"+receivername+"%"));
		}
		if (null != rwkindid && !"".equals(rwkindid)) {
			criteria.add(Restrictions.eq("rwkindid", rwkindid));
		}
		if (null != rwkindname && !"".equals(rwkindname)) {
			criteria.add(Restrictions.like("rwkindname", "%" + rwkindname + "%"));
		}
		if (null != kind && !"".equals(kind)) {
			criteria.add(Restrictions.eq("kind", kind));
		}
		if (null != checkstatusids && checkstatusids.length() > 0) {
			criteria.add(Restrictions.in("checkstatus", checkstatusids.split(",")));
		}
		if (null != kinds && !"".equals(kinds)) {
			criteria.add(Restrictions.in("kind", kinds.split(",")));
		}
	}

	public Object[] call_P_ZBC_PLAN_GENDRTRAIN(final String month) {
		return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] o = new Object[2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
					String procedure = "{call P_ZBC_PLAN_GENDRTRAIN(?,?,?)}";
					cstmt = conn.prepareCall(procedure);

					cstmt.setString(1, month);
					cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
					cstmt.execute();
					o[0] = cstmt.getInt(2);// 返回值代码
					o[1] = cstmt.getString(3);// 返回值描述
					return o;
				} catch (Exception e) {
					logger.error("call P_INIT_MCARCOST error.", e);
					return null;
				} finally {
					try {
						if (cstmt != null) {
							cstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (Exception e) {

					}
				}
			}
		});
	}

	public boolean hasGen(final String month) {
		return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(PlaDrtrain.class);
				criteria.add(Restrictions.eq("month", month));
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (o != null && Integer.parseInt(o.toString()) > 0) {
					return true;
				}
				return false;
			}
		});
	}
}
