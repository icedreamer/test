package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.equ.model.EquYcheckBureau;

@Repository
public class EquYcheckBureauDao extends _GenericDao<EquYcheckBureau> {

	public List<EquYcheckBureau> findEquYcheckBureau(final String yinspectionno) {
		String hql = "from EquYcheckBureau a where a.yinspectionno = ? "
				+ "order by a.yinspectionno,a.goodsid,a.bureaushortname ";
		return (List<EquYcheckBureau>) getHibernateTemplate().find(hql, yinspectionno);
	}

	public List<EquYcheckBureau> findEquYcheckBureau(final String yinspectionno,
			final List<String> goodsIdList) {
		if (null == goodsIdList || goodsIdList.isEmpty()) {
			return null;
		}
		final String hql = "from EquYcheckBureau a where a.yinspectionno = ? and a.goodsid in (:goodsIdList) "
				+ "order by a.yinspectionno,a.goodsid,a.bureaushortname ";
		return (List<EquYcheckBureau>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, yinspectionno);
				query.setParameterList("goodsIdList", goodsIdList);
				return query.list();
			}
		});

	}

	public void delete(final List<String> yinspectionnos) {
		if (null == yinspectionnos || yinspectionnos.isEmpty()) {
			return;
		}
		final String hql = "delete from EquYcheckBureau a where a.yinspectionno in (:yinspectionnos)";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("yinspectionnos", yinspectionnos);
				return query.executeUpdate();
			}
		});
	}

	public EquYcheckBureau getEquYcheckBureau(String yinspectionno, String goodsid, String bureauid) {
		String hql = "from EquYcheckBureau a where trim(a.yinspectionno) = ? and a.goodsid = ? and a.bureauid = ? ";
		List<EquYcheckBureau> list = getHibernateTemplate().find(hql,
				new String[] { yinspectionno, goodsid, bureauid });
		if (null != list && !list.isEmpty()) {
			return (EquYcheckBureau) list.get(0);
		}
		return null;
	}
}
