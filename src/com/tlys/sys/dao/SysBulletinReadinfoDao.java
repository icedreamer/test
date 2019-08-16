package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysBulletinReadinfo;

/**
 * 通知公告阅读信息
 * 
 * @author ccsong 2012-5-29 下午3:42:39
 */
@Repository
public class SysBulletinReadinfoDao extends _GenericDao<SysBulletinReadinfo> {
	/**
	 * 主要用户取阅读次数
	 * 
	 * @param bulletinIdList
	 * @return
	 */
	public List<Object[]> getSysBulletinReadCount(final List<Long> bulletinIdList) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysBulletinReadinfo.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("bulletinid"));
				projectionList.add(Projections.count("id"));
				criteria.setProjection(projectionList);
				criteria.add(Restrictions.in("bulletinid", bulletinIdList));
				return criteria.list();
			}
		});
	}

	public SysBulletinReadinfo getSysBulletinReadinfo(final String userid, final Long bulletinid) {
		final String hql = "from SysBulletinReadinfo t where t.readerid = ? and t.bulletinid = ? order by t.readtime desc ";
		return (SysBulletinReadinfo) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, userid);
				query.setLong(1, bulletinid);
				query.setMaxResults(1);
				return query.uniqueResult();
			}
		});
	}
	
	public boolean ifReadover(SysBulletinReadinfo btread){
		List btlist = getHibernateTemplate().findByExample(btread);
		boolean rdov = false;
		if(btlist.size()>0){
			rdov = true;
		}
		return rdov;
	}
}
