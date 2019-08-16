/**
 * 
 */
package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysUser;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class SysUserDao extends _GenericDao<SysUser> {

	public SysUser load(Integer id) {
		SysUser sysUser = super.load(id);
		return sysUser;
	}

	public List findSysUsers() {
		return super.findAll();
	}
	
	public List<SysUser> findUsers(){
		return super.findAll(" where corptab != 9 order by corptab, username ");
	}

	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_SYS_USER.nextval from dual";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}

	public SysUser loadByLoginid(final String loginid) {
		SysUser sysUser = super.findUnique("from SysUser where loginid=?", loginid);

		return sysUser;
	}

	/**
	 * 查找当前组所包含的用户
	 * 
	 * @param groupid
	 * @return
	 */
	public List<SysUser> findGroupUsers(String groupid) {
		String hql = "from SysUser su where su.userid in"
				+ "(select sug.userid from SysUsergroups sug where sug.groupid='" + groupid + "')";

		List<SysUser> rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 查找当前角色所包含的用户
	 * 
	 * @param roleid
	 * @return
	 */
	public List<SysUser> findRoleUsers(String roleid) {
		String hql = "from SysUser su where su.userid in"
				+ "(select sug.userid from SysUserroles sug where sug.roleid='" + roleid + "')";

		List<SysUser> rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	public List<SysUser> findSysUserByUserIds(final List<String> userIdList) {
		final String hql = "from SysUser where userid in (:userIdList)";
		return (List<SysUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("userIdList", userIdList);
				List<SysUser> list = query.list();
				return list;
			}
		});
	}

	public List<SysUser> find_bak(SysUser sysUser) {
		final Example exUser = Example.create(sysUser).ignoreCase();
		List<SysUser> sysUsers = super.findByExample(sysUser);
		return sysUsers;
	}

	public List<SysUser> find(final SysUser sysUser) {
		return (List<SysUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SysUser.class);
				buildCrit(ca, sysUser);
				ca.addOrder(Order.asc("corptab"));// 排序
				ca.addOrder(Order.asc("corpid"));// 排序
				ca.addOrder(Order.asc("username"));// 排序
				return ca.list();
			}
		});

	}
	public List<SysUser> findSysUser(final SysUser sysUser) {
		return (List<SysUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SysUser.class);
				buildCrit(ca, sysUser);
				ca.addOrder(Order.asc("username"));// 排序
				return ca.list();
			}
		});
		
	}

	/**
	 * 分页查询
	 * 
	 * @param plaYuprtamount
	 * @param pageCtr
	 */
	public void find(final SysUser sysUser, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SysUser.class);
				buildCrit(ca, sysUser);
				ca.addOrder(Order.asc("corptab"));// 排序
				ca.addOrder(Order.asc("corpid"));// 排序
				ca.addOrder(Order.asc("username"));// 排序
				pageCtr.buildList(ca);
				return null;
			}
		});

	}

	public int getSysUserCount(final SysUser sysUser) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SysUser.class);
				buildCrit(ca, sysUser);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 查询条件生成，在计算返回记录数和真正查询列表的时候均要调用。
	 * 
	 * @param ca
	 * @param plaYuprtamount
	 */
	private void buildCrit(Criteria ca, SysUser sysUser) {
		if (null == sysUser) {
			return;
		}
		// sp2null(sysUser);
		ca.add(Example.create(sysUser).enableLike(MatchMode.ANYWHERE));
	}

	/**
	 * 将查询字段的值从空值变为null,目前username和loginid 后发现，当查询值为空时，不影响查询结果，待验证
	 * 
	 * @param plaMsprtrain
	 */
	private void sp2null(SysUser sysUser) {
		if (null != sysUser.getUsername() && "".equals(sysUser.getUsername())) {
			sysUser.setUsername(null);
		}
		if (null != sysUser.getLoginid() && "".equals(sysUser.getLoginid())) {
			sysUser.setLoginid(null);
		}

	}

	/**
	 * 根据用户所能操作的企业权限获取这些企业下的所有用户Id
	 * 
	 * @param corpids
	 * @return
	 */
	public List<String> getUserIdsByCorpids(final Object[] corpids) {
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysUser.class);
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("userid"));
				criteria.setProjection(projectionList);
				if (null != corpids && corpids.length > 0) {
					criteria.add(Restrictions.in("corpid", corpids));
				}
				return criteria.list();
			}
		});
	}

	public List<SysUser> findSysUser(final List<String> useridList) {
		return (List<SysUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(SysUser.class);
				if (null != useridList && !useridList.isEmpty()) {
					criteria.add(Restrictions.in("userid", useridList));
				}
				return criteria.list();
			}
		});
	}

	public List<SysUser> findSysUserByNoContain(final List<String> useridList, final String corpid) {
		return (List<SysUser>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(SysUser.class);
				if (null != useridList && !useridList.isEmpty()) {
					criteria.add(Restrictions.not(Restrictions.in("userid", useridList)));
				}
				if (logger.isDebugEnabled()) {
					logger.debug("corpid : " + corpid);
				}
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("corpid", corpid));
				} else {
					criteria.add(Restrictions.not(Restrictions.eq("corpid", "lis00000")));
				}
				return criteria.list();
			}
		});
	}
}
