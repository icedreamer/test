/**
 * 
 */
package com.tlys.dic.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicGoogsreceiver;

/**
 * @author ·ëÑåÃ÷
 * 
 */
@Repository
public class DicGoogsreceiverDao extends _GenericDao<DicGoogsreceiver> {

	public DicGoogsreceiver load(String id) {
		DicGoogsreceiver dicGoogsreceiver = super.load(id);
		return dicGoogsreceiver;
	}
	
	public List<DicGoogsreceiver> findByParentid(String pid){
		return this.findByProperty("parentid", pid);
	}
	
	public List<DicGoogsreceiver> find(final DicGoogsreceiver dicGoogsreceiver) {
		List<DicGoogsreceiver> dicGoogsreceivers = (List<DicGoogsreceiver>)getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicGoogsreceiver> doInHibernate(Session session)
					throws HibernateException {
				List dacs = session.createCriteria(
						DicGoogsreceiver.class).add(Example.create(dicGoogsreceiver).enableLike(MatchMode.ANYWHERE))
						.list();
				return dacs;
			}
		});

		return dicGoogsreceivers;
	}

}
