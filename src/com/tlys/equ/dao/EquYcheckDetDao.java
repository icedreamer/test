package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquYcheckDet;

@Repository
public class EquYcheckDetDao extends _GenericDao<EquYcheckDet> {

	public void findEquYcheckDet(final EquYcheckDet equYcheckDet, final PageCtr<EquYcheckDet> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EquYcheckDet.class);
				buildCriteria(criteria, equYcheckDet);
				criteria.addOrder(Order.asc("yinspectionno"));
				criteria.addOrder(Order.asc("goodsid"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public void buildCriteria(Criteria criteria, EquYcheckDet equYcheckDet) {
		String year = null;
		String corpid = null;
		String carno = null;
		String carkindid = null;
		String cartypeid = null;
		String goodsid = null;
		String saftcertcode = null;
		String trancertcode = null;
		String usercertcode = null;
		String yinspectionno = null;
		List<String> yinspectionnoList = null;
		String[] carnos = null;
		String cartypeids = null;
		String carkindids = null;
		String goodsids = null;
		String corpids = null;
		String inspectiontype = null;
		if (null != equYcheckDet) {
			year = equYcheckDet.getYear();
			corpid = equYcheckDet.getCorpid();
			carno = equYcheckDet.getCarno();
			carkindid = equYcheckDet.getCarkindid();
			cartypeid = equYcheckDet.getCartypeid();
			goodsid = equYcheckDet.getGoodsid();
			saftcertcode = equYcheckDet.getSaftcertcode();
			trancertcode = equYcheckDet.getTrancertcode();
			usercertcode = equYcheckDet.getUsercertcode();
			yinspectionno = equYcheckDet.getYinspectionno();
			inspectiontype = equYcheckDet.getInspectiontype();
			// yinspectionnoList = equYcheckDet.getYinspectionnoList();
			// carnos = equYcheckDet.getCarnos();
			// cartypeids = equYcheckDet.getCartypeids();
			// carkindids = equYcheckDet.getCarkindids();
			// goodsids = equYcheckDet.getGoodsids();
			// corpids = equYcheckDet.getCorpids();
		}
		if (null != year && !"".equals(year)) {
			criteria.add(Restrictions.eq("year", year));
		}
		if (null != corpid && !"".equals(corpid)) {
			criteria.add(Restrictions.eq("corpid", corpid));
		}
		if (null != corpids && !"".equals(corpids)) {
			criteria.add(Restrictions.in("corpid", corpids.split(",")));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != carno && !"".equals(carno)) {
			criteria.add(Restrictions.sqlRestriction("trim(carno) = '" + carno + "'"));
		}
		if (null != carkindid && !"".equals(carkindid)) {
			criteria.add(Restrictions.eq("carkindid", carkindid));
		}
		if (null != carkindids && !"".equals(carkindids)) {
			criteria.add(Restrictions.in("carkindid", carkindids.split(",")));
		}
		if (null != cartypeid && !"".equals(cartypeid)) {
			criteria.add(Restrictions.eq("cartypeid", cartypeid));
		}
		if (null != cartypeids && !"".equals(cartypeids)) {
			criteria.add(Restrictions.in("cartypeid", cartypeids.split(",")));
		}
		if (null != goodsid && !"".equals(goodsid)) {
			criteria.add(Restrictions.eq("goodsid", goodsid));
		}
		if (null != goodsids && !"".equals(goodsids)) {
			criteria.add(Restrictions.in("goodsid", goodsids.split(",")));
		}
		if (null != saftcertcode && !"".equals(saftcertcode)) {
			criteria.add(Restrictions.eq("saftcertcode", saftcertcode));
		}
		if (null != trancertcode && !"".equals(trancertcode)) {
			criteria.add(Restrictions.eq("trancertcode", trancertcode));
		}
		if (null != usercertcode && !"".equals(usercertcode)) {
			criteria.add(Restrictions.eq("usercertcode", usercertcode));
		}
		if (null != yinspectionno && !"".equals(yinspectionno)) {
			criteria.add(Restrictions.eq("yinspectionno", yinspectionno));
		}

		if (null != yinspectionnoList && !yinspectionnoList.isEmpty()) {
			criteria.add(Restrictions.in("yinspectionno", yinspectionnoList));
		}
		if (null != carnos && carnos.length > 0) {
			criteria.add(Restrictions.sqlRestriction("carno in (" + CommUtils.getSqlIn(carnos) + ")"));
		}

		if (null != inspectiontype && !inspectiontype.equals("")) {
			criteria.add(Restrictions.eq("inspectiontype", inspectiontype));
		}
	}

	public int getEquYcheckDetCount(final EquYcheckDet equYcheckDet) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EquYcheckDet.class);
				buildCriteria(criteria, equYcheckDet);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (null != o && !"".equals(o.toString())) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	public List<EquYcheckDet> findEquYcheckDet(final EquYcheckDet equYcheckDet) {
		return (List<EquYcheckDet>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquYcheckDet.class);
				buildCriteria(criteria, equYcheckDet);
				criteria.addOrder(Order.asc("yinspectionno"));
				criteria.addOrder(Order.asc("goodsid"));
				criteria.addOrder(Order.asc("carno"));
				return criteria.list();
			}
		});
	}

	public void delete(final List<String> carnoList, final String year, final String corpid) {
		if (null == carnoList || carnoList.isEmpty()) {
			return;
		}
		final String hql = "delete from EquYcheckDet a where a.corpid = ? and a.year = ?  and a.carno in (:carnoList)";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, corpid);
				query.setString(1, year);
				query.setParameterList("carnoList", carnoList);
				return query.executeUpdate();
			}
		});
	}

	public void delete(final List<String> yinspectionnos) {
		if (null == yinspectionnos || yinspectionnos.isEmpty()) {
			return;
		}
		final String hql = "delete from EquYcheckDet a where a.yinspectionno in (:yinspectionnos) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("yinspectionnos", yinspectionnos);
				return query.executeUpdate();
			}
		});
	}

	// public void update(final String yinspectionno, final String goodsid,
	// String type,
	// final String cardnum) {
	// final String hql = "update EquYcheckDet a set a." + type
	// + " = ? where a.yinspectionno = ? and a.goodsid = ?";
	// getHibernateTemplate().execute(new HibernateCallback() {
	// public Object doInHibernate(Session s) throws HibernateException,
	// SQLException {
	// Query query = s.createQuery(hql);
	// query.setString(0, cardnum);
	// query.setString(1, yinspectionno);
	// query.setString(2, goodsid);
	// return query.executeUpdate();
	// }
	// });
	// }
}
