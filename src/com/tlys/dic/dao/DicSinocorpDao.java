/**
 * 
 */
package com.tlys.dic.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicSinocorp;

/**
 * @author 冯彦明
 * 
 */
@Repository
public class DicSinocorpDao extends _GenericDao<DicSinocorp> {

	public DicSinocorp load(String id) {
		DicSinocorp dicSinocorp = super.load(id);
		return dicSinocorp;
	}

	public List<DicSinocorp> findByAreaid(String areaid) {
		return this.findByProperty("areaid", areaid);
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<DicSinocorp> find(final DicSinocorp dicSinocorp) {
		List<DicSinocorp> dicSinocorps = (List<DicSinocorp>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<DicSinocorp> doInHibernate(final Session session) throws HibernateException {
				Criteria crit = session.createCriteria(DicSinocorp.class);
				crit = crit.add(Example.create(dicSinocorp).enableLike(MatchMode.ANYWHERE));
				if (null == dicSinocorp.getCorptype()) {
					// 加上要查询的是石化企业这个条件,当这个查询条件为空时，表示当前为普通企业，特别加上如下的查询条件
					crit.add(Restrictions.or(Restrictions.isNull("corptype"), Restrictions.eq("corptype", "1")));
				}

				String provids = dicSinocorp.getProvids();
				if (null != provids) {
					crit.add(Restrictions.in("provinceid", provids.split(", ")));
				}
				crit.addOrder(Order.asc("areaid"));
				crit.addOrder(Order.asc("shortname"));
				final List dacs = crit.list();
				return dacs;
			}
		});

		return dicSinocorps;
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<DicSinocorp> findById(final DicSinocorp DicSinocorp) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria ca = session.createCriteria(DicSinocorp.class);
				String corpid = DicSinocorp.getCorpid();
				if (null != corpid && !"".equals(corpid))
					ca.add(Restrictions.eq("corpid", corpid));
				return ca.list();
			}
		});
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<DicSinocorp> find4view(String viewname) {
		String hql = "from DicSinocorp d where d.isactive='1' and d.is" + viewname
				+ "='0' and d.corptype='1' order by d.shortname";
		return this.getHibernateTemplate().find(hql);
	}

	public List<DicSinocorp> find4usercorpauth() {
		String hql = "from DicSinocorp d where d.isactive='1' and (d.corptype is null or d.corptype='1') order by d.shortname";
		return this.getHibernateTemplate().find(hql);
	}

	public void saveOrUpdateAll(List sinocorps) {
		getHibernateTemplate().saveOrUpdateAll(sinocorps);
	}

	public List<DicSinocorp> findDicSinocorp() {
		String hql = "from DicSinocorp where " + CommUtils.getCorpIds("corpid")
				+ " and corptype = '1' order by areaid,shortname ";
		return (List) find(hql);
	}

	public List<DicSinocorp> findDicSinocorpAll() {
		String hql = "from DicSinocorp where " + CommUtils.getCorpIds("corpid") + " order by areaid,shortname ";
		return (List) find(hql);
	}

	public List<DicSinocorp> findDicSinocorp(final String areaid, final String corpid) {
		return (List<DicSinocorp>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(DicSinocorp.class);
				if (null != areaid && !"".equals(areaid)) {
					criteria.add(Restrictions.eq("areaid", areaid));
				}
				if (null != corpid && !"".equals(corpid)) {
					criteria.add(Restrictions.eq("corpid", corpid));
				}

				// 加上要查询的是石化企业这个条件,当这个查询条件为空时，表示当前为普通企业，特别加上如下的查询条件
				criteria.add(Restrictions.or(Restrictions.isNull("corptype"), Restrictions.eq("corptype", "1")));

				criteria.addOrder(Order.asc("areaid"));
				criteria.addOrder(Order.asc("shortname"));
				return criteria.list();
			}
		});
	}

	/**
	 * 查找当前用户有权限操作的企业
	 * 
	 * @param userid
	 * @return
	 */
	public List<DicSinocorp> findSinocorps(String userid) {
		String hql = "from DicSinocorp ds where ds.corpid in"
				+ "(select sug.datacorpid from SysUserdatas sug where sug.userid='" + userid
				+ "') order by ds.shortname";

		List<DicSinocorp> rlist = this.getHibernateTemplate().find(hql);
		return rlist;
	}

	public DicSinocorp getDicSinocorpByName(final String corpName) {
		return (DicSinocorp) getHibernateTemplate().execute(new HibernateCallback() {
			final String hql = "from DicSinocorp where fullname = ? ";

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, corpName);
				List<DicSinocorp> list = query.list();
				if (null != list && !list.isEmpty()) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public List<String> findCorpIdsByAreaId(final String areaid) {
		final String hql = "select distinct a.corpid from DicSinocorp a where a.areaid = ? ";
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setString(0, areaid);
				return query.list();
			}
		});
	}
}
