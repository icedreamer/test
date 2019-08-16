package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysMail;
import com.tlys.sys.model.SysMailfolder;
import com.tlys.sys.model.SysUser;

@Repository
public class SysMailDao extends _GenericDao<SysMail> {

	public long getNextSysMailSeqId() {
		final String sql = "select SEQ_TB_ZBC_SYS_MAIL.nextval from dual";
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				Object o = query.uniqueResult();
				return o == null || o.toString().equals("") ? 0l : Long.valueOf(o.toString());
			}
		});
	}

	public void findSysMails(final SysMail sysMail, final PageCtr<SysMail> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMail.class);
				buildCriteria(sysMail, criteria);
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	private void buildCriteria(SysMail sysMail, Criteria criteria) {
		if (null == sysMail) {
			return;
		}
		SysUser sysUser = sysMail.getSysUser();
		String ownerid = "";
		if (null != sysUser) {
			ownerid = sysUser.getUserid();
		}
		criteria.add(Restrictions.eq("sysUser.userid", ownerid));
	}

	public int getSysMailCount(final SysMail sysMail) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMail.class);
				buildCriteria(sysMail, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (o != null) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	/**
	 * 拿到分组后的邮件数
	 * 
	 * @param sysMailfolder
	 * @return
	 */
	public List getMailCountGroup(final SysUser sysUser) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMailfolder.class);
				String superUserId = CommUtils.getSuperUser();
				// SysUser sysUser = sysMailfolder.getSysUser();
				String ownerid = "-1";
				String loginid = "";
				if (null != sysUser) {
					ownerid = sysUser.getUserid();
					loginid = sysUser.getLoginid();
				}
				// String folder = sysMailfolder.getFolder();
				// String readflag = sysMailfolder.getReadflag();

				ProjectionList projList = Projections.projectionList();
				projList.add(Projections.groupProperty("folder"));
				projList.add(Projections.rowCount());

				criteria.setProjection(projList);
				if (null != loginid && !loginid.equals(superUserId)) {
					criteria.add(Restrictions.eq("sysUser.userid", ownerid));
				}

				List objArrList = criteria.list();
				return objArrList;
			}
		});
	}

	/**
	 * 得到新邮件数
	 * 
	 * @param sysUser
	 * @return
	 */
	public int getNewMailCount(final SysUser sysUser) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String superUserId = CommUtils.getSuperUser();
				Criteria criteria = s.createCriteria(SysMailfolder.class);
				String loginid = "";
				String ownerid = "-1";
				if (null != sysUser) {
					ownerid = sysUser.getUserid();
					loginid = sysUser.getLoginid();
				}
				if (null != loginid && !loginid.equals(superUserId)) {
					criteria.add(Restrictions.eq("sysUser.userid", ownerid));
				}

				// criteria.add(Restrictions.eq("folder", folder));

				criteria.add(Restrictions.eq("readflag", "0"));
				criteria.add(Restrictions.eq("folder", "INBOX"));
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (o != null) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

}
