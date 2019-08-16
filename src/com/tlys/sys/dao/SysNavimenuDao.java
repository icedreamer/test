package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysNavimenu;

/**
 * �˵��־���
 * 
 * @author ccsong
 * 
 */
@Repository
public class SysNavimenuDao extends _GenericDao<SysNavimenu> {

	/**
	 * ��ȡ���в˵��б�����levelorder����
	 * 
	 * @return
	 */
	public List<SysNavimenu> findNavimenu() {
		String hql = "from SysNavimenu a order by a.levelorder ";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * ��ȡdistinct��ID
	 * 
	 * @return
	 */
	public List<SysNavimenu> findNavimenuOrderByPmenuId() {
		String sql = "select DISTINCT a.pmenuid from SysNavimenu a order by a.pmenuid,a.menuid,a.levelorder desc ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(sql);
		List<SysNavimenu> list = query.list();
		return list;
	}

	/**
	 * ���ݲ˵�ID��ȡ��Ӧ�˵�����
	 * 
	 * @param menuid
	 * @return
	 */
	public SysNavimenu getSysNavimenu(int menuid) {
		// String sql = "from SysNavimenu a where a.menuid = ? ";
		return (SysNavimenu) getHibernateTemplate().load(SysNavimenu.class, menuid);
	}

	/**
	 * ����ĳ��ɫ����δ��Ȩ�Ĳ˵�
	 * 
	 * @param roleid
	 * @return by fengym
	 */
	public List<SysNavimenu> findSysNavimenusNotSel(String roleid) {
		/*
		 * String hql = "select distinct(a) from SysNavimenu a,SysMenuopra b " +
		 * "where a.menuid=b.menuid and a.opraid not in" + "(select c.opraid
		 * from SysRoleperm c where c.roleid='" + roleid + "') order by
		 * a.menuid,a.levelorder";
		 */
		String hql = "select distinct(a) from SysNavimenu a,SysMenuopra b "
				+ "where a.menuid=b.menuid and a.menuid not in" + "(select d.menuid from SysRoleperm c,SysMenuopra d "
				+ "where c.opraid=d.opraid and c.roleid='" + roleid + "') order by a.menuid,a.levelorder";

		List<SysNavimenu> mlist = this.getHibernateTemplate().find(hql);

		return mlist;

	}

	/**
	 * ����ĳ��ɫ��������Ȩ�Ĳ˵�
	 * 
	 * @param roleid
	 * @return
	 * 
	 *         by fengym
	 */
	public List<SysNavimenu> findSysNavimenusSel(String roleid) {
		/*
		 * String hql = "select distinct(a) from SysNavimenu a,SysMenuopra b " +
		 * "where a.menuid=b.menuid and b.opraid in" + "(select c.opraid from
		 * SysRoleperm c where c.roleid='" + roleid + "') order by
		 * a.menuid,a.levelorder";
		 */

		String hql = "select distinct(a) from SysNavimenu a,SysMenuopra b " + "where a.menuid=b.menuid and a.menuid in"
				+ "(select d.menuid from SysRoleperm c,SysMenuopra d " + "where c.opraid=d.opraid and c.roleid='"
				+ roleid + "') order by a.menuid,a.levelorder";

		List<SysNavimenu> mlist = this.getHibernateTemplate().find(hql);

		return mlist;

	}

	/**
	 * �������п���Ȩ�˵��������Ȩ���� ������Ȩ�˵���ָ�˵������и�������Ĳ˵�)
	 * 
	 * @param roleid
	 * @return list����һ�����飬a[0]��SysNavimenu��a[1]��SysMenuopra��a[2]��SysOprationtype
	 * 
	 *         by fengym
	 */
	public List findMenusWithOper() {

		String hql = "select a,b,e from SysNavimenu a,SysMenuopra b,SysOprationtype e "
				+ "where a.menuid=b.menuid and b.operid=e.operid order by a.menuid,a.levelorder";

		List mlist = this.getHibernateTemplate().find(hql);
		return mlist;

	}

	/**
	 * ���ҵ�ǰ��ɫ������Ĳ˵���������Ĳ���
	 * 
	 * @param roleid
	 * @return list����һ�����飬a[0]��SysNavimenu��a[1]��SysMenuopra��a[2]��SysOprationtype
	 */
	public List findSysNavimenusAndOperSel(String roleid) {
		/*
		 * String hql = "select distinct(a) from SysNavimenu a,SysMenuopra b " +
		 * "where a.menuid=b.menuid and b.opraid in" +
		 * "(select c.opraid from SysRoleperm c where c.roleid='" + roleid +
		 * "') order by a.menuid,a.levelorder";
		 */

		String hql = "select a,b,e from SysNavimenu a,SysMenuopra b,SysOprationtype e "
				+ "where a.menuid=b.menuid and b.operid=e.operid and b.opraid in"
				+ "(select d.opraid from SysRoleperm c,SysMenuopra d " + "where c.opraid=d.opraid and c.roleid='"
				+ roleid + "') order by a.menuid,a.levelorder";

		List list = this.getHibernateTemplate().find(hql);

		return list;

	}

	/**
	 * ��ȡ���Ĳ˵�ID
	 * 
	 * @return
	 */
	public int getMaxMenuId() {
		String sql = "select menuid from (select a.menuid from TB_ZBC_SYS_NAVIMENU a order by a.menuid desc) where rownum = 1 ";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(sql);
		List<Integer> list = query.addScalar("menuid", Hibernate.INTEGER).list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return 0;
	}

	/**
	 * ���ݲ˵�ID���б�ɾ���˵�����
	 * 
	 * @param menuIds
	 */
	public void deleteSysNavimenuByNotInMenuIds(List<Integer> menuIds) {
		if (null == menuIds || menuIds.isEmpty()) {
			return;
		}
		String sql = "delete from SysNavimenu a where a.menuid not in (:menuIds)";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(sql);
		query.setParameterList("menuIds", menuIds);
		query.executeUpdate();
	}

	public SysNavimenu getSysNavimenuByUri(final String uri) {
		final String hql = "from SysNavimenu where urlpath = ? ";
		return (SysNavimenu) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, uri);
				List<SysNavimenu> list = query.list();
				if (list != null && !list.isEmpty()) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public List<SysNavimenu> findSysNavimenuByRoles(final Set<String> roleIdList) {
		if (null == roleIdList || roleIdList.isEmpty()) {
			return null;
		}
		final String hql = "SELECT a FROM SysNavimenu  a where a.menuid in (select b.menuid from SysMenuopra b,SysRoleperm c WHERE b.opraid = c.opraid and c.roleid IN (:roleIdList)) ";
		return (List<SysNavimenu>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("roleIdList", roleIdList);
				List<SysNavimenu> list = query.list();
				return list;
			}
		});
	}

	public List<SysMenuopra> findSysMenuopraByRoles(final Set<String> roleIdList) {
		if (null == roleIdList || roleIdList.isEmpty()) {
			return null;
		}
		final String hql = "Select b From SysRoleperm a, SysMenuopra b, SysNavimenu c Where a.opraid = b.opraid And c.menuid = b.menuid and a.roleid in (:roleIdList) order by b.operid desc ";
		return (List<SysMenuopra>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("roleIdList", roleIdList);
				List<SysMenuopra> list = query.list();
				return list;
			}
		});
	}

	public SysNavimenu getSysNavimenu(String menuCode) {
		return this.findUnique("from SysNavimenu where menucode = ? ", menuCode);
	}

	public List<SysNavimenu> findSysNavimenuByPmenuId(int pmenuid) {
		final String hql = "from SysNavimenu where pmenuid = ? order by levelorder";
		return getHibernateTemplate().find(hql, pmenuid);
	}

	/**
	 * ���ݲ˵�id���б��ѯ�˵��б�
	 * 
	 * @param menuIdList
	 * @return
	 */
	public List<SysNavimenu> findSysNavimenuByMenuIdList(final List<Integer> menuIdList) {
		if (null == menuIdList || menuIdList.isEmpty()) {
			return null;
		}
		final String hql = "from SysNavimenu where menuid in (:menuIdList) ";
		return (List<SysNavimenu>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("menuIdList", menuIdList);
				List<SysNavimenu> list = query.list();
				return list;
			}
		});
	}
}
