package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpePapers;
import com.tlys.spe.model.SpePaperstype;

@Repository
public class SpePapersDao extends _GenericDao<SpePapers> {

	public void findSpePapers(final SpePapers spePapers, final PageCtr<SpePapers> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpePapers.class);
				buildSpePapersCriteria(spePapers, ca);
				ca.addOrder(Order.desc("createdtime"));
				pageCtr.buildList(ca);
				return null;
			}
		});
	}

	public List<SpePapers> findSpePapers(final SpePapers spePapers) {
		return (List<SpePapers>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SpePapers.class);
				buildSpePapersCriteria(spePapers, criteria);
				return criteria.list();
			}
		});
	}

	public int getSpePapersCount(final SpePapers spePapers) {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(SpePapers.class);
				buildSpePapersCriteria(spePapers, ca);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	public void updateSpePapersPtypeid(final String ptypeid) {
		final String hql = "update SpePapers a set a.ptypeid = '00' where spePaperstype.id = ? ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				Query query = arg0.createQuery(hql);
				query.setString(0, ptypeid);
				return query.executeUpdate();
			}
		});
	}

	public Long getNextSpePapersSeqId() {
		final String sql = "select SEQ_TB_ZBC_SPE_PAPERS.nextval from dual";
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				Object o = query.uniqueResult();
				return o == null || o.toString().equals("") ? 0l : Long.valueOf(o.toString());
			}
		});
	}

	private void buildSpePapersCriteria(SpePapers spePapers, Criteria criteria) {
		final String[] corpids = (null == spePapers ? null : spePapers.getCorpids());
		final String startTime = (null == spePapers ? null : spePapers.getStartTime());
		final String endTime = (null == spePapers ? null : spePapers.getEndTime());
		final String carno = (null == spePapers ? null : spePapers.getCarno());

		String typeid = null;
		if (null != spePapers) {
			SpePaperstype spePaperstype = spePapers.getSpePaperstype();
			if (null != spePaperstype) {
				typeid = spePaperstype.getPtypeid();
			}
		}

		if (null != typeid && !"".equals(typeid)) {
			criteria.add(Restrictions.eq("spePaperstype.ptypeid", typeid));
		}
		if (null != carno && !"".equals(carno)) {
			criteria.add(Restrictions.like("carno", "%" + carno + "%"));
		}
		String papername = (null == spePapers ? null : spePapers.getPapersname());
		if (null != papername && !"".equals(papername)) {
			criteria.add(Restrictions.like("papersname", "%" + papername + "%"));
		}
		if (corpids != null && corpids.length > 0) {
			criteria.add(Restrictions.in("corpid", corpids));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != startTime && !"".equals(startTime)) {
			criteria.add(Restrictions.sqlRestriction("to_char(createdtime,'yyyy-MM-dd HH:mm:ss') >= ? ", startTime,
					Hibernate.STRING));
		}

		if (null != endTime && !endTime.equals("")) {
			criteria.add(Restrictions.sqlRestriction("to_char(createdtime,'yyyy-MM-dd HH:mm:ss') <= ? ", endTime,
					Hibernate.STRING));
		}

		// EquCar 车号过滤条件
		final String[] carkindids = (null == spePapers ? null : spePapers.getCarkindids());
		final String[] cartypeids = (null == spePapers ? null : spePapers.getCartypeids());
		final String[] goodsids = (null == spePapers ? null : spePapers.getGoodsids());

		String sql = "select t.carno from tb_zbc_equ_car t where 1=1 ";
		if (null != carkindids && carkindids.length > 0) {
			sql += "and  t.carkindid IN (" + CommUtils.getSqlIn(carkindids) + ")";
		}
		if (null != cartypeids && cartypeids.length > 0) {
			sql += "and  t.cartypeid IN (" + CommUtils.getSqlIn(cartypeids) + ")";
		}
		if (null != goodsids && goodsids.length > 0) {
			sql += "and  t.goodsid IN (" + CommUtils.getSqlIn(goodsids) + ")";
		}
		System.out.println(sql);
		if (null != carkindids || null != cartypeids || null != goodsids) {
			criteria.add(Restrictions.sqlRestriction(" carno in (" + sql + ")"));
		}

	}
}
