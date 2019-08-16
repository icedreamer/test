package com.tlys.dic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.dic.model.DicCarcost;

@Repository
public class DicCarcostDao extends _GenericDao<DicCarcost> {
	public List<DicCarcost> findDicCarcost() {
		return getHibernateTemplate().find("from DicCarcost a order by a.costid ");
	}
}
