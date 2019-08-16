package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.equ.model.EquYcheckStation;

@Repository
public class EquYcheckStationDao extends _GenericDao<EquYcheckStation> {

	public List<EquYcheckStation> findEquYcheckStation(final String yinspectionno) {
		String hql = "from EquYcheckStation a where a.yinspectionno = ? "
				+ "order by a.yinspectionno,a.yinspectionbureauid";
		return (List<EquYcheckStation>) getHibernateTemplate().find(hql, yinspectionno);
	}

	public void delete(final String yinspectionno) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String hql = "delete from EquYcheckStation a where a.yinspectionno = ? ";
				Query query = s.createQuery(hql);
				query.setString(0, yinspectionno);
				return query.executeUpdate();
			}
		});
	}

	public void delete(final List<String> yinspectionnos) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				String hql = "delete from EquYcheckStation a where a.yinspectionno in (:yinspectionnos) ";
				Query query = s.createQuery(hql);
				query.setParameterList("yinspectionnos", yinspectionnos);
				return query.executeUpdate();
			}
		});
	}

	public List<EquYcheckStation> getEquYcheckStation(String yinspectionbureauid) {
		String hql = "from EquYcheckStation a where a.yinspectionbureauid = ? ";
		return (List<EquYcheckStation>) getHibernateTemplate().find(hql, yinspectionbureauid);
	}
}
