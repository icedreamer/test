package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.exe.model.ExeAreaDist;
import com.tlys.sys.model.SysUser;

/**
 * 数据区域分布Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeAreaDistDao extends _GenericDao<ExeAreaDist> {
	/**
	 * 根据日期，统计时间点查询车辆分布数据
	 * 
	 * @param stat_date
	 * @param stat_point
	 * @return
	 */
	@SuppressWarnings(value = { "unchecked" })
	public List<ExeAreaDist> listAreaDist(final String stat_date, final Integer stat_point,
			final String le_code) {
		return (List<ExeAreaDist>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeAreaDist.class);
				// /收货人：CON_NAME，模糊查询
				if (null != stat_date && !stat_date.equals("")) {
					ca.add(Restrictions.sqlRestriction("to_char(stat_date,'yyyy-MM-dd')= ? ",
							stat_date, Hibernate.STRING));
				}
				if (null != stat_point && !stat_point.equals("")) {
					ca.add(Restrictions.eq("id.stat_point", stat_point));
				}
				if (null != le_code) {
					ca.add(Restrictions.eq("id.le_code", le_code));
				}
				ca.addOrder(Order.asc("id.user_area_id"));
				ca.addOrder(Order.asc("id.car_user_id"));
				ca.addOrder(Order.asc("id.cur_adm"));
				return ca.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listAreaDist(String stat_date, int stat_point) {
		return (List<Object[]>) getHibernateTemplate()
				.find(
						"select id.user_area_id,id.car_user_id,id.cur_adm,sum(zbc_car_num) from ExeAreaDist where to_char(stat_date,'yyyy-mm-dd')=? and stat_point=? group by id.user_area_id,id.car_user_id,id.cur_adm order by id.user_area_id,id.car_user_id,id.cur_adm",
						new Object[] { stat_date, stat_point });
	}

	/**
	 * 获取重空等车在某个点的数据(生成饼状图使用)
	 * 
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findCarsOfCode(String date, int point) {
		final String sql = "select t.id.le_code, sum(t.zbc_car_num) from ExeAreaDist t where to_char(t.id.stat_date, 'yyyymmdd') = ? and t.id.stat_point = ? and "
				+ CommUtils.getCorpIds("t.id.car_user_id") + " group by t.id.le_code";
		return (List<Object[]>) getHibernateTemplate().find(sql, new Object[] { date, point });
	}

	/**
	 * 获取重空车一周数据(生成曲线图使用)
	 * 
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findWeekCarsOfLecode(String date, int point) {
		final String sql = "select to_char(t.id.stat_date, 'yyyymmdd'), t.id.le_code, sum(t.zbc_car_num) from ExeAreaDist t where to_char( t.id.stat_date,'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ? and t.id.stat_point = ? and "
				+ CommUtils.getCorpIds("t.id.car_user_id")
				+ " group by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd') order by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd') ";
		return (List<Object[]>) getHibernateTemplate()
				.find(sql, new Object[] { date, date, point });
	}

	/**
	 * 选择某个日期显示相应区域公司的车辆情况
	 * 
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findDayCarsOfAreas(String date, int point, SysUser sysUser) {
		String corptab = sysUser.getCorptab();
		String sql = "";
		// 总部权限
		if ("0".equals(corptab)) {
			sql = "select to_char(t.id.stat_date, 'yyyymmdd'), a.shortname, a.areaid, t.id.le_code, sum(t.zbc_car_num)	from ExeAreaDist t, DicAreacorp a where a.areaid = t.id.user_area_id and to_char(t.id.stat_date, 'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ?	and t.id.stat_point = ? and "
					+ CommUtils.getCorpIds("t.id.car_user_id")
					+ " group by to_char(t.id.stat_date, 'yyyymmdd'),a.areaid,a.shortname,t.id.le_code order by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd'), a.areaid, a.shortname ";
		} else if ("1".equals(corptab)) {
			// 区域公司权限
			sql = "select to_char(t.id.stat_date, 'yyyymmdd'), a.shrinkname, a.corpid, t.id.le_code, sum(t.zbc_car_num)	from ExeAreaDist t, DicSinocorp a where a.corpid = t.id.car_user_id and to_char(t.id.stat_date, 'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ?	and t.id.stat_point = ? and "
					+ CommUtils.getCorpIds("t.id.car_user_id")
					+ " group by to_char(t.id.stat_date, 'yyyymmdd'),a.corpid,a.shrinkname,t.id.le_code order by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd'), a.corpid, a.shrinkname ";
		} else if ("2".equals(corptab) || "3".equals(corptab)) {
			// 企业或驻厂办权限 // 下属部门
			// 此处需要增加车号字段或者车种字段才能到车种的统计，该处暂不准确
			// sql = "select to_char(t.id.stat_date, 'yyyymmdd'), b.shortname,
			// b.carkindid, t.id.le_code, sum(t.zbc_car_num) from ExeAreaDist t,
			// DicSinocorp a,DicCarkind b,EquCar c where a.corpid =
			// t.id.car_user_id and to_char(t.id.stat_date, 'yyyymmdd') between
			// to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ? AND
			// c.corpid = a.corpid AND c.carkindid = b.carkindid and
			// t.id.stat_point = ? and "
			// + CommUtils.getCorpIds("t.id.car_user_id")
			// + " group by to_char(t.id.stat_date,
			// 'yyyymmdd'),b.carkindid,b.shortname,t.id.le_code order by
			// t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd'), b.carkindid,
			// b.shortname ";
			sql = "select to_char(a.id.stat_date, 'yyyymmdd'),b.goodsname,b.goodsid,a.id.le_code,sum(a.zbc_car_num) from ExeAreaDist a,DicGoodscategory b where a.id.car_medium_id = b.goodsid and "
					+ CommUtils.getCorpIds("a.id.car_user_id")
					+ " and to_char(a.id.stat_date, 'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ? and a.id.stat_point = ? group by to_char(a.id.stat_date, 'yyyymmdd'),b.goodsname,b.goodsid,a.id.le_code order by a.id.le_code, to_char(a.id.stat_date, 'yyyymmdd'),b.goodsname ";
		}

		return (List<Object[]>) getHibernateTemplate()
				.find(sql, new Object[] { date, date, point });
	}

}
