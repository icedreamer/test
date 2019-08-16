package com.tlys.spe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.spe.model.SpePackmark;

@Repository
public class SpePackmarkDao extends _GenericDao<SpePackmark> {

	public List<SpePackmark> find(final SpePackmark spePackmark){
		List<SpePackmark> markList = (List<SpePackmark>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<SpePackmark> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(SpePackmark.class);
				ca.addOrder(Order.desc("createdtime"));
				List<SpePackmark> markList = ca.add(Example.create(spePackmark)).list();
				return markList;
			}
		});
		
		return markList;
	}
	
	
	
	/**
	 * 从数据库得到序列转成String，供插入新记录时补零后使用
	 * @return
	 */
	public String getSeq() {
		final String sql = "select SEQ_TB_ZBC_SPE_PACKMARK.nextval from dual";
		Object o=getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s)
					throws HibernateException, SQLException {
				SQLQuery query = s.createSQLQuery(sql);
				return query.uniqueResult();
			}
		});
		return o.toString();
	}
}
