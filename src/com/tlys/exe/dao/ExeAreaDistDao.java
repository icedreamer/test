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
 * ��������ֲ�Dao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeAreaDistDao extends _GenericDao<ExeAreaDist> {
	/**
	 * �������ڣ�ͳ��ʱ����ѯ�����ֲ�����
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
				// /�ջ��ˣ�CON_NAME��ģ����ѯ
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
	 * ��ȡ�ؿյȳ���ĳ���������(���ɱ�״ͼʹ��)
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
	 * ��ȡ�ؿճ�һ������(��������ͼʹ��)
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
	 * ѡ��ĳ��������ʾ��Ӧ����˾�ĳ������
	 * 
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findDayCarsOfAreas(String date, int point, SysUser sysUser) {
		String corptab = sysUser.getCorptab();
		String sql = "";
		// �ܲ�Ȩ��
		if ("0".equals(corptab)) {
			sql = "select to_char(t.id.stat_date, 'yyyymmdd'), a.shortname, a.areaid, t.id.le_code, sum(t.zbc_car_num)	from ExeAreaDist t, DicAreacorp a where a.areaid = t.id.user_area_id and to_char(t.id.stat_date, 'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ?	and t.id.stat_point = ? and "
					+ CommUtils.getCorpIds("t.id.car_user_id")
					+ " group by to_char(t.id.stat_date, 'yyyymmdd'),a.areaid,a.shortname,t.id.le_code order by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd'), a.areaid, a.shortname ";
		} else if ("1".equals(corptab)) {
			// ����˾Ȩ��
			sql = "select to_char(t.id.stat_date, 'yyyymmdd'), a.shrinkname, a.corpid, t.id.le_code, sum(t.zbc_car_num)	from ExeAreaDist t, DicSinocorp a where a.corpid = t.id.car_user_id and to_char(t.id.stat_date, 'yyyymmdd') between to_char(to_date(?, 'yyyymmdd') - 6, 'yyyymmdd') and ?	and t.id.stat_point = ? and "
					+ CommUtils.getCorpIds("t.id.car_user_id")
					+ " group by to_char(t.id.stat_date, 'yyyymmdd'),a.corpid,a.shrinkname,t.id.le_code order by t.id.le_code, to_char(t.id.stat_date, 'yyyymmdd'), a.corpid, a.shrinkname ";
		} else if ("2".equals(corptab) || "3".equals(corptab)) {
			// ��ҵ��פ����Ȩ�� // ��������
			// �˴���Ҫ���ӳ����ֶλ��߳����ֶβ��ܵ����ֵ�ͳ�ƣ��ô��ݲ�׼ȷ
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
