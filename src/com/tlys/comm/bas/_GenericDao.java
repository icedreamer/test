package com.tlys.comm.bas;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.GenericsUtils;
import com.tlys.comm.util.page2.Page;

/**
 * @author fengym
 * @param <T>
 * 
 * DAO���װ�ӿ�ʵ���࣬�������õ�CURD�ͷ�ҳ������ʹ�÷���
 */
public class _GenericDao<T> extends HibernateDaoSupport {
	protected final Log log = LogFactory.getLog(this.getClass());

	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected String entityClassName = getEntityName(this.entityClass);
	protected String keyFieldName = getKeyFieldName(this.entityClass);

	/**
	 * ����ָ��ʵ����
	 * 
	 * @param entityobj
	 *            ʵ����
	 */
	public void save(T entity) {
		try {
			getHibernateTemplate().save(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("����ʵ����ɹ�," + entityClassName);
			}
		} catch (RuntimeException e) {
			logger.error("����ʵ���쳣," + entityClassName, e);
			throw e;
		}
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("����ʵ����ɹ�," + entityClassName);
		}
	}

	/**
	 * ɾ��ָ��ʵ��
	 * 
	 * @param entityobj
	 *            ʵ����
	 */
	public void delete(T entity) {
		try {
			getHibernateTemplate().delete(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("ɾ��ʵ����ɹ�," + entityClassName);
			}
		} catch (RuntimeException e) {
			logger.error("ɾ��ʵ���쳣", e);
			throw e;
		}
	}

	/**
	 * ����ID�б�ɾ��ʵ��
	 * 
	 * @param entityids
	 */
	public void delete(Serializable... entityids) {
		for (Object id : entityids) {
			Object obj = load((Serializable) id);
			if (obj != null)
				super.getHibernateTemplate().delete(obj);
		}
	}

	/**
	 * ��ȡ����ʵ�弯��
	 * 
	 * @param entityClass
	 *            ʵ��
	 * @return ����
	 */
	public List<T> findAll() {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("��������ʵ�弯�ϣ�" + entityClassName);
			}
			return getHibernateTemplate().find("from " + entityClassName);
		} catch (RuntimeException e) {
			logger.error("����ָ��ʵ�弯���쳣��ʵ�壺" + entityClassName, e);
			throw e;
		}
	}

	/**
	 * ��ȡ����ʵ�弯��,ͬʱ�������������
	 * 
	 * @param entityClass
	 *            ʵ��
	 * @return ����
	 */
	public List<T> findAll(String extHql) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("����ָ��ʵ�弯�ϣ�" + entityClassName);
			}
			return getHibernateTemplate().find("from " + entityClassName + extHql);
		} catch (RuntimeException e) {
			logger.error("����ָ��ʵ�弯���쳣��ʵ�壺" + entityClassName, e);
			// e.printStackTrace();
			throw e;
		}
	}

	public List<T> findAlls(String order) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("����ָ��ʵ�弯�ϣ�" + entityClassName);
			}
			return getHibernateTemplate().find(
					"from " + entityClassName + " as model order by model." + order);
		} catch (RuntimeException e) {
			logger.error("����ָ��ʵ�弯���쳣��ʵ�壺" + entityClassName, e);
			// e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ���»򱣴�ָ��ʵ��
	 * 
	 * @param entity
	 *            ʵ����
	 */
	public void saveOrUpdate(T entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("���»��߱���ʵ��ɹ�," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("���»򱣴�ʵ���쳣", e);
			throw e;
		}
	}

	/**
	 * ����ָ��IDʵ�������
	 * 
	 * @param entityClass
	 *            ʵ��Class
	 * @param id
	 *            ʵ��ID
	 * @return ʵ�����
	 */
	public T load(Serializable id) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ����IDΪ" + id + "��ʵ�壺" + entityClass.getName());
			}
			return (T) getHibernateTemplate().get(entityClass, id);
		} catch (RuntimeException e) {
			logger.error("����ָ��IDʵ���쳣��ID��" + id, e);
			throw e;
		}
	}

	/**
	 * ��ѯָ��HQL�������ؼ���
	 * 
	 * @param hql
	 *            HQL���
	 * @param values
	 *            �ɱ�Ĳ����б�
	 * @return ����
	 */
	public List<Object> find(String hql, Object... values) {
		try {
			return getHibernateTemplate().find(hql, values);
		} catch (RuntimeException e) {
			logger.error("��ѯָ��HQL�쳣��HQL��" + hql, e);
			throw e;
		}
	}

	/**
	 * ����HQL����ѯΨһ����.
	 * 
	 * @param hql
	 *            HQL���
	 * @param values
	 *            �ɱ��������
	 * @return OBJECT����
	 */
	public T findUnique(final String hql, final Object... values) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ��ѯ����Ψһ�����HQL���," + hql);
			}
			return (T) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = createQuery(s, hql, values);
					return query.uniqueResult();
				}
			});
		} catch (RuntimeException e) {
			logger.error("��ѯָ��HQL�쳣��HQL��" + hql, e);
			throw e;
		}
	}

	/**
	 * ����ָ��HQL������INT��
	 * 
	 * @param hql
	 *            HQL���
	 * @param values
	 *            �ɱ�����б�
	 * @return INT
	 */
	public int findInt(final String hql, final Object... values) {
		Object o = findUnique(hql, values);
		if (logger.isDebugEnabled()) {
			logger.debug("��ѯ�ܼ�¼ : " + o);
		}
		return o == null ? 0 : Integer.parseInt(o.toString());
	}

	/**
	 * ��ȡָ��ʵ��Classָ�������ļ�¼����
	 * 
	 * @param entityClass
	 *            ʵ��Class
	 * @param where
	 *            HQL�Ĳ�ѯ����,֧�ֲ����б�
	 * @param values
	 *            �ɱ�����б�
	 * @return ��¼����
	 */
	public int findTotalCount(final String where, final Object... values) {
		String hql = "select count(e) from " + entityClass.getName() + " as e " + where;
		return findInt(hql, values);
	}

	/**
	 * ��ȡָ��ʵ��Class�ļ�¼����
	 * 
	 * @param entityClass
	 *            ʵ��Class
	 * @return ��¼����
	 */
	public int findTotalCount() {
		return findTotalCount("");
	}

	/**
	 * ����ָ�����Ե�ʵ�弯��
	 * 
	 * @param entityClass
	 *            ʵ��
	 * @param propertyName
	 *            ������
	 * @param value
	 *            ����
	 * @return ʵ�弯��
	 */
	public List<T> findByProperty(String propertyName, Object value, String order) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ����ָ�����ԣ�" + propertyName + "Ϊ" + value + "��ʵ��"
						+ entityClass.getName());
			}
			String queryStr = "from " + entityClass.getName() + " as model where model."
					+ propertyName + "=?";
			if (null != order) {
				queryStr += " order by model." + order;
			}
			return getHibernateTemplate().find(queryStr, value);
		} catch (RuntimeException e) {
			logger.error("����ָ������ʵ�弯���쳣��������" + propertyName, e);
			throw e;
		}
	}

	/**
	 * ����ָ�����Ե�ʵ�弯��
	 * 
	 * @param entityClass
	 *            ʵ��
	 * @param propertyName
	 *            ������
	 * @param value
	 *            ����
	 * @return ʵ�弯��
	 */
	public List<T> findByProperty(String propertyName, Object value) {
		return findByProperty(propertyName, value, null);
	}

	/**
	 * ģ����ѯָ���������󼯺� <br>
	 * �÷�������ʵ����һ���յ�T������Ҫ��ѯĳ���ֶΣ���set���ֶε�����Ȼ����ñ�����<br>
	 * ȱ�㣺Ŀǰ����ò��ֻ��֧��String��ģ����ѯ����Ȼ�а취��д����û��Ҫ��������HQL<br>
	 * 
	 * @param entity
	 *            ����ʵ��
	 * @return ���
	 */
	public List<T> findByExample(T entity) {
		try {
			List<T> results = getHibernateTemplate().findByExample(entity);
			return results;
		} catch (RuntimeException re) {
			logger.error("����ָ������ʵ�弯���쳣", re);
			throw re;
		}
	}

	/**
	 * ���䷽��(δ��) ��˵��������session��״̬�־û�����
	 * 
	 * @param entity
	 *            ʵ����
	 * @return �־ú��ʵ����
	 */
	public T merge(T entity) {
		try {
			T result = (T) getHibernateTemplate().merge(entity);
			return result;
		} catch (RuntimeException re) {
			logger.error("merge�쳣", re);
			throw re;
		}
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page
	 *            ҳ�����
	 * @param hql
	 *            HQL���
	 * @param values
	 *            �ɱ�����б�
	 * @return ��ҳ����
	 */
	public Page<T> findByPage(final Page<T> page, final String hql, final Object... values) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("��ʼ����ָ��HQL��ҳ����," + hql);
			}
			return (Page<T>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = createQuery(s, hql, values);
					if (page.isFirstSetted()) {
						query.setFirstResult(page.getFirst());
					}
					if (page.isPageSizeSetted()) {
						query.setMaxResults(page.getPageSize());
					}
					page.setResult(query.list());
					if (logger.isDebugEnabled()) {
						logger.debug("����ָ��HQL��ҳ���ݳɹ�," + hql);
					}
					return page;
				}
			});
		} catch (RuntimeException e) {
			logger.error("��ҳ��ѯ�쳣��HQL��" + hql, e);
			throw e;
		}
	}

	/**
	 * ���ݲ�ѯ����������б���Query����
	 * 
	 * @param session
	 *            Hibernate�Ự
	 * @param hql
	 *            HQL���
	 * @param objects
	 *            �����б�
	 * @return Query����
	 */
	public Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query;
	}

	/**
	 * ��ȡʵ�������
	 * 
	 * @param <E>
	 * @param clazz
	 *            ʵ����
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> clazz) {
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	/**
	 * ��ȡʵ�������
	 * 
	 * @param <E>
	 * @param clazz
	 *            ʵ����
	 * @return ������
	 */
	protected static <E> String getKeyFieldName(Class<E> clazz) {
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz)
					.getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (null != method && null != method.getAnnotation(javax.persistence.Id.class)) {
					return propertydesc.getName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "id";
	}

	public void updateEntity(Object object, Serializable id) {
		if (null == object) {
			logger.warn("��ǰ���󲻴���");
			return;
		}
		boolean isUpdate = false;
		// δ�־û��Č���
		Class clazzView = object.getClass();
		// ��ȡ��ǰ����(δ�־û���)�е�ֵ
		ClassMetadata metaData = getHibernateTemplate().getSessionFactory().getClassMetadata(
				clazzView);

		// ��ȡ�ö��������ݿ��е�ֵ,���־û�
		Object objValieOfPersistence = getHibernateTemplate().get(clazzView, id);
		if (null == objValieOfPersistence) {
			logger.warn("���ݿⲻ���ڸü�¼��");
			return;
		}
		// �������ģ���е�����
		String[] properties = metaData.getPropertyNames();
		for (String property : properties) {
			// �����Եĵ�һ����ĸ��д
			String propertyFirstToUpcase = CommUtils.getString(property.substring(0, 1)
					.toUpperCase(), property.substring(1));

			String methodNameOfGet = CommUtils.getString("get", propertyFirstToUpcase);
			try {
				// δ�־û��ķ���
				Method methodOfGetView = clazzView.getMethod(methodNameOfGet, null);
				// δ�־û��Č���ֵ
				Object valueOfView = methodOfGetView.invoke(object, null);
				// �־Ìӵ�POJO
				Class clazzPersistence = objValieOfPersistence.getClass();
				// �־Ìӵķ���
				Method methodOfGetPersistence = clazzPersistence.getMethod(methodNameOfGet, null);
				// �־û��Č���ֵ
				Object valueOfPersistence = methodOfGetPersistence.invoke(objValieOfPersistence,
						null);

				Field field = clazzView.getDeclaredField(property);
				String fieldType = field.getType().getSimpleName();

				boolean canUpdate = false;
				if (null != valueOfView
						&& ((fieldType.equals("Integer") && Integer
								.parseInt(valueOfView.toString()) == Integer.MIN_VALUE)
								|| (fieldType.equals("Double") && Double.parseDouble(valueOfView
										.toString()) == Double.MIN_VALUE)
								|| (fieldType.equals("Long") && Long.parseLong(valueOfView
										.toString()) == Long.MIN_VALUE) || (fieldType
								.equals("Float") && Float.parseFloat(valueOfView.toString()) == Float.MIN_VALUE))) {
					canUpdate = true;
				}

				if (null != valueOfView
						&& (fieldType.equals("Date") && CommUtils.parseCST(valueOfView.toString())
								.compareTo(CommUtils.getDefaultDate()) == 0)) {
					canUpdate = true;
				}
				if (null != valueOfView || canUpdate) {
					if (valueOfPersistence == null && valueOfView != null) {
						isUpdate = true;
					} else if (valueOfPersistence != null) {
						// ������ֵ�����ݿ��е�ֵ��ͬ
						if (!valueOfPersistence.equals(valueOfView)) {
							isUpdate = true;
						}
					}
					if (canUpdate) {
						valueOfView = null;
					}
					if (isUpdate) {
						// ʹ��setter�����������ݸ���
						// object.getClass().getDeclaredField(s).getType()
						// ��ö������Ե����͡�����ȷ�ϴ��ݲ���������
						String methodNameOfSet = CommUtils.getString("set", propertyFirstToUpcase);
						Method methodOfSetPersistence = clazzPersistence.getMethod(methodNameOfSet,
								field.getType());
						// ִ��setter����
						methodOfSetPersistence.invoke(objValieOfPersistence, valueOfView);
					}
				}
			} catch (Exception e) {
				logger.error("parse property error.", e);
			}
		}
		if (isUpdate) {
			this.getHibernateTemplate().update(objValieOfPersistence);
		}
	}

	/**
	 * ����ʵ�� ����Hibernate��dynamicUpdate������̬����SQL
	 * 
	 * @param object
	 * @param id
	 */
	public void updateEntity_back(Object object, Serializable id) {
		boolean isUpdate = false;
		Class obj = object.getClass();
		ClassMetadata metaData = getHibernateTemplate().getSessionFactory().getClassMetadata(obj);
		// ��ȡ�ö��������ݿ��е�ֵ,���־û�
		Object objPers_ = getHibernateTemplate().get(obj, id);
		// System.out.println("_GenericDao.updateEntity->objPers_=="+objPers_);
		// �������ģ���е�����
		String[] property = metaData.getPropertyNames();
		for (String s : property) {
			if (logger.isDebugEnabled()) {
				logger.debug("property : " + s);
			}
			String propertyNameUpFirst = s.substring(0, 1).toUpperCase() + s.substring(1);
			try {
				// ����getter����
				// Expiredate����Ϊjava.util.Date���ͣ���ҳ�潫���ֶ���Ϊnullʱ����������ֵ���棬���Խ�Expiredate���⴦��
				Method methodFromView = obj.getMethod("get" + propertyNameUpFirst, null);
				if (logger.isDebugEnabled()) {
					logger.debug("object.getClass().getDeclaredField(s).getType() : "
							+ object.getClass().getDeclaredField(s).getType().getSimpleName());
					logger.debug("methodFromView.invoke(object, null)1 : "
							+ methodFromView.invoke(object, null));
				}
				if (methodFromView.invoke(object, null) != null
						|| (null != propertyNameUpFirst && "Expiredate".equals(propertyNameUpFirst))) {
					// �ж��Ƿ�Ϊint����

					if (object.getClass().getDeclaredField(s).getType() == Integer.TYPE) {
						// ��Ϊint����,���ж�ֵ�Ƿ�Ϊ-1

						if (logger.isDebugEnabled()) {
							logger.debug("methodFromView.invoke(object, null)2 : "
									+ methodFromView.invoke(object, null));
						}

						if ((Integer) methodFromView.invoke(object, null) == -1) {
							// ����-1,�˳�����ѭ����������һ����
							continue;
						}
					}
					// �ж��Ƿ�Ϊdouble����
					if (object.getClass().getDeclaredField(s).getType() == Double.TYPE) {
						// ��Ϊdouble����,���ж�ֵ�Ƿ�Ϊ-1
						if ((Double) methodFromView.invoke(object, null) == -1.0) {
							// ����-1,�˳�����ѭ����������һ����
							continue;
						}
					}
					Method methodForPers = objPers_.getClass().getMethod(
							"get" + propertyNameUpFirst, null);

					Object propetyPers = methodForPers.invoke(objPers_, null);
					Object propetyView = methodFromView.invoke(object, null);
					if (propetyPers == null && propetyView != null) {
						isUpdate = true;
					} else if (propetyPers != null) {
						// ������ֵ�����ݿ��е�ֵ��ͬ
						if (!propetyPers.equals(propetyView)) {
							isUpdate = true;
						}
					}

					if (isUpdate) {
						// ʹ��setter�����������ݸ���
						// object.getClass().getDeclaredField(s).getType()
						// ��ö������Ե����͡�����ȷ�ϴ��ݲ���������
						Method method_ = objPers_.getClass().getMethod("set" + propertyNameUpFirst,
								object.getClass().getDeclaredField(s).getType());
						// ִ��setter����
						method_.invoke(objPers_, methodFromView.invoke(object, null));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("_GenericDao.updateEntity->e=="+e);
				// TODO: handle exception
			}
		}
		if (isUpdate) {
			this.getHibernateTemplate().update(objPers_);
		}
	}

}
