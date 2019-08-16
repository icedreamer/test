/**
 * 
 */
package com.tlys.dic.dao.ctl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;

/**
 * @author ¹ù½¨¾ü
 * 
 */
@Repository
public class CtlLiaisonocorpDao extends _GenericDao<CtlLiaisonocorp> {

	public List<CtlLiaisonocorp> find(final CtlLiaisonocorp CtlLiaisonocorp) {
		List<CtlLiaisonocorp> CtlLiaisonocorps = (List<CtlLiaisonocorp>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List<CtlLiaisonocorp> doInHibernate(Session session) throws HibernateException {
						List dacs = session.createCriteria(CtlLiaisonocorp.class)
								.add(Example.create(CtlLiaisonocorp).enableLike(MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return CtlLiaisonocorps;
	}

	public List<String> findCorpidsByBranchid(final String branchid) {
		final String hql = "select distinct a.corpid from CtlLiaisonocorp a where a.branchid = ? ";
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, branchid);
				return query.list();
			}
		});

	}
}
