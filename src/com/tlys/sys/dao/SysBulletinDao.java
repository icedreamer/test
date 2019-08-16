package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysBulletin;

/**
 * 通知公告主表
 * 
 * @author ccsong 2012-5-29 下午3:42:11
 */
@Repository
public class SysBulletinDao extends _GenericDao<SysBulletin> {

	public void findSysBulletin(final SysBulletin sysBulletin, final PageCtr<SysBulletin> pageCtr) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysBulletin.class);
				buildCriteria(sysBulletin, criteria);
				criteria.addOrder(Order.desc("publishtime"));
				criteria.addOrder(Order.desc("writetime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public int getSysBulletinCount(final SysBulletin sysBulletin) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysBulletin.class);
				buildCriteria(sysBulletin, criteria);
				criteria.setProjection(Projections.rowCount());
				return criteria.uniqueResult();
			}
		});
	}

	private void buildCriteria(SysBulletin sysBulletin, Criteria criteria) {
		if (null == sysBulletin) {
			return;
		}
		String writerid = sysBulletin.getWriterid();
		String publisherid = sysBulletin.getPublisherid();
		String status = sysBulletin.getStatus();
		String kind = sysBulletin.getKind();
		Date writetime = sysBulletin.getWritetime();
		Date publishtime = sysBulletin.getPublishtime();
		if (null != writerid && !"".equals(writerid)) {
			criteria.add(Restrictions.eq("writerid", writerid));
		}
		if (null != publisherid && !"".equals(publisherid)) {
			criteria.add(Restrictions.eq("publisherid", publisherid));
		}
		if (null != status && !"".equals(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (null != writetime) {
			criteria.add(Restrictions.ge("writetime", writetime));
		}
		if (null != publishtime) {
			criteria.add(Restrictions.ge("publishtime", publishtime));
		}
		if (null != kind && !"".equals(kind)) {
			criteria.add(Restrictions.eq("kind", kind));
		}
	}

	public List<Object[]> findWriter() {
		String sql = "SELECT DISTINCT b.userid,b.username from SysBulletin a,SysUser b WHERE a.writerid = b.userid ";
		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	public List<Object[]> findPublisher() {
		String sql = "SELECT DISTINCT b.userid,b.username from SysBulletin a,SysUser b WHERE a.publisherid = b.userid ";
		return (List<Object[]>) getHibernateTemplate().find(sql);
	}

	public Long getNextSysBulletinSeqId() {
		final String sql = "select SEQ_TB_ZBC_SYS_BULLETIN.nextval from dual";
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				Object o = query.uniqueResult();
				return o == null || o.toString().equals("") ? 0l : Long.valueOf(o.toString());
			}
		});
	}

	public List<SysBulletin> findSysBulletin(final String userid, final String status) {
		final String sql = "SELECT a FROM  SysBulletin a WHERE a.status = ? and a.bulletinid NOT IN (SELECT b.bulletinid FROM SysBulletinReadinfo b WHERE b.readerid = ? ) order by a.publishtime desc ";
		return (List<SysBulletin>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setString(0, status);
				query.setString(1, userid);
				return query.list();
			}
		});
	}
	
	
}
