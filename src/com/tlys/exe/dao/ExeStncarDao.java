package com.tlys.exe.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericDao;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageView;
import com.tlys.exe.model.ExeStncar;
import com.tlys.exe.model.ExeStncarId;

/**
 * AEI车辆信息Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeStncarDao extends _GenericDao<ExeStncar> {

	/**
	 * 根据报文名查询车站（企业站区)车辆出入信息
	 * 
	 * @param rptname
	 * @param pageUrl
	 * @param totalRecord
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExeStncar> listStncar(final String rptname) {
		return (List<ExeStncar>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeStncar.class);
				ca.add(Restrictions.eq("id.rptname", rptname));
				ca.addOrder(Order.asc("id.car_position"));
				return ca.list();
			}
		});
	}

	/**
	 * 分页查询车辆信息
	 * 
	 * @param field
	 * @param pageUrl
	 * @param totalRecord
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageView<ExeStncar> searchStncar(final ExeAeiSearchCarField field, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeStncar>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeStncar.class);
				ca = queryStncar(ca, field);
				ca.addOrder(Order.desc("arr_time"));
				ca.addOrder(Order.asc("id.car_position"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * 查询车辆数
	 * 
	 * @param field
	 * @return
	 */
	public int getListStncarCount(final ExeAeiSearchCarField field) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeStncar.class);
				ca = queryStncar(ca, field);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	public Criteria queryStncar(Criteria ca, ExeAeiSearchCarField field) {
		if (null != field.getS_date() && !field.getS_date().equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(arr_time,'yyyy-MM-dd') >= ? ", field.getS_date(),
					Hibernate.STRING));
		}
		if (null != field.getE_date() && !field.getE_date().equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(arr_time,'yyyy-MM-dd') <= ? ", field.getE_date(),
					Hibernate.STRING));
		}
		// field.setCar_no(car_no);
		// field.setStn_entpr_id(stn_entpt_id);
		// field.setArea_id(area_id);
		// field.setCar_medium_id(car_medium_id);
		// field.setZbc_flag(zbc_flag);
		//		
		// 车号
		if (null != field.getCar_no() && !field.getCar_no().equals("")) {
			ca.add(Restrictions.eq("car_no", field.getCar_no()));
		}
		// 查询类型,T-部属车，Q-企业自备车，
		if (null != field.getZbc_flag() && !field.getZbc_flag().equals("")) {
			String car_flag = "";
			if (field.getZbc_flag().equals("0"))
				car_flag = "T";
			else if (field.getZbc_flag().equals("1"))
				car_flag = "Q";
			ca.add(Restrictions.eq("car_flag", car_flag));
		}

		// 所属企业
		if (null != field.getStn_entpr_id() && !field.getStn_entpr_id().equals("")) {
			String[] stn_entpr_idArr = field.getStn_entpr_id().split(",");
			String corpid = "";
			for (String stn_entpr_id : stn_entpr_idArr)
				corpid += "'" + stn_entpr_id + "',";
			if (corpid.length() > 0)
				corpid = corpid.substring(0, corpid.length() - 1);
			ca
					.add(Restrictions
							.sqlRestriction("this_.stn_name in (select entpr_stn_code from tb_zbc_dic_entpr_stn where entpr_id in ("
									+ corpid + "))"));
		}

		// 充装介质
		if (null != field.getCar_medium_id() && !field.getCar_medium_id().equals("")) {
			String[] car_medium_idArr = field.getCar_medium_id().split(",");
			String car_medium_id = "";
			for (String str : car_medium_idArr)
				car_medium_id += "'" + str + "',";
			if (car_medium_id.length() > 0)
				car_medium_id = car_medium_id.substring(0, car_medium_id.length() - 1);
			ca.add(Restrictions.sqlRestriction("this_.car_no in (select carno from TB_ZBC_EQU_CAR where GOODSID in ("
					+ car_medium_id + "))"));
		}

		// 区域
		if (null != field.getArea_id() && !field.getArea_id().equals("")) {
			String[] area_idArr = field.getArea_id().split(",");
			String area_id = "";
			for (String str : area_idArr)
				area_id += "'" + str + "',";
			if (area_id.length() > 0)
				area_id = area_id.substring(0, area_id.length() - 1);
			ca
					.add(Restrictions
							.sqlRestriction("this_.stn_name in (select entpr_stn_code from tb_zbc_dic_entpr_stn where entpr_id in (select corpid from tb_zbc_dic_sinocorp where areaid in ("
									+ area_id + ")))"));
		}
		// 用户默认企业数据权限
		ca.add(Restrictions.sqlRestriction("this_.stn_name in (select entpr_stn_code from tb_zbc_dic_entpr_stn where  "
				+ CommUtils.getCorpIds("entpr_id") + ")"));
		return ca;

	}

	/**
	 * 增加车辆信息
	 * 
	 * @param exeStncar
	 */
	public void addExeStncar(ExeStncar exeStncar) {
		getHibernateTemplate().save(exeStncar);
	}

	/**
	 * 修改车辆信息
	 * 
	 * @param exeStncar
	 */
	public void updateExeStncar(ExeStncar exeStncar) {
		exeStncar = (ExeStncar) getHibernateTemplate().merge(exeStncar);
		getHibernateTemplate().update(exeStncar);
	}

	/**
	 * 删除车辆信息
	 * 
	 * @param exeStncar
	 */
	public void deleteExeStncar(ExeStncar exeStncar) {
		getHibernateTemplate().delete(exeStncar);
	}

	/**
	 * 根据报文名、顺位查车辆信息
	 * 
	 * @param rptname
	 * @param car_position
	 * @return
	 */
	public ExeStncar loadExeStncar(String rptname, Integer car_position) {
		return (ExeStncar) getHibernateTemplate().get(ExeStncar.class, new ExeStncarId(rptname, car_position));
	}

	/**
	 * 根据报文名获取车辆顺位
	 * 
	 * @param rptname
	 * @return
	 */
	public int getCar_position(String rptname) {
		String hql = "select max(id.car_position) from ExeStncar where rptname=?";
		List<Object> list = getHibernateTemplate().find(hql, rptname);
		int maxId = 0;
		if (list.get(0) != null)
			maxId = Integer.parseInt(list.get(0).toString()) + 1;
		else
			maxId = 1;
		return maxId;
	}

}
