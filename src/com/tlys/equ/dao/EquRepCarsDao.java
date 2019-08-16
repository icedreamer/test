package com.tlys.equ.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquRepCars;
import com.tlys.equ.model.EquRepCarsId;

@Repository
public class EquRepCarsDao extends _GenericDao<EquRepCars> {

	@Autowired
	DicMap dicMap;

	public void findEquRepCars(final EquRepCars equRepCars, final PageCtr<EquRepCars> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {

				Criteria criteria = s.createCriteria(EquRepCars.class);
				buildCriteria(equRepCars, criteria);
				criteria.addOrder(Order.asc("id.areaid"));
				criteria.addOrder(Order.asc("id.corpshortname"));
				criteria.addOrder(Order.asc("id.carno"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public int getEquRepCarsCount(final EquRepCars equRepCars) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepCars.class);
				buildCriteria(equRepCars, criteria);
				criteria.setProjection(Projections.rowCount());
				return criteria.uniqueResult();
			}
		});
	}

	private void buildCriteria(EquRepCars equRepCars, Criteria criteria) {
		String repkind = null;
		String cartypeids = null;
		String carkindids = null;
		String corpids = null;
		String carno = null;
		Object[] carnos = null;
		String areaids = null;
		String goodsids = null;
		if (null != equRepCars) {
			EquRepCarsId equRepCarsId = equRepCars.getId();
			if (null != equRepCarsId) {
				repkind = equRepCarsId.getRepkind();
				carno = equRepCarsId.getCarno();
			}
			cartypeids = equRepCars.getCartypeids();
			carkindids = equRepCars.getCarkindids();
			corpids = equRepCars.getCorpids();
			carnos = equRepCars.getCarnos();
			areaids = equRepCars.getAreaids();
			goodsids = equRepCars.getGoodsids();
		}
		if (null != carno && !"".equals(carno)) {
			// criteria.add(Restrictions.eq("id.carno", carno));
			criteria.add(Restrictions.like("id.carno", carno + "%"));
		}
		if (null != repkind && !"".equals(repkind)) {
			criteria.add(Restrictions.eq("id.repkind", repkind));
		}
		if (null != cartypeids && !"".equals(cartypeids)) {
			criteria.add(Restrictions.in("id.cartypeid", cartypeids.split(",")));
		}
		if (null != carkindids && !"".equals(carkindids)) {
			criteria.add(Restrictions.in("id.carkindid", carkindids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			criteria.add(Restrictions.in("id.corpid", corpids.split(",")));
		} else {
			// criteria.add(Restrictions.sqlRestriction(CommUtils.userCorpAuthHql(CommUtils.getCurUser(),
			// dicMap)));
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != carnos && carnos.length > 0) {
			criteria.add(Restrictions.in("id.carno", carnos));
		}
		if (null != areaids && !"".equals(areaids)) {
			criteria.add(Restrictions.in("id.areaid", areaids.split(",")));
		}
		if (null != goodsids && !"".equals(goodsids)) {
			criteria.add(Restrictions.in("id.goodsid", goodsids.split(",")));
		}
	}

	public Object[] call_P_ZBC_REP_GENCAR() {
		return (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] o = new Object[2];
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
					String procedure = "{call P_ZBC_REP_GENCAR(?,?)}";
					cstmt = conn.prepareCall(procedure);
					cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
					cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
					cstmt.execute();
					o[0] = cstmt.getInt(1);// 返回值代码
					o[1] = cstmt.getString(2);// 返回值描述
					return o;
				} catch (Exception e) {
					logger.error("call P_ZBC_REP_GENCAR error.", e);
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

	public List<Object[]> findEquRepCarsNum(final String corpid) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquRepCars.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("id.repkind"));
				projectionList.add(Projections.count("id.carno"));
				projectionList.add(Projections.groupProperty("id.repkind"));
				criteria.setProjection(projectionList);
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("id.corpid", corpid));
				}
				criteria.add(Restrictions.in("id.repkind", new String[] { "MP", "EP" }));
				return criteria.list();
			}
		});
	}

}
