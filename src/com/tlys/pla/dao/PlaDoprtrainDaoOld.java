package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaDoprtrainOld;

@Repository
public class PlaDoprtrainDaoOld extends _GenericDao<PlaDoprtrainOld> {

	public List<PlaDoprtrainOld> find(final PlaDoprtrainOld plaDoprtrainOld) {
		return (List<PlaDoprtrainOld>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<PlaDoprtrainOld> plaDoprtrainOlds = new ArrayList<PlaDoprtrainOld>();
				Criteria ca = session.createCriteria(PlaDoprtrainOld.class);
				buildCrit(ca, plaDoprtrainOld);
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.desc("loadtdate"));// 排序
				return ca.list();
			}
		});
	}

	/**
	 * 查找指定hql的实体集合
	 * 
	 * @param hql
	 * @return List
	 */
	public List<PlaDoprtrainOld> find(final String hql) {
		List<PlaDoprtrainOld> Objects = (List<PlaDoprtrainOld>) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				List dacs = session.createQuery(hql).list();
				return dacs;
			}
		});
		return Objects;
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param plaDoprtrainOld
	 */
	private void buildCrit(Criteria ca, PlaDoprtrainOld plaDoprtrainOld) {
		if (null == plaDoprtrainOld)
			return;
		ca.add(Example.create(plaDoprtrainOld).enableLike(MatchMode.ANYWHERE));

		// 产品类别
		String productkindids = plaDoprtrainOld.getProductkindids();
		String corpids = plaDoprtrainOld.getCorpids();
		if (null != productkindids && productkindids.length() > 0) {
			ca.add(Restrictions.in("productkind", productkindids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}
	}

	public void find(final PlaDoprtrainOld plaDoprtrainOld, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDoprtrainOld.class);
				buildCrit(ca, plaDoprtrainOld);
				ca.addOrder(Order.asc("areaid"));// 排序
				ca.addOrder(Order.asc("corpshortname"));// 排序
				ca.addOrder(Order.desc("loadtdate"));// 排序

				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaDoprtrainCount(final PlaDoprtrainOld plaDoprtrainOld) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaDoprtrainOld.class);
				buildCrit(ca, plaDoprtrainOld);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}
}
