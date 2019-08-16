package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpePackmark;
import com.tlys.spe.model.SpePackmarkPic;

@Repository
public class SpePackmarkPicDao extends _GenericDao<SpePackmarkPic> {

	public void findSpePackmarkPic(final SpePackmarkPic spePackmarkPic, final PageCtr pageCtr){
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				/*Criteria ca = session.createCriteria(SpePackmarkPic.class);
				Criteria caMark = session.createCriteria(SpePackmark.class);
				buildCrit(ca,caMark,spePackmarkPic);
				
				ca.addOrder(Order.desc("markid"));
				pageCtr.buildList(ca);*/
				
				//����hql��ѯ
				String hql = "select a from SpePackmarkPic a,SpePackmark b where a.markid=b.markid";
				hql += " and " + buildWhere(spePackmarkPic);
				hql += " order by a.markid";
				
				Query query = session.createQuery(hql);
				pageCtr.buildList(query);
				
				return null;
			}
		});
	}

	
	
	
	public int getSpePackmarkPicCount(final SpePackmarkPic spePackmarkPic) {
		
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				/*Criteria ca = s.createCriteria(SpePackmarkPic.class);
				Criteria caMark = s.createCriteria(SpePackmark.class);
				
				buildCrit(ca,caMark,spePackmarkPic);
				
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();*/
				String hql = "select count(a) from SpePackmarkPic a,SpePackmark b where a.markid=b.markid";
				hql += " and " + buildWhere(spePackmarkPic);
				Query query = session.createQuery(hql);
				Object o = query.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}
	
	/**
	 * ��ѯ�������ɣ��ڼ��㷵�ؼ�¼����������ѯ�б��ʱ���Ҫ���á�
	 * 
	 * �����е����⣬�Ȳ��markid����ͨ��markid��pic
	 * 
	 * &&&&&&ע�⣬�˷����Ѳ���ʹ�ã�����query���飬�˷������ڴ˽���Χ��
	 * @param crit
	 * @param spePackmarkPic
	 */
	private void buildCrit(Criteria crit,Criteria critMark,SpePackmarkPic spePackmarkPic){
		if(null!=spePackmarkPic){
			String markname = spePackmarkPic.getSpePackmark().getMarkname();
			String kind = spePackmarkPic.getSpePackmark().getKind();
		
			if (null != markname && !"".equals(markname)) {
				critMark.add(Restrictions.like("markname", "%" + markname + "%"));
			}
			if (null != kind && !"".equals(kind)) {
				critMark.add(Restrictions.eq("kind", kind));
			}
			List<SpePackmark> markList = critMark.list();
			String[] ids = new String[markList.size()];
			for (int i=0;i<markList.size();i++) {
				ids[i]=markList.get(i).getMarkid();
			}

			if(null != ids && ids.length>0){
				crit.add(Restrictions.in("markid", ids));
			}else{
				crit.add(Restrictions.eq("markid", "-1"));
			}
		
		}
	}
	
	/**
	 * ���ɲ�ѯhql
	 * @param spePackmarkPic
	 * @return
	 */
	private String buildWhere(SpePackmarkPic spePackmarkPic){
		//
		String extHql = "";
		if(null!=spePackmarkPic){
			String markname = spePackmarkPic.getSpePackmark().getMarkname();
			String kind = spePackmarkPic.getSpePackmark().getKind();
			if (null != markname && !"".equals(markname)) {
				extHql += "b.markname like '%" + markname + "%'";
			}
			if (null != kind && !"".equals(kind)) {
				if(!"".equals(extHql)){
					extHql += " and ";
				}
				extHql += "b.kind= '" + kind + "'";
			}
		}
		if("".equals(extHql)){
			extHql = "1=1";
		}
		return extHql;
	}




	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_SPE_PACKMARK_PIC.nextval from dual";
		Object o=getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s)
					throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}




	public List<SpePackmarkPic> find(final SpePackmarkPic spePackmarkPic) {
			List<SpePackmarkPic> markPicList = (List<SpePackmarkPic>)getHibernateTemplate().execute(new HibernateCallback() {
				public List<SpePackmarkPic> doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria ca = session.createCriteria(SpePackmarkPic.class);
					List<SpePackmarkPic> markPicList = ca.add(Example.create(spePackmarkPic)).list();
					return markPicList;
				}
			});
			
			return markPicList;
	}
}
