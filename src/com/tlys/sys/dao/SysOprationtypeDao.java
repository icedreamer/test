package com.tlys.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysOprationtype;

/**
 * @author ������
 * 
 */
@Repository
public class SysOprationtypeDao extends _GenericDao<SysOprationtype> {

	/**
	 * ����ĳ���˵�������ɵĲ�������
	 * 
	 * @param menuid
	 * @return
	 */
	public List<SysOprationtype> findSysOprationtypeMenu(int menuid) {
		String hql = "select op from SysOprationtype op,SysMenuopra mo where " + "op.operid=mo.operid "
				+ "and mo.menuid=" + menuid;

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * ���ҵ�ǰ��ɫ�������ĳ���˵�������Ĳ�������
	 * 
	 * @param roleid
	 * @return
	 */
	public List<SysOprationtype> findSysOprationtypeRoleMenu(String roleid, int menuid) {
		String hql = "select op from SysOprationtype op,SysMenuopra mo where " + "op.operid=mo.operid and mo.opraid in"
				+ "(select rp.opraid from SysRoleperm rp where rp.roleid='" + roleid + "') " + "and mo.menuid="
				+ menuid;

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * ��ȡ���в�������
	 * 
	 * @return
	 */
	public List<SysOprationtype> findSysOprationType() {
		String hql = "from SysOprationtype order by operid desc ";
		return getHibernateTemplate().find(hql);
	}

}
