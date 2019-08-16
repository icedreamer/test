package com.tlys.spe.dao;

import java.sql.SQLException;

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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpeCertisupercargo;

@Repository
public class SpeCertisupercargoDao extends _GenericDao<SpeCertisupercargo> {

	public void findSpeCertisupercargo(
			final SpeCertisupercargo speCertisupercargo, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpeCertisupercargo.class);
				buildCrit(ca, speCertisupercargo);

				String[] curSortArr = pageCtr.getCurSortArr();
				if (null == curSortArr) {
					ca.addOrder(Order.desc("createdtime"));
				} else {
					if (null != curSortArr[1]) {// 当数组第二个元素不为null时，一定是desc
						ca.addOrder(Order.desc(curSortArr[0]));
					} else {
						ca.addOrder(Order.asc(curSortArr[0]));
					}
				}
				pageCtr.buildList(ca);

				return null;
			}
		});
	}

	public int getSpeCertisupercargoCount(
			final SpeCertisupercargo speCertisupercargo) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Criteria ca = s
								.createCriteria(SpeCertisupercargo.class);
						buildCrit(ca, speCertisupercargo);

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
	 * @param speCertisupercargo
	 */
	private void buildCrit(Criteria crit, SpeCertisupercargo speCertisupercargo) {
		String[] corpids = null;
		if (null != speCertisupercargo) {
			corpids = speCertisupercargo.getCorpids();
			String expdateStart = speCertisupercargo.getExpiredateStart();
			String expdateEnd = speCertisupercargo.getExpiredateEnd();
			String name = speCertisupercargo.getName();

			if (null != name && !"".equals(name)) {
				crit.add(Restrictions.like("name", "%" + name + "%"));
			}

			if (null != expdateStart && !"".equals(expdateStart)) {
				crit.add(Restrictions.sqlRestriction(
						"to_char(expiredate,'yyyy-MM-dd') >= ? ", expdateStart,
						Hibernate.STRING));
			}

			if (null != expdateEnd && !expdateEnd.equals("")) {
				crit.add(Restrictions.sqlRestriction(
						"to_char(expiredate,'yyyy-MM-dd') <= ? ", expdateEnd,
						Hibernate.STRING));
			}
		}

		// 当corpids有值时，此值一定是当前用户有权获取的值列表，因此这时不需再额外增加权限按制
		// 所以下面用else
		if (corpids != null && corpids.length > 0) {
			crit.add(Restrictions.in("corpid", corpids));
		} else {
			// 增加用户数据权限
			crit.add(Restrictions
					.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
	}
}
