package com.tlys.pla.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMspstransport2;

@Repository
public class PlaMspstransport2Dao extends _GenericDao<PlaMspstransport2> {

	public void find(final PlaMspstransport2 plaMspstransport2, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransport2.class);
				buildCrit(ca, plaMspstransport2);
				ca.addOrder(Order.desc("month"));// ����
				ca.addOrder(Order.asc("corpid"));
				pageCtr.buildList(ca);
				return null;
			}
		});
	}

	public int getCount(final PlaMspstransport2 plaMspstransport2) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransport2.class);
				buildCrit(ca, plaMspstransport2);

				/*
				 * ca.setProjection(Projections.rowCount()); Object o =
				 * ca.uniqueResult(); return Integer.parseInt(o.toString());
				 */

				List planList = ca.list();
				int cnt = 0;
				if (null != planList) {
					cnt = planList.size();
				}
				ca.list().size();
				return cnt;
			}
		});
	}

	private void buildCrit(Criteria crit, PlaMspstransport2 plaMspstransport2) {
		if (null == plaMspstransport2)
			return;
		crit.add(Example.create(plaMspstransport2).enableLike(MatchMode.ANYWHERE));

		// add by fengym
		ProjectionList projectionList = Projections.projectionList();

		// ����������������distinctӰ��ķ���Ч����һ����
		projectionList.add(Projections.groupProperty("corpid"));
		projectionList.add(Projections.groupProperty("month"));

		crit.setProjection(projectionList);
		// end of add

		String corpids = plaMspstransport2.getCorpids();
		String statuss = plaMspstransport2.getStatuss();
		String expdateStart = plaMspstransport2.getMonthStart();
		String expdateEnd = plaMspstransport2.getMonthEnd();
		String plannos = plaMspstransport2.getPlannos();
		if (null != statuss && statuss.length() > 0) {
			crit.add(Restrictions.in("status", statuss.split(",")));
		}
		if (null != corpids && corpids.length() > 0) {
			for (int i = 0; i < corpids.split(",").length; i++) {
				logger.debug("corpids.split(,) : " + corpids.split(",")[i]);
			}
			crit.add(Restrictions.in("corpid", corpids.split(",")));
		}
		/*
		 * ԭ������Ȩ��ֱ�Ӽ������ȡ��������service��ͳһ��
		 * 
		 * 2012-10-22 ��ҵ�������е�����ʱ�����⣬�ָ������ﴦ����ҵ����Ȩ��
		 */
		else {
			crit.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
		}
		if (null != expdateStart && !"".equals(expdateStart)) {
			expdateStart = expdateStart.replace("-", "");
			crit.add(Restrictions.ge("month", expdateStart));
		}
		if (null != expdateEnd && !expdateEnd.equals("")) {
			expdateEnd = expdateEnd.replace("-", "");
			crit.add(Restrictions.le("month", expdateEnd));
		}
		if (null != plannos && plannos.length() > 0) {
			crit.add(Restrictions.in("planno", plannos.split(",")));
		}
	}

	public List find(final PlaMspstransport2 PlaMspstransport2) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransport2.class);
				buildCrit(ca, PlaMspstransport2);
				return ca.list();
			}
		});
	}

	/**
	 * ��ѯ�����ƻ�
	 * 
	 * @param PlaMspstransport2
	 * @return
	 */
	public List<PlaMspstransport2> findByAdjust(final PlaMspstransport2 plaMspstransport2) {
		return (List<PlaMspstransport2>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMspstransport2.class);
				// ������ҵID���·�������ѯ����
				ca.add(Example.create(plaMspstransport2).enableLike(MatchMode.ANYWHERE));

				String statuss = plaMspstransport2.getStatuss();
				if (null != statuss && !"".equals(statuss)) {
					ca.add(Restrictions.in("status", statuss.split(",")));
				}

				// ��������������������ʾ
				ca.addOrder(Order.desc("adjustnumber"));
				// if(log.isDebugEnabled()){
				// log.debug("################################��ѯ������"+ca);
				// }
				return ca.list();
			}
		});
	}

	/**
	 * ��鱾�ƻ�֮ǰ�Ƿ���ָ��״̬�ļƻ�
	 * 
	 * @param plano
	 * @param statuss��ָ����״̬�����ŷֿ����б�
	 * @return
	 */
	public boolean hasPrePlaStatus(String plano, String statuss) {
		boolean reFlag = false;
		String pnSch = plano.substring(0, plano.length() - 3);
		// System.out.println("PlaMupstransport2Dao.getMaxPlano->pnSch=="+pnSch);
		DetachedCriteria decrit = DetachedCriteria.forClass(PlaMspstransport2.class);
		decrit.add(Restrictions.like("planno", pnSch, MatchMode.START));
		decrit.add(Restrictions.lt("planno", plano));
		decrit.add(Restrictions.in("status", statuss.split(",")));

		// decrit.setProjection(Projections.max("planno"));

		List mplist = getHibernateTemplate().findByCriteria(decrit);
		if (null != mplist && mplist.size() > 0) {
			reFlag = true;
		}

		return reFlag;
	}

	/**
	 * ���ݵ�ǰ�����ļƻ���ţ����ҵ�ǰ�ƻ��������������ļƻ����
	 * 
	 * @param plano
	 * @return
	 */
	public String getMaxPlanno(String plano) {
		String rePlano = "";
		String pnSch = plano.substring(0, plano.length() - 3);
		// System.out.println("PlaMupstransport2Dao.getMaxPlano->pnSch=="+pnSch);
		DetachedCriteria decrit = DetachedCriteria.forClass(PlaMspstransport2.class);
		decrit.add(Restrictions.like("planno", pnSch, MatchMode.START));
		decrit.setProjection(Projections.max("planno"));

		List mplist = getHibernateTemplate().findByCriteria(decrit);

		// System.out.println("PlaMupstransport2Dao.getMaxPlano->mplist.size()=="+mplist.size());

		Object ro = mplist.size() > 0 ? mplist.get(0) : null;

		// Object[] roArr = (Object[])ro;

		// System.out.println("PlaMupstransport2Dao.getMaxPlano->ro[]=="+ro);

		return ro.toString();
	}
}
