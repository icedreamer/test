package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysMessage;

/**
 * 
 * @author ccsong 2012-3-22 ÏÂÎç12:35:12
 */
@Repository
public class SysMessageDao extends _GenericDao<SysMessage> {

	public List<Object[]> findSysMessage(final String userid) {
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMessage.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("msgtypeid"));
				projectionList.add(Projections.count("id"));
				criteria.setProjection(projectionList);
				criteria.add(Restrictions.eq("receiverid", userid));
				criteria.add(Restrictions.eq("status", "0"));
				return criteria.list();
			}
		});
	}

	public void findSysMessage(final SysMessage sysMessage, final PageCtr<SysMessage> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMessage.class);
				buildCriteria(sysMessage, criteria);
				criteria.addOrder(Order.desc("sendtime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	public int getSysMessageCount(final SysMessage sysMessage) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMessage.class);
				buildCriteria(sysMessage, criteria);
				criteria.setProjection(Projections.rowCount());
				return criteria.uniqueResult();
			}
		});
	}

	private void buildCriteria(SysMessage sysMessage, Criteria criteria) {
		String status = "";
		String msgtypeid = "";
		Date startDate = null;
		Date endDate = null;
		String senderid = "";
		String receiverid = "";
		if (null != sysMessage) {
			status = sysMessage.getStatus();
			msgtypeid = sysMessage.getMsgtypeid();
			startDate = sysMessage.getStartDate();
			endDate = sysMessage.getEndDate();
			senderid = sysMessage.getSenderid();
			receiverid = sysMessage.getReceiverid();
		}
		if (null != senderid && !"".equals(senderid)) {
			criteria.add(Restrictions.eq("senderid", senderid));
		}
		if (null != receiverid && !"".equals(receiverid)) {
			criteria.add(Restrictions.eq("receiverid", receiverid));
		}
		if (null != status && !"".equals(status)) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (null != msgtypeid && !"".equals(msgtypeid)) {
			criteria.add(Restrictions.eq("msgtypeid", msgtypeid));
		}
		if (null != startDate) {
			criteria.add(Restrictions.ge("sendtime", startDate));
		}
		if (null != endDate) {
			criteria.add(Restrictions.le("sendtime", endDate));
		}
	}

	public void updateSysMessages(final List<Long> ids) {
		final String hql = "update SysMessage a set a.status = 1 where a.id in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("ids", ids);
				query.executeUpdate();
				return null;
			}
		});
	}
}
