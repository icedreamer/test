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
import com.tlys.dic.model.DicTrankMaterial;

/**
 * @author guojj
 * 
 */
@Repository
public class DicTrankMaterialDao extends _GenericDao<DicTrankMaterial> {

	public DicTrankMaterial load(String id) {
		DicTrankMaterial DicTrankMaterial = super.load(id);
		return DicTrankMaterial;
	}

	public List<DicTrankMaterial> find(final DicTrankMaterial DicTrankMaterial) {
		List<DicTrankMaterial> DicTrankMaterials = (List<DicTrankMaterial>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public List<DicTrankMaterial> doInHibernate(Session session)
							throws HibernateException {
						List dacs = session.createCriteria(
								DicTrankMaterial.class).add(
								Example.create(DicTrankMaterial).enableLike(
										MatchMode.ANYWHERE)).list();
						return dacs;
					}
				});

		return DicTrankMaterials;
	}

}
