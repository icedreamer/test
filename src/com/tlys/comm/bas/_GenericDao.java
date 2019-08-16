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
 * DAO层封装接口实现类，包含常用的CURD和分页操作，使用泛型
 */
public class _GenericDao<T> extends HibernateDaoSupport {
	protected final Log log = LogFactory.getLog(this.getClass());

	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected String entityClassName = getEntityName(this.entityClass);
	protected String keyFieldName = getKeyFieldName(this.entityClass);

	/**
	 * 保存指定实体类
	 * 
	 * @param entityobj
	 *            实体类
	 */
	public void save(T entity) {
		try {
			getHibernateTemplate().save(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体类成功," + entityClassName);
			}
		} catch (RuntimeException e) {
			logger.error("保存实体异常," + entityClassName, e);
			throw e;
		}
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("保存实体类成功," + entityClassName);
		}
	}

	/**
	 * 删除指定实体
	 * 
	 * @param entityobj
	 *            实体类
	 */
	public void delete(T entity) {
		try {
			getHibernateTemplate().delete(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("删除实体类成功," + entityClassName);
			}
		} catch (RuntimeException e) {
			logger.error("删除实体异常", e);
			throw e;
		}
	}

	/**
	 * 根据ID列表删除实体
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
	 * 获取所有实体集合
	 * 
	 * @param entityClass
	 *            实体
	 * @return 集合
	 */
	public List<T> findAll() {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("查找所有实体集合：" + entityClassName);
			}
			return getHibernateTemplate().find("from " + entityClassName);
		} catch (RuntimeException e) {
			logger.error("查找指定实体集合异常，实体：" + entityClassName, e);
			throw e;
		}
	}

	/**
	 * 获取所有实体集合,同时附加排序等条件
	 * 
	 * @param entityClass
	 *            实体
	 * @return 集合
	 */
	public List<T> findAll(String extHql) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("查找指定实体集合：" + entityClassName);
			}
			return getHibernateTemplate().find("from " + entityClassName + extHql);
		} catch (RuntimeException e) {
			logger.error("查找指定实体集合异常，实体：" + entityClassName, e);
			// e.printStackTrace();
			throw e;
		}
	}

	public List<T> findAlls(String order) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("查找指定实体集合：" + entityClassName);
			}
			return getHibernateTemplate().find(
					"from " + entityClassName + " as model order by model." + order);
		} catch (RuntimeException e) {
			logger.error("查找指定实体集合异常，实体：" + entityClassName, e);
			// e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 更新或保存指定实体
	 * 
	 * @param entity
	 *            实体类
	 */
	public void saveOrUpdate(T entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("更新或者保存实体成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("更新或保存实体异常", e);
			throw e;
		}
	}

	/**
	 * 查找指定ID实体类对象
	 * 
	 * @param entityClass
	 *            实体Class
	 * @param id
	 *            实体ID
	 * @return 实体对象
	 */
	public T load(Serializable id) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("开始查找ID为" + id + "的实体：" + entityClass.getName());
			}
			return (T) getHibernateTemplate().get(entityClass, id);
		} catch (RuntimeException e) {
			logger.error("查找指定ID实体异常，ID：" + id, e);
			throw e;
		}
	}

	/**
	 * 查询指定HQL，并返回集合
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变的参数列表
	 * @return 集合
	 */
	public List<Object> find(String hql, Object... values) {
		try {
			return getHibernateTemplate().find(hql, values);
		} catch (RuntimeException e) {
			logger.error("查询指定HQL异常，HQL：" + hql, e);
			throw e;
		}
	}

	/**
	 * 按照HQL语句查询唯一对象.
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数集合
	 * @return OBJECT对象
	 */
	public T findUnique(final String hql, final Object... values) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("开始查询返回唯一结果的HQL语句," + hql);
			}
			return (T) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = createQuery(s, hql, values);
					return query.uniqueResult();
				}
			});
		} catch (RuntimeException e) {
			logger.error("查询指定HQL异常，HQL：" + hql, e);
			throw e;
		}
	}

	/**
	 * 查找指定HQL并返回INT型
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数列表
	 * @return INT
	 */
	public int findInt(final String hql, final Object... values) {
		Object o = findUnique(hql, values);
		if (logger.isDebugEnabled()) {
			logger.debug("查询总记录 : " + o);
		}
		return o == null ? 0 : Integer.parseInt(o.toString());
	}

	/**
	 * 获取指定实体Class指定条件的记录总数
	 * 
	 * @param entityClass
	 *            实体Class
	 * @param where
	 *            HQL的查询条件,支持参数列表
	 * @param values
	 *            可变参数列表
	 * @return 记录总数
	 */
	public int findTotalCount(final String where, final Object... values) {
		String hql = "select count(e) from " + entityClass.getName() + " as e " + where;
		return findInt(hql, values);
	}

	/**
	 * 获取指定实体Class的记录总数
	 * 
	 * @param entityClass
	 *            实体Class
	 * @return 记录总数
	 */
	public int findTotalCount() {
		return findTotalCount("");
	}

	/**
	 * 查找指定属性的实体集合
	 * 
	 * @param entityClass
	 *            实体
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            条件
	 * @return 实体集合
	 */
	public List<T> findByProperty(String propertyName, Object value, String order) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("开始查找指定属性：" + propertyName + "为" + value + "的实体"
						+ entityClass.getName());
			}
			String queryStr = "from " + entityClass.getName() + " as model where model."
					+ propertyName + "=?";
			if (null != order) {
				queryStr += " order by model." + order;
			}
			return getHibernateTemplate().find(queryStr, value);
		} catch (RuntimeException e) {
			logger.error("查找指定条件实体集合异常，条件：" + propertyName, e);
			throw e;
		}
	}

	/**
	 * 查找指定属性的实体集合
	 * 
	 * @param entityClass
	 *            实体
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            条件
	 * @return 实体集合
	 */
	public List<T> findByProperty(String propertyName, Object value) {
		return findByProperty(propertyName, value, null);
	}

	/**
	 * 模糊查询指定条件对象集合 <br>
	 * 用法：可以实例化一个空的T对象，需要查询某个字段，就set该字段的条件然后调用本方法<br>
	 * 缺点：目前测试貌似只能支持String的模糊查询，虽然有办法重写，但没必要，其他用HQL<br>
	 * 
	 * @param entity
	 *            条件实体
	 * @return 结合
	 */
	public List<T> findByExample(T entity) {
		try {
			List<T> results = getHibernateTemplate().findByExample(entity);
			return results;
		} catch (RuntimeException re) {
			logger.error("查找指定条件实体集合异常", re);
			throw re;
		}
	}

	/**
	 * 补充方法(未测) 据说可以无视session的状态持久化对象
	 * 
	 * @param entity
	 *            实体类
	 * @return 持久后的实体类
	 */
	public T merge(T entity) {
		try {
			T result = (T) getHibernateTemplate().merge(entity);
			return result;
		} catch (RuntimeException re) {
			logger.error("merge异常", re);
			throw re;
		}
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            页面对象
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数列表
	 * @return 分页数据
	 */
	public Page<T> findByPage(final Page<T> page, final String hql, final Object... values) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("开始查找指定HQL分页数据," + hql);
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
						logger.debug("查找指定HQL分页数据成功," + hql);
					}
					return page;
				}
			});
		} catch (RuntimeException e) {
			logger.error("分页查询异常，HQL：" + hql, e);
			throw e;
		}
	}

	/**
	 * 根据查询条件与参数列表创建Query对象
	 * 
	 * @param session
	 *            Hibernate会话
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            参数列表
	 * @return Query对象
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
	 * 获取实体的名称
	 * 
	 * @param <E>
	 * @param clazz
	 *            实体类
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
	 * 获取实体的主键
	 * 
	 * @param <E>
	 * @param clazz
	 *            实体类
	 * @return 主键名
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
			logger.warn("当前对象不存在");
			return;
		}
		boolean isUpdate = false;
		// 未持久化的對象
		Class clazzView = object.getClass();
		// 获取当前对象(未持久化的)中的值
		ClassMetadata metaData = getHibernateTemplate().getSessionFactory().getClassMetadata(
				clazzView);

		// 获取该对象在数据库中的值,并持久化
		Object objValieOfPersistence = getHibernateTemplate().get(clazzView, id);
		if (null == objValieOfPersistence) {
			logger.warn("数据库不存在该记录！");
			return;
		}
		// 获得数据模型中的属性
		String[] properties = metaData.getPropertyNames();
		for (String property : properties) {
			// 将属性的第一个字母大写
			String propertyFirstToUpcase = CommUtils.getString(property.substring(0, 1)
					.toUpperCase(), property.substring(1));

			String methodNameOfGet = CommUtils.getString("get", propertyFirstToUpcase);
			try {
				// 未持久化的方法
				Method methodOfGetView = clazzView.getMethod(methodNameOfGet, null);
				// 未持久化的屬性值
				Object valueOfView = methodOfGetView.invoke(object, null);
				// 持久層的POJO
				Class clazzPersistence = objValieOfPersistence.getClass();
				// 持久層的方法
				Method methodOfGetPersistence = clazzPersistence.getMethod(methodNameOfGet, null);
				// 持久化的屬性值
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
						// 当参数值与数据库中的值不同
						if (!valueOfPersistence.equals(valueOfView)) {
							isUpdate = true;
						}
					}
					if (canUpdate) {
						valueOfView = null;
					}
					if (isUpdate) {
						// 使用setter方法进行数据更新
						// object.getClass().getDeclaredField(s).getType()
						// 获得对象属性的类型。用以确认传递参数的类型
						String methodNameOfSet = CommUtils.getString("set", propertyFirstToUpcase);
						Method methodOfSetPersistence = clazzPersistence.getMethod(methodNameOfSet,
								field.getType());
						// 执行setter方法
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
	 * 更新实体 根据Hibernate的dynamicUpdate参数动态生成SQL
	 * 
	 * @param object
	 * @param id
	 */
	public void updateEntity_back(Object object, Serializable id) {
		boolean isUpdate = false;
		Class obj = object.getClass();
		ClassMetadata metaData = getHibernateTemplate().getSessionFactory().getClassMetadata(obj);
		// 获取该对象在数据库中的值,并持久化
		Object objPers_ = getHibernateTemplate().get(obj, id);
		// System.out.println("_GenericDao.updateEntity->objPers_=="+objPers_);
		// 获得数据模型中的属性
		String[] property = metaData.getPropertyNames();
		for (String s : property) {
			if (logger.isDebugEnabled()) {
				logger.debug("property : " + s);
			}
			String propertyNameUpFirst = s.substring(0, 1).toUpperCase() + s.substring(1);
			try {
				// 构造getter方法
				// Expiredate参数为java.util.Date类型，在页面将该字段置为null时，会跳过该值保存，所以将Expiredate特殊处理
				Method methodFromView = obj.getMethod("get" + propertyNameUpFirst, null);
				if (logger.isDebugEnabled()) {
					logger.debug("object.getClass().getDeclaredField(s).getType() : "
							+ object.getClass().getDeclaredField(s).getType().getSimpleName());
					logger.debug("methodFromView.invoke(object, null)1 : "
							+ methodFromView.invoke(object, null));
				}
				if (methodFromView.invoke(object, null) != null
						|| (null != propertyNameUpFirst && "Expiredate".equals(propertyNameUpFirst))) {
					// 判断是否为int类型

					if (object.getClass().getDeclaredField(s).getType() == Integer.TYPE) {
						// 若为int类型,则判断值是否为-1

						if (logger.isDebugEnabled()) {
							logger.debug("methodFromView.invoke(object, null)2 : "
									+ methodFromView.invoke(object, null));
						}

						if ((Integer) methodFromView.invoke(object, null) == -1) {
							// 等于-1,退出本次循环。进入下一次中
							continue;
						}
					}
					// 判断是否为double类型
					if (object.getClass().getDeclaredField(s).getType() == Double.TYPE) {
						// 若为double类型,则判断值是否为-1
						if ((Double) methodFromView.invoke(object, null) == -1.0) {
							// 等于-1,退出本次循环。进入下一次中
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
						// 当参数值与数据库中的值不同
						if (!propetyPers.equals(propetyView)) {
							isUpdate = true;
						}
					}

					if (isUpdate) {
						// 使用setter方法进行数据更新
						// object.getClass().getDeclaredField(s).getType()
						// 获得对象属性的类型。用以确认传递参数的类型
						Method method_ = objPers_.getClass().getMethod("set" + propertyNameUpFirst,
								object.getClass().getDeclaredField(s).getType());
						// 执行setter方法
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
