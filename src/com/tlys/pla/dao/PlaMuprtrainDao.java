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
import com.tlys.pla.model.PlaMuprtrain;

@Repository
public class PlaMuprtrainDao extends _GenericDao<PlaMuprtrain> {

	public List<PlaMuprtrain> find(final PlaMuprtrain PlaMuprtrain) {
		return (List<PlaMuprtrain>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<PlaMuprtrain> plaMuprtrains = new ArrayList<PlaMuprtrain>();
						Criteria ca = session
								.createCriteria(PlaMuprtrain.class);
						buildCrit(ca, PlaMuprtrain);
						
						ca.addOrder(Order.asc("areaid"));// ����
						ca.addOrder(Order.asc("corpshortname"));// ����
						//ca.addOrder(Order.desc("month"));// ����
						ca.addOrder(Order.desc("checktime"));// ����

						return ca.list();
					}
				});
	}

	/**
	 * ����ָ��hql��ʵ�弯��
	 * 
	 * @param hql
	 * @return List
	 */
	public List<PlaMuprtrain> find(final String hql) {
		List<PlaMuprtrain> Objects = (List<PlaMuprtrain>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createQuery(hql).list();
						return dacs;
					}
				});
		return Objects;
	}

	/**
	 * ��ѯ�������ɣ��ڼ��㷵�ؼ�¼����������ѯ�б��ʱ���Ҫ���á�
	 * 
	 * @param ca
	 * @param plaMuprtrain
	 */
	private void buildCrit(Criteria ca, PlaMuprtrain plaMuprtrain) {
		if(null == plaMuprtrain) return;
		sp2null(plaMuprtrain);
		
		ca.add(Example.create(plaMuprtrain).enableLike(MatchMode.ANYWHERE));
		
		String checkstatusids = plaMuprtrain.getCheckstatusids();
		String corpids = plaMuprtrain.getCorpids();
		
		if (null != checkstatusids && checkstatusids.length() > 0) {
			ca.add(Restrictions.in("checkstatus", checkstatusids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			ca.add(Restrictions.in("corpid", corpids.split(",")));
		}
	}
	
	
	
	
	public void find(final PlaMuprtrain plaMuprtrain, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(PlaMuprtrain.class);
				buildCrit(ca, plaMuprtrain);
				ca.addOrder(Order.desc("month"));// ����
				ca.addOrder(Order.asc("areaid"));// ����
				ca.addOrder(Order.asc("corpshortname"));// ����
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getPlaMuprtrainCount(final PlaMuprtrain plaMuprtrain) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria ca = session
								.createCriteria(PlaMuprtrain.class);
						buildCrit(ca, plaMuprtrain);
						ca.setProjection(Projections.rowCount());
						Object o = ca.uniqueResult();
						return Integer.parseInt(o.toString());
					}
				});
	}
	
	
	/**
	 * ����ѯ�ֶε�ֵ�ӿ�ֵ��Ϊnull,Ŀǰacceptcarno���ֵ������null���ȼ���
	 * @param plaMsprtrain
	 */
	private void sp2null(PlaMuprtrain pmst){
		if(null!=pmst.getAcceptcarno() && "".equals(pmst.getAcceptcarno())){
			pmst.setAcceptcarno(null);
		}

		
	}
}
