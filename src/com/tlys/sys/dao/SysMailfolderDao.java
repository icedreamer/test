package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
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
import com.tlys.sys.model.SysMail;
import com.tlys.sys.model.SysMailfolder;
import com.tlys.sys.model.SysUser;

@Repository
public class SysMailfolderDao extends _GenericDao<SysMailfolder> {
	public void findSysMailfolders(final SysMailfolder sysMailfolder,
			final PageCtr<SysMailfolder> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMailfolder.class);
				Criteria criteriaSysMail = criteria.createCriteria("sysMail");
				buildCriteria(sysMailfolder, criteria, criteriaSysMail, CommUtils.getSuperUser());
				// criteria.addOrder(Order.desc("sysMail.mailtime"));

				criteriaSysMail.addOrder(Order.desc("mailtime"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	private void buildCriteria(SysMailfolder sysMailfolder, Criteria criteria,
			Criteria criteriaSysMail, String superUserId) {
		if (null == sysMailfolder) {
			return;
		}

		SysUser ownerUser = sysMailfolder.getSysUser();
		String ownerid = "";
		String userid = "";
		String loginid = "";
		String folder = sysMailfolder.getFolder();
		String readflag = sysMailfolder.getReadflag();

		if (null != folder && !"".equals(folder)) {
			criteria.add(Restrictions.eq("folder", folder));
		}
		if (null != readflag && !"".equals(readflag)) {
			criteria.add(Restrictions.eq("readflag", readflag));
		}

		if (null != ownerUser) {
			ownerid = ownerUser.getUserid();
			loginid = ownerUser.getLoginid();
		}
		if (null != loginid && !loginid.equals(superUserId)) {
			criteria.add(Restrictions.eq("sysUser.userid", ownerid));
		}
		SysMail sysMail = sysMailfolder.getSysMail();
		if (null != sysMail) {
			// Criteria criteriaSysMail = criteria.createCriteria("sysMail");
			String body = sysMail.getBody();
			if (null != body && !"".equals(body)) {
				criteriaSysMail.add(Restrictions.like("body", "%" + body + "%"));
			}
			SysUser sysUser = sysMail.getSysUser();
			if (null != sysUser) {
				Criteria criteriaSysUser = criteriaSysMail.createCriteria("sysUser");
				userid = sysUser.getUserid();
				if (null != userid && !"".equals(userid)) {
					criteriaSysUser.add(Restrictions.eq("userid", userid));
				}
			}
			String toid = sysMail.getToid();
			if (null != toid && !"".equals(toid)) {
				criteriaSysMail.add(Restrictions.or(Restrictions.like("toid", "%" + toid + "%"),
						Restrictions.like("ccid", "%" + toid + "%")));
			}
		}

	}

	public int getSysMailfolderCount(final SysMailfolder sysMailfolder) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysMailfolder.class);
				Criteria criteriaSysMail = criteria.createCriteria("sysMail");
				buildCriteria(sysMailfolder, criteria, criteriaSysMail, CommUtils.getSuperUser());
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				if (o != null) {
					return Integer.parseInt(o.toString());
				}
				return 0;
			}
		});
	}

	public List<Object[]> findUsers(String userid) {
		String sql = "select distinct b.sysUser.userid, c.username from SysMailfolder a, SysMail b,SysUser c where a.folder = 'INBOX' and a.sysUser.userid = '"
				+ userid + "' and c.userid = b.sysUser.userid and a.sysMail.mailid = b.mailid ";
		return getHibernateTemplate().find(sql);
	}

}
