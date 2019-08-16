/**
 * 
 */
package com.tlys.equ.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquCar;
import com.tlys.sys.model.SysUser;

/**
 * @author 郭建军
 * 
 */
@Repository
public class EquCarDao extends _GenericDao<EquCar> {
	@Autowired
	DicMap dicMap;

	/**
	 * 按区域公司-企业-车号排序列表显示
	 * 
	 * @param obj
	 * @param pageCtr
	 */
	public void findPageCtr(final EquCar obj, final PageCtr pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				final String sql = "SELECT A.* FROM TB_ZBC_EQU_CAR A,TB_ZBC_DIC_SINOCORP B " + buildWhere(obj)
						+ " AND A.CORPID=B.CORPID ORDER BY B.AREAID,B.CORPID,A.CARNO";
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity("A", EquCar.class);
				pageCtr.buildList(query);
				return null;
			}
		});
	}

	public int getObjCount(final EquCar obj) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select count(A.carno) from EquCar A " + buildWhere(obj);
				Query query = session.createQuery(sql);
				Object o = query.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});

	}

	/**
	 * 生成查询hql
	 * 
	 * @param spePackmarkPic
	 * @return
	 */
	public String buildWhere(EquCar equCar) {
		String hql = " where 1=1 ";
		if (equCar != null) {
			if (equCar.getCarno() != null && equCar.getCarno().length() > 0) {
				hql += " and A.carno like '" + equCar.getCarno() + "%'";
			}
			if (equCar.getIsexpire() != null && !equCar.getIsexpire().equals("-1") && !equCar.getIsexpire().equals("")) {
				hql += " and A.isexpire = '" + equCar.getIsexpire() + "'";
			}
			if (equCar.getCartypeid() != null && equCar.getCartypeid().length() > 0) {
				hql += " and A.cartypeid in (" + CommUtils.StringJoinQuotation(equCar.getCartypeid()) + ")";
			}
			if (equCar.getCarkindid() != null && equCar.getCarkindid().length() > 0) {
				hql += " and A.carkindid in (" + CommUtils.StringJoinQuotation(equCar.getCarkindid()) + ")";
			}
			if (equCar.getCorpid() != null && equCar.getCorpid().length() > 0) {
				hql += " and A.corpid in (" + CommUtils.StringJoinQuotation(equCar.getCorpid()) + ")";
			}
			if (equCar.getGoodsid() != null && equCar.getGoodsid().length() > 0) {
				hql += " and A.goodsid in (" + CommUtils.StringJoinQuotation(equCar.getGoodsid()) + ")";
			}
			if (equCar.getTankmateid() != null && equCar.getTankmateid().length() > 0) {
				hql += " and A.tankmateid in (" + CommUtils.StringJoinQuotation(equCar.getTankmateid()) + ")";
			}
			if (equCar.getCarmakerid() != null && equCar.getCarmakerid().length() > 0) {
				hql += " and A.carmakerid in (" + CommUtils.StringJoinQuotation(equCar.getCarmakerid()) + ")";
			}
			if (equCar.getMadedate() != null && !equCar.getMadedate().equals("")) {
				hql += " and substr(A.madedate,0,6) = '" + equCar.getMadedate() + "' ";
			}
		}
		/**
		 * 租赁方式使用（作废) if (equCar.getCarnos() != null &&
		 * equCar.getCarnos()[0].trim().length > 0) {
		 * System.out.println("################################查询条件："
		 * +equCar.getCarnos().length); hql += " and A.carno in (" +
		 * CommUtils.getSqlIn(equCar.getCarnos()) + ")"; }
		 */
		Object[] corpids = CommUtils.getCorpId();
		if (null != corpids) {
			hql += " and " + CommUtils.getCorpIds("A.corpid");
		}
		return hql;
	}

	/**
	 * 导出使用
	 * 
	 * @param obj
	 * @return
	 */
	public List<EquCar> find(final EquCar obj) {
		List<EquCar> equCars = (List<EquCar>) getHibernateTemplate().execute(new HibernateCallback() {
			public List<EquCar> doInHibernate(Session session) throws HibernateException {
				final String sql = "SELECT A.* FROM TB_ZBC_EQU_CAR A,TB_ZBC_DIC_SINOCORP B " + buildWhere(obj)
						+ " AND A.CORPID=B.CORPID ORDER BY B.AREAID,B.CORPID,A.CARNO";
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity("A", EquCar.class);
				return query.list();
			}
		});
		return equCars;
	}

	/**
	 * 查找指定hql的实体集合
	 * 
	 * @param hql
	 * @return List
	 */
	public List<Object> find(final String hql) {
		List<Object> Objects = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				// System.out.println("EquCarDao.find(...).new
				// HibernateCallback() {...}.doInHibernate->hql=="+hql);
				List dacs = session.createQuery(hql).list();
				return dacs;
			}
		});
		return Objects;
	}

	/**
	 * 查找指定sql的实体属性值集合
	 * 
	 * @param hql
	 * @return List
	 */
	public List<String[]> query(final String hql) {
		List<String[]> Objects = (List<String[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				List dacs = session.createQuery(hql).list();
				return dacs;
			}
		});
		return Objects;
	}

	/**
	 * 查找指定sql的集合
	 * 
	 * @param hql
	 * @return List
	 */
	public List<String[]> querySql(final String hql) {
		List<String[]> Objects = (List<String[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(Session session) throws HibernateException {
				List<String[]> dacs = session.createSQLQuery(hql).list();
				return dacs;
			}
		});
		return Objects;
	}

	/**
	 * add by fengym 联合查询，得到数组list,即每个list中是一个数组，长度为4，放置的是查出来的四个对象
	 * 这四个对象共享一个主键（carno)
	 * 
	 * @return
	 */
	public List queryArr() {

		String hql = "select car,np,hp,reg from EquCar car,EquCarNpinfo np,EquCarHpinfo hp,EquCarHpreginfo reg "
				+ "where car.carno=np.carno and np.carno=hp.carno and hp.carno=reg.carno";

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	public List<EquCar> findEquCarByCorpid(String corpid) {
		String hql = "from EquCar where corpid = ? order by carno ";
		return (List) find(hql, corpid);
	}

	/**
	 * 查询企业车辆信息，不包括carnoList 这些车 且没有报废的车
	 * 
	 * @param corpid
	 * @param carnoList
	 * @return
	 */
	public List<EquCar> findEquCar(final String corpid, final List<String> carnoList) {

		return (List<EquCar>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquCar.class);
				criteria.add(Restrictions.eq("corpid", corpid));
				if (null != carnoList && !carnoList.isEmpty()) {
					criteria.add(Restrictions.not(Restrictions.in("carno", carnoList)));
				}
				criteria.add(Restrictions.eq("isexpire", "0"));
				return criteria.list();
			}
		});
	}

	/**
	 * 车辆检修使用
	 * 
	 * @param equCar
	 * @param pageCtr
	 */
	public void findEquCar(final EquCar equCar, final PageCtr<EquCar> pageCtr) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquCar.class, "a");
				buildCriteria(equCar, criteria);
				criteria.addOrder(Order.asc("corpid"));
				criteria.addOrder(Order.asc("carno"));
				criteria.addOrder(Order.asc("cartypeid"));
				pageCtr.buildList(criteria);
				return null;
			}
		});
	}

	/**
	 * 车辆检修使用
	 * 
	 * @param equCar
	 */
	public int getEquCarCount(final EquCar equCar) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria criteria = s.createCriteria(EquCar.class, "a");
				buildCriteria(equCar, criteria);
				criteria.setProjection(Projections.rowCount());
				Object o = criteria.uniqueResult();
				return Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 车辆检修使用
	 * 
	 * @param equCar
	 * @param Criteria
	 */
	public void buildCriteria(EquCar equCar, Criteria criteria) {
		String carno = null;
		String carkindids = null;
		String cartypeids = null;
		String corpids = null;
		Object[] carnos = null;
		if (null != equCar) {
			carno = equCar.getCarno();
			carkindids = equCar.getCarkindids();
			cartypeids = equCar.getCartypeids();
			corpids = equCar.getCorpids();
			carnos = equCar.getCarnos();
		}
		if (null != carnos && carnos.length > 0) {
			// StringBuilder buffer = new StringBuilder("(");
			// for (int i = 0; i < carnos.length; i++) {
			// buffer.append("carno = ").append(carnos[i]);
			// if (i + 1 != carnos.length) {
			// buffer.append(" or ");
			// }
			// }
			// buffer.append(")");
			// criteria.add(Restrictions.sqlRestriction(buffer.toString()));
			criteria.add(Restrictions.in("carno", carnos));
		}
		if (null != carno && !"".equals(carno)) {
			criteria.add(Restrictions.like("carno", "%" + carno + "%"));
		}
		if (null != cartypeids && !"".equals(cartypeids)) {
			criteria.add(Restrictions.in("cartypeid", cartypeids.split(",")));
		}
		if (null != carkindids && !"".equals(carkindids)) {
			criteria.add(Restrictions.in("carkindid", carkindids.split(",")));
		}
		if (null != corpids && !"".equals(corpids)) {
			criteria.add(Restrictions.in("corpid", corpids.split(",")));
		} else {
			criteria.add(Restrictions.sqlRestriction(CommUtils.getCorpIds("corpid")));
			// criteria.add(Restrictions.sqlRestriction(CommUtils.userCorpAuthHql(CommUtils.getCurUser(),
			// dicMap)));
		}

		criteria.add(Restrictions.eq("isexpire", "0"));
		criteria.add(Restrictions.eq("iscentralize", "1"));
	}

	public List getCorpids() {
		String hql = "select distinct(c.corpid) as corpid,c.corpshrinkname from EquCar c ";
		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 
	 * @return
	 */
	public List getCarNumByCorp(String corpid) {
		String hql = "select c.goodsid,count(c.carno) as carnum from EquCar c " + "where c.corpid='" + corpid + "' group by c.goodsid";

		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	public List findGoodsids() {
		String hql = "select distinct(c.goodsid) as goodsid from EquCar c ";
		List rlist = this.getHibernateTemplate().find(hql);

		return rlist;
	}

	/**
	 * 获取每个区域公司的下属企业都有多少辆车
	 * 
	 * @return
	 */
	public List<Object[]> findCarsGroupByAreaId(SysUser sysUser) {
		String corptab = sysUser.getCorptab();
		String sql = "";
		if ("0".equals(corptab)) {
			// 总部权限
			sql = "select b.areaid,c.shortname ,count(a.carno) from EquCar a, DicSinocorp b, DicAreacorp c where a.corpid = b.corpid and b.areaid = c.areaid and "
					+ CommUtils.getCorpIds("a.corpid") + " group by b.areaid,c.shortname ";
		} else if ("1".equals(corptab)) {
			// 区域公司权限
			sql = "select b.corpid,b.shrinkname,count(a.carno) from EquCar a, DicSinocorp b where a.corpid = b.corpid and "
					+ CommUtils.getCorpIds("a.corpid") + " group by b.corpid,b.shrinkname ";
		} else if ("2".equals(corptab) || "3".equals(corptab)) {
			// 企业或驻厂办权限
			sql = "select c.carkindid,c.shortname,count(a.carno) from EquCar a, DicSinocorp b,DicCarkind c where a.corpid = b.corpid and "
					+ CommUtils.getCorpIds("a.corpid") + " and c.carkindid = a.carkindid group by c.carkindid,c.shortname ";
		}
		return getHibernateTemplate().find(sql);
	}

	public List<EquCar> findEquCar(final List<String> carnoList) {
		final String hql = "from EquCar a where a.carno in (:carnoList) and a.isexpire = 0 ";
		return (List<EquCar>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setParameterList("carnoList", carnoList);
				return query.list();
			}
		});
	}
}
