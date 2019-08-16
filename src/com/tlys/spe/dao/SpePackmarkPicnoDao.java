package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.spe.model.SpePackmarkPicno;

@Repository
public class SpePackmarkPicnoDao extends _GenericDao<SpePackmarkPicno> {

	public List findSpePackmarkPicno(final SpePackmarkPicno spePackmarkPicno){
		List<SpePackmarkPicno> picnoList = (List<SpePackmarkPicno>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<SpePackmarkPicno> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpePackmarkPicno.class);
				//buildCrit(ca,spePackmark);
				ca.addOrder(Order.desc("createdtime"));
				return ca.list();
			}
		});
		return picnoList;
	}
	
	public void deleteByMarkPicid(final String markpicid){
		final String hql = "delete from SpePackmarkPicno where markpicid = ? ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, markpicid);
				return query.executeUpdate();
			}
		});
	}

	
	
	
}
