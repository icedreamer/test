package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysMenuopra;

/**
 * ������ϸ���ñ�ֻ��¼��ĩһ���˵��Ĳ�����Ϣ
 * 
 * @author ccsong
 * 
 */
@Repository
public class SysMenuopraDao extends _GenericDao<SysMenuopra> {
	/**
	 * ��ȡ�˵��ɲ������ܵ�����
	 * 
	 * @return
	 */
	public String getSeqOperaId() {
		final String sql = "select SEQ_TB_ZBC_SYS_MENUOPRA.nextval from dual";
		Object o = getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}

	/**
	 * ���Ӳ˵�ID�б�ɾ���˵���Ӧ��������
	 * 
	 * @param menuIdList
	 */
	public void deleteSysmenuopraByNotInMenuIds(final List<Integer> menuIdList) {
		if (null == menuIdList || menuIdList.isEmpty()) {
			return;
		}
		final String sql = "delete from SysMenuopra a where a.menuid not in (:menuIdList) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("menuIdList", menuIdList);
				return query.executeUpdate();
			}
		});
	}

	public List<String> findOpraIdByNotInMenuIds(final List<Integer> menuIdList) {
		if (null == menuIdList || menuIdList.isEmpty()) {
			return null;
		}
		final String sql = "select a.opraid from SysMenuopra a where a.menuid not in (:menuIdList) ";
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<String> doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setParameterList("menuIdList", menuIdList);
				return query.list();
			}
		});
	}

	/**
	 * ���ݲ˵�ID�Ͳ���ID��ȡ�˵����������¼
	 * 
	 * @param menuId
	 * @param operid
	 * @return
	 */
	public SysMenuopra getSysMenuOpraByMenuIdAndOperId(int menuId, String menuopercode) {
		return findUnique("from SysMenuopra where menuid = '" + menuId + "' and menuopercode = '" + menuopercode + "' ");
	}

	/**
	 * ���ݲ˵�ID��ȡ���в˵���������SysMenuopra
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SysMenuopra> findSysMenuopraByMenuId(int menuId) {
		return findByProperty("menuid", menuId);
	}

	public SysMenuopra getSysMenuopraByMenuopercode(String menuopercode) {
		return findUnique("from SysMenuopra where menuopercode = ? ", menuopercode);
	}

	public void delete(final List<String> opraids) {
		final String hql = "delete from SysMenuopra where opraid in (:opraids)";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("opraids", opraids);
				query.executeUpdate();
				return null;
			}
		});
	}
}
