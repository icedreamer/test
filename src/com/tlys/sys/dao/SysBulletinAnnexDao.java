package com.tlys.sys.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.sys.model.SysBulletinAnnex;

/**
 * 通知公告附件信息
 * 
 * @author ccsong 2012-5-29 下午3:42:27
 */
@Repository
public class SysBulletinAnnexDao extends _GenericDao<SysBulletinAnnex> {

	public List<SysBulletinAnnex> findSysBulletinAnnexByBulletinId(final Long bulletinid) {
		final String sql = "from SysBulletinAnnex where bulletinid = ? order by uploadtime desc ,annexname";
		return (List<SysBulletinAnnex>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(sql);
				query.setLong(0, bulletinid);
				return query.list();
			}
		});
	}
}
