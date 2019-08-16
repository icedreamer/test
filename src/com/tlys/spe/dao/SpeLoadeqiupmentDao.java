package com.tlys.spe.dao;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpeLoadeqiupment;

@Repository
public class SpeLoadeqiupmentDao extends _GenericDao<SpeLoadeqiupment> {

	public void findSpeLoadeqiupment(final SpeLoadeqiupment speLoadeqiupment, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpeLoadeqiupment.class);
				buildCrit(ca, speLoadeqiupment);
				ca.addOrder(Order.asc("corpname"));
				ca.addOrder(Order.asc("equname"));
				ca.addOrder(Order.desc("createdtime"));
				pageCtr.buildList(ca);
				return null;
			}
		});
	}

	public int getSpeLoadeqiupmentCount(final SpeLoadeqiupment speLoadeqiupment) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(SpeLoadeqiupment.class);
				buildCrit(ca, speLoadeqiupment);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});

	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param crit
	 * @param speLoadeqiupment
	 */
	private void buildCrit(Criteria crit, SpeLoadeqiupment speLoadeqiupment) {
		if (null != speLoadeqiupment) {
			String corpid = speLoadeqiupment.getCorpid();
			String pcategoryname = speLoadeqiupment.getPcategoryname();
			String psecondkindname = speLoadeqiupment.getPsecondkindname();
			String meastypeid = speLoadeqiupment.getMeastypeid();
			String bureauid = speLoadeqiupment.getBureauid();
			String stationid = speLoadeqiupment.getStationid();
			if (null != corpid && !"".equals(corpid)) {
				crit.add(Restrictions.eq("corpid", corpid));
			}
			if (null != pcategoryname && !"".equals(pcategoryname)) {
				crit.add(Restrictions.like("pcategoryname", pcategoryname, MatchMode.ANYWHERE));

			}
			if (null != psecondkindname && !"".equals(psecondkindname)) {
				crit.add(Restrictions.like("psecondkindname", psecondkindname, MatchMode.ANYWHERE));
			}

			if (null != meastypeid && !meastypeid.equals("")) {
				crit.add(Restrictions.eq("meastypeid", meastypeid));
			}

			if (null != bureauid && !bureauid.equals("")) {
				crit.add(Restrictions.eq("bureauid", bureauid));
			}

			if (null != stationid && !stationid.equals("")) {
				crit.add(Restrictions.eq("stationid", stationid));
			}
		}
	}

	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_SPE_LOADEQIUPMENT.nextval from dual";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}
}
