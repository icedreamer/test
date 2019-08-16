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
 * AEI�г���ϢDao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeStntrnDao extends _GenericDao<ExeStntrn> {

	/**
	 * ��վ����ҵվ��)�г�������Ϣ�б�
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
	 * ��վ����ҵվ��)�г�������Ϣ����
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
	 * ������ʼ���ڲ�ѯ��վ����ҵվ��)�г�������Ϣ
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
	 * �����г���Ϣ
	 * 
	 * @param exeStntrn
	 */
	public void addExeStntrn(ExeStntrn exeStntrn) {
		getHibernateTemplate().save(exeStntrn);
	}

	/**
	 * �޸��г���Ϣ
	 * 
	 * @param exeStntrn
	 */
	public void updateExeStntrn(ExeStntrn exeStntrn) {
		exeStntrn = (ExeStntrn) getHibernateTemplate().merge(exeStntrn);
		getHibernateTemplate().update(exeStntrn);
	}

	/**
	 * ɾ���г���Ϣ
	 * 
	 * @param exeStntrn
	 */
	public void deleteExeStntrn(ExeStntrn exeStntrn) {
		getHibernateTemplate().delete(exeStntrn);
	}

	/**
	 * ȡ�����ı������
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
	 * ����id����id��ȫ
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
	 * ����rptname���Ҹ��г���Ϣ
	 * 
	 * @param rptname
	 * @return
	 */
	public ExeStntrn loadExeStntrn(String rptname) {
		return (ExeStntrn) getHibernateTemplate().get(ExeStntrn.class, rptname);
	}

	/**
	 * ���ݱ�����ȡ�ñ����ܳ������Ϳ�ʶ������
	 * 
	 * @param rptname
	 * @return
	 */
	public Long getStntrnCountByRptname(String rptname) {
		return (Long) getHibernateTemplate().find("select count(*) from ExeStntrn where rptname=?", rptname).get(0);
	}

}
