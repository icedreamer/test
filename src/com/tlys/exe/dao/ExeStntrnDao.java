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
import com.tlys.comm.util.page.PageView;
import com.tlys.exe.model.ExeStntrn;

/**
 * AEI列车信息Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeStntrnDao extends _GenericDao<ExeStntrn> {

	/**
	 * 车站（企业站区)列车出入信息列表
	 */
	@SuppressWarnings("unchecked")
	public PageView<ExeStntrn> listStntrn(final String s_date, final String e_date, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return (PageView<ExeStntrn>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeStntrn.class);
				ca = queryStntrn(ca, s_date, e_date);
				ca.addOrder(Order.desc("arr_time"));
				PageView page = new PageView(ca, pageUrl, totalRecord, currentPage, pageSize);
				return page;
			}
		});
	}

	/**
	 * 车站（企业站区)列车出入信息数量
	 * 
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public int getListStntrnCount(final String s_date, final String e_date) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(ExeStntrn.class);
				ca = queryStntrn(ca, s_date, e_date);
				ca.setProjection(Projections.rowCount());
				Object o = ca.uniqueResult();
				return o == null || o.toString().equals("") ? 0 : Integer.parseInt(o.toString());
			}
		});
	}

	/**
	 * 根据起始日期查询车站（企业站区)列车出入信息
	 * 
	 * @param ca
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public Criteria queryStntrn(Criteria ca, String s_date, String e_date) {
		if (null != s_date && !s_date.equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(arr_time,'yyyy-MM-dd') >= ? ", s_date, Hibernate.STRING));
		}
		if (null != e_date && !e_date.equals("")) {
			ca.add(Restrictions.sqlRestriction("to_char(arr_time,'yyyy-MM-dd') <= ? ", e_date, Hibernate.STRING));
		}
		return ca;
	}

	/**
	 * 增加列车信息
	 * 
	 * @param exeStntrn
	 */
	public void addExeStntrn(ExeStntrn exeStntrn) {
		getHibernateTemplate().save(exeStntrn);
	}

	/**
	 * 修改列车信息
	 * 
	 * @param exeStntrn
	 */
	public void updateExeStntrn(ExeStntrn exeStntrn) {
		exeStntrn = (ExeStntrn) getHibernateTemplate().merge(exeStntrn);
		getHibernateTemplate().update(exeStntrn);
	}

	/**
	 * 删除列车信息
	 * 
	 * @param exeStntrn
	 */
	public void deleteExeStntrn(ExeStntrn exeStntrn) {
		getHibernateTemplate().delete(exeStntrn);
	}

	/**
	 * 取得最大的报文序号
	 * 
	 * @return
	 */
	public String getMaxRptId(String rptName) {
		String hql = "select max(substr(rptname,6,3)) from ExeStntrn where substr(rptname,0,5)=?";
		List<Object> list = getHibernateTemplate().find(hql, rptName);
		String maxId = "";
		if (list.get(0) != null)
			maxId = dealId(Integer.parseInt(list.get(0).toString()) + 1);
		else
			maxId = "001";
		return maxId;
	}

	/**
	 * 处理id，给id补全
	 * 
	 * @param id
	 * @return
	 */
	private String dealId(int id) {
		if (id < 10)
			return "00" + id;
		else if (id < 100)
			return "0" + id;
		else if (id < 1000)
			return String.valueOf(id);
		else
			return "001";
	}

	/**
	 * 根据rptname查找该列车信息
	 * 
	 * @param rptname
	 * @return
	 */
	public ExeStntrn loadExeStntrn(String rptname) {
		return (ExeStntrn) getHibernateTemplate().get(ExeStntrn.class, rptname);
	}

	/**
	 * 根据报文名取得报文总车辆数和可识别车辆数
	 * 
	 * @param rptname
	 * @return
	 */
	public Long getStntrnCountByRptname(String rptname) {
		return (Long) getHibernateTemplate().find("select count(*) from ExeStntrn where rptname=?", rptname).get(0);
	}

}
