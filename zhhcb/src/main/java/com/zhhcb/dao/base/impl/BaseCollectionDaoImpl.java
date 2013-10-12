package com.zhhcb.dao.base.impl;



/*
 * 封装了一些与集合相关的方法...如统计查询,分页查询
 * NOTE: 工具类,无状态
 * 
 * @author KEN
 * @since 2013.03.11
 * 版本: 1.4
 * 1: 增加指定subCriteria的查询
 */



/**
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.Assert;

import com.zhhcb.dao.base.BaseCollectionDao;






/*
 * 封装了一些与集合相关的方法...如统计查询,分页查询
 * NOTE: 工具类,无状态
 * 
 * @author KEN
 * @since 2012.03.15
 * 版本: 1.0
 */

public class BaseCollectionDaoImpl<T, ID extends Serializable> extends BaseEntityDaoImpl<T, ID> implements BaseCollectionDao<T, ID>,PagingAndSortingRepository<T, ID>
{

	/**
	 * 在Criteria执行之前给Criteria增加限制条件
	 * @param criteria
	 * @param criterions
	 */
	private void addCriterions(Criteria criteria,Criterion... criterions)
	{
		if(criterions != null)
		{
			for(Criterion criterion : criterions)    //增加查询条件
			{
				criteria.add(criterion);
			}
		}
	}
	
	
	//-------------------------------------------------------------------------
	// Convenience finder methods for HQL strings
	//-------------------------------------------------------------------------

	/**
	 * 原始的HQL查询
	 * 参考了HibernateTemplate
	 * Execute an HQL query.
	 * @param queryString a query expressed in Hibernate's query language
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	@Override
	public List<T> find(String queryString) 
	{
		return find(queryString, (Object[]) null);
	}

	/**
	 * 参考了HibernateTemplate
	 * Execute an HQL query, binding one value to a "?" parameter in the
	 * query string.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param value the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	@Override
	public List<T> find(String queryString, Object value) 
	{
		return find(queryString, new Object[] {value});
	}
	
	
	/**
	 * 参考了HibernateTemplate
	 * Execute an HQL query, binding a number of values to "?" parameters
	 * in the query string.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param values the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(final String queryString, final Object... values)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Query queryObject = session.createQuery(queryString);
		
		if (values != null) 
		{
			for (int i = 0; i < values.length; i++) 
			{
				queryObject.setParameter(i, values[i]);
			}
		}
				
		return queryObject.list();
	}
	
	
	/**
	 * 参考了HibernateTemplate
	 * Execute an HQL query, binding one value to a ":" named parameter
	 * in the query string.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	@Override
	public List<T> findByNamedParam(String queryString, String paramName, Object value)
	{

		return findByNamedParam(queryString, new String[] {paramName}, new Object[] {value});
	}

	
	
	/**
	 * 参考了HibernateTemplate
	 * Execute an HQL query, binding a number of values to ":" named
	 * parameters in the query string.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param paramNames the names of the parameters
	 * @param values the values of the parameters
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedParam(final String queryString, final String[] paramNames, final Object[] values)
	{

		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		
		Session session = sessionFactory.getCurrentSession();
	
		Query queryObject = session.createQuery(queryString);
				
		if (values != null) 
		{
			for (int i = 0; i < values.length; i++) 
			{				
				queryObject.setParameter(paramNames[i], values[i]);
			}
		}
				
		
		return queryObject.list();
		
	}
	
	
	
	
	/**
	 * 参考了HibernateTemplate
	 * Execute an HQL query, binding the properties of the given paramMap to
	 * <i>named</i> parameters in the query string.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param paramMap key:属性名,value:属性值
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Query#setProperties
	 * @see org.hibernate.Session#createQuery
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByParamMap(final String queryString, final Map<String,Object> paramMap)
	{

		Session session = sessionFactory.getCurrentSession();
		
		Query queryObject = session.createQuery(queryString);
				
		queryObject.setProperties(paramMap);
		
		return queryObject.list();
		
	}

	
	
	
	

	
	
	
	
	/**
	 * 参考了HibernateTemplate
	 * Delete all given persistent instances.
	 * <p>This can be combined with any of the find methods to delete by query
	 * in two lines of code.
	 * @param entities the persistent instances to delete
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#delete(Object)
	 */
	@Override
	public void deleteAll(final Collection<T> entities)
	{
		Session session = sessionFactory.getCurrentSession();
		
		for (Object entity : entities) 
		{
			session.delete(entity);
		}
	}
	
	


	


	
	
	/**
	 * 参考了HibernateTemplate
	 * Return all persistent instances of the given entity class.
	 * Note:简单查询所有给定类型的对象
	 * @param entityClass a persistent class
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException if there is a Hibernate error
	 * @see org.hibernate.Session#createCriteria
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		//这里是为了防止得出来的list内含有重复内容而做的操作
		Set<T> set = new HashSet<T>();
		set.addAll(criteria.list());
		List<T> list = new ArrayList<T>();
		list.addAll(set);
				
		return list;
	}
	
	/*
	 * 可使用查询缓存的getAll()
	 * since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable);
		
		
		return criteria.list();
	}


	/*
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable,Order order) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable).addOrder(order);
		
		
		return criteria.list();
	}

	
	
	
	
	
	////////===========----------------------------
	
	
	
	
	
	
	//-------------------------------------------------------------------------
	// Convenience query methods for iteration 
	//-------------------------------------------------------------------------

	@Override
	public Iterator<T> iterate(String queryString)
	{
		return iterate(queryString, (Object[]) null);
	}

	

	
	/**
	 * 参考了HibernateTemplate
	 * Execute a query for persistent instances, binding a number of
	 * values to "?" parameters in the query string.
	 * <p>Returns the results as an {@link Iterator}. Entities returned are
	 * initialized on demand. See the Hibernate API documentation for details.
	 * @param queryString a query expressed in Hibernate's query language
	 * @param values the values of the parameters
	 * @return an {@link Iterator} containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 * @see org.hibernate.Query#iterate
	 * 
	 * 按顺序向HQL设置参数后,执行得到未初始化的实体的集合,然后返回其Iterator
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterate(final String queryString, final Object... values)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Query queryObject = session.createQuery(queryString);
				
		if (values != null) 
		{
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		
		return queryObject.iterate();
	}
	
	//-------------------------------------------------------------------------
	
	
	
	
	
	
	
	

	/**
	 * 查询所有条件，可加入条件，支持排序
	 * @param <T>
	 * 
	 * @param orderPropertyName  //待排序的属性名
	 * @param isAsc
	 * @param criterions
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean isAsc,String orderPropertyName, Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		if (criterions != null)
			for (Criterion criterion : criterions)
				criteria.add(criterion);
					
		if (isAsc)
			criteria.addOrder(Order.asc(orderPropertyName));
		else 
			criteria.addOrder(Order.desc(orderPropertyName));
		
		return criteria.list();
	}
	
	
	
	//-------------------------------------------------------------------------
	// 查找指定条件的实体集合    find
	//-------------------------------------------------------------------------
	

	/**
	 * 查找指定条件的实体集合: 通过一个属性值查询实体返回一个集合
	 * @param <T>
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public List<T> findByProperty(final String propertyName,final Object value) 
	{
		return findByProperty(propertyName, value, Order.asc("id"));
	}
	
	
	@Override
	public List<T> findByProperty(String propertyName, Object value,Order... orderTypes)
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add(Restrictions.eq(propertyName, value));    //增加属性相等约束
		
		//设置排序方式
		for(Order orderType : orderTypes)
		{
			criteria.addOrder(orderType);
		}
		
		//这里是为了防止得出来的list内含有重复内容而做的操作
		Set<T> set = new HashSet<T>();
		set.addAll(criteria.list());
		List<T> list = new ArrayList<T>();
		list.addAll(set);
		
		return list;
	}

	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final String[] propertyNames,final Object[] values) 
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final Map<String,Object> propertyNameValues) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterions( Criterion... criterions) 
	{
		
		Session session=sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 并可以设置排序方式
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterions(Order orderType, Criterion... criterions) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		if(orderType != null) 
			criteria.addOrder(orderType);
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return criteria.list();
	}
	
	
	@Override
	public List<T> findByCriteriaWithSubCriterion(Criterion[] criterions, String associationPath, Criterion[] subCriterions)
	{

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(entityClass);

		addCriterions(criteria,criterions);//增加查询条件

		if (null != subCriterions)
		{
			Criteria subCriteria = criteria.createCriteria(associationPath);  //对当前类的associationPath路径的属性 增加 查询条件
			
			addCriterions(subCriteria,subCriterions);//增加sub查询条件
		}

		return criteria.list();
	}
	
	
	
	/**
	 * 参考了HibernateTemplate
	 * Execute a query based on a given Hibernate criteria object.
	 * @param criteria the detached Hibernate criteria object.
	 * <b>Note: Do not reuse criteria objects! They need to recreated per execution,
	 * due to the suboptimal design of Hibernate's criteria facility.</b>
	 * @return a {@link List} containing 0 or more persistent instances
	 
	 * @see org.hibernate.criterion.DetachedCriteria#getExecutableCriteria(org.hibernate.Session)
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria
	 */
	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) 
	{
		return findByCriteria(criteria, -1, -1);
	}

	
	/**
	 * 参考了HibernateTemplate
	 * Execute a query based on the given Hibernate criteria object.
	 * @param criteria the detached Hibernate criteria object.
	 * <b>Note: Do not reuse criteria objects! They need to recreated per execution,
	 * due to the suboptimal design of Hibernate's criteria facility.</b>
	 * @param firstResult the index of the first result object to be retrieved
	 * (numbered from 0)
	 * @param maxResults the maximum number of result objects to retrieve
	 * (or <=0 for no limit)
	 * @return a {@link List} containing 0 or more persistent instances
	
	 * @see org.hibernate.criterion.DetachedCriteria#getExecutableCriteria(org.hibernate.Session)
	 * @see org.hibernate.Criteria#setFirstResult(int)
	 * @see org.hibernate.Criteria#setMaxResults(int)
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		return executableCriteria.list();
	}

	
	

	//-------------------------------------------------------------------------
	
	
	
	//-------------------------------------------------------------------------
	// 查找指定条件的唯一实体   findUnique
	//-------------------------------------------------------------------------
	
	/**
	 * 通过条件得到唯一对象,不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param <T>
	 * 
	 * @param criterions
	 * @return
	 * 
	 * NOTE: 
	 * Throws:
	 * HibernateException - if there is more than one matching result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperty(final String propertyName,final Object value)  
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.eq(propertyName, value) );   //增加属性相等约束  
		
		return (T) criteria.uniqueResult();
	}

	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final String[] propertyNames,final Object[] values) 
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final Map<String,Object> propertyNameValues) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 通过条件得到唯一对象,不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param <T>
	 * 
	 * @param criterions
	 * @return
	 * 
	 * NOTE: 
	 * Throws:
	 * HibernateException - if there is more than one matching result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriterions(final Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for (Criterion criterion : criterions)
			criteria.add(criterion);
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriteria(DetachedCriteria criteria)
	{	
		Session session = sessionFactory.getCurrentSession();	
		
		return (T) criteria.getExecutableCriteria(session).uniqueResult();
	}

	//-------------------------------------------------------------------------
	
	
	
	
	
	
	

	//-------------------------------------------------------------------------
	// 统计查询    count
	//-------------------------------------------------------------------------
	

	
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@Override
	public Long countByProperty(final String propertyName,final Object value) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.rowCount())            //统计 实体: count(*)
				.add(Restrictions.eq(propertyName, value));
		
		
		return (Long) criteria.uniqueResult();
	}
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@Override
	public Long countByProperties(final String[] propertyNames,final Object[] values) 
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.rowCount());              //统计 实体: count(*)
				
		for(int i = 0; i< values.length; i++)
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		
		return (Long) criteria.uniqueResult();
	}
	
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@Override
	public Long countByProperties(final Map<String,Object> propertyNameValues) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.rowCount());              //统计 实体: count(*)
				
		criteria.add( Restrictions.allEq(propertyNameValues) );		
		
		return (Long) criteria.uniqueResult();
	}
	
	
	

	/**
	 * 通过条件统计实体总数
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public Long countByCriterions( Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.rowCount());              //统计 实体: count(*)
		
		for(Criterion criterion : criterions)
			criteria.add(criterion);
				
		return (Long) criteria.uniqueResult();
	}
	
	
	/**
	 * 通过条件统计实体总数
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public Long countByCriteria(final DetachedCriteria criteria)
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session)
											  .setProjection(Projections.rowCount());  //统计 实体: count(*)
		
		return (Long) executableCriteria.uniqueResult();
	}
	
	
	//-------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 知道数据库中某对象的某属性的值的情况下,判断该属性值是否唯一.
	 * 如果该属性值在数据库唯一存在,则返回true,否则返回false
	 * 如果该属性值在数据库在不存在,同样返回False
	 * 
	 * 1:统计指定属性值的数量,如果数量==1,则返回true
	 * 
	 * @param <T>
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@Override
	public boolean isPropertyUnique(final String propertyName,final Object value) 
	{
	
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.count(propertyName))            //统计 实体: count( propertyName ) 
				.add(Restrictions.eq(propertyName, value));              //增加属性条件
				
		
		
		Long cnt = (Long) criteria.uniqueResult();
		
		return cnt==1 ? true : false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//-------------------------------------------------------------------------
	// 统计查询    isEntityExist
	//-------------------------------------------------------------------------
	
	

	

	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isEntityExistByProperty(final String propertyName,final Object value) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.id())      				//只查询ID
				.add( Restrictions.eq(propertyName, value) )  		//设置条件
				.setMaxResults(1);									//因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		List<T> list = criteria.list();      

		return list.size() >= 1 ? true : false;
	}
	
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isEntityExistByProperties(final String[] propertyNames,final Object[] values) 
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.id())      				//只查询ID
				.setMaxResults(1);									//因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		for(int i=0; i<values.length; i++)                        //设置条件
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		List<T> list = criteria.list();      

		return list.size() >= 1 ? true : false;
	}
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isEntityExistByProperties(final Map<String,Object> propertyNameValues) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.id())      				//只查询ID
				.add( Restrictions.allEq(propertyNameValues) )  		//设置条件
				.setMaxResults(1);									//因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		List<T> list = criteria.list();      

		return list.size() >= 1 ? true : false;
	}
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param criteria 外部的一组对于某实体的查询条件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isEntityExistByCriteria(final DetachedCriteria criteria)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session)
											  .setProjection(Projections.id())        //只查询ID
										      .setMaxResults(1);					  //因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		List<T> list = executableCriteria.list();      

		return list.size() >= 1 ? true : false;
	}
	
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param <T>
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isEntityExistByCriterions( Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.id())        //只查询ID
								   .setMaxResults(1);					  //因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		for(Criterion criterion : criterions )   //加入限制条件
		{
			criteria.add(criterion);
		}
		
		List<T> list = criteria.list();      

		return list.size() >= 1 ? true : false;
	}
	
	
	//-------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------------------------------------------
	// 分页查询: 主要加入查询范围的设置
	//-------------------------------------------------------------------------
	
	
	/**
	 * 查询指定分页大小的实体
	 * 
	 * @param first    >=0      查询的起点
	 * @param pageSize       分页的大小
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPageSize( int first, int pageSize, Order orderType,Criterion... criterions)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setFirstResult(first)
								   .setMaxResults(pageSize);
		
		if(orderType != null) 
				criteria.addOrder(orderType);
		
		for(Criterion criterion : criterions)
		{
			criteria.add(criterion);
		}
		
		
		return criteria.list();
	}
	
	
	/**
	 * 查询指定范围的实体  [first,last)
	 * 
	 * @param first   >=0   查询的起点
	 * @param last        查询的终点
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * 
	 * @return
	 */
	@Override
	public List<T> findByRange( int first, int last, Order orderType)
	{
		if(first > last ) 
			throw new IllegalArgumentException("fisrt should smaller than last!");
		
		return findByPageSize(first, last - first, orderType);
	}
	
	
	/**
	 * 查询指定范围的实体  [first,last)
	 * 
	 * @param first   >=0   查询的起点
	 * @param last        查询的终点
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * 
	 * @return
	 */
	@Override
	public List<T> findByRange( int first, int last, Order orderType,Criterion... criterions)
	{
		if(first > last ) 
			throw new IllegalArgumentException("fisrt should smaller than last!");
		
		return findByPageSize(first, last - first, orderType,criterions);
	}
	
	
	/**
	 * 查询指定页面索引的所有实体
	 * 
	 * 
	 * @param pageIndex >=1   页面索引(第几页)
	 * @param pageSize      分页大小 
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 */
	@Override
	public List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType)
	{
		return findByPageSize((pageIndex-1)*pageSize, pageSize, orderType);
	}
	
	
	/**
	 * 通过criteria进行分页查询
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		if(orderType != null) 
			criteria.addOrder(orderType);
		
		return executableCriteria.list();
	}
		

	//-------------------------------------------------------------------------
	
	
	
	//-------------------------------------------------------------------------
	// 查找特定条件的对象的属性    findProperty
	//-------------------------------------------------------------------------
	

	/**
	 * 查找一类对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findProperty(String propertyName) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName));  
		
		
		return criteria.list();
	}
	
	
	/**
	 * 查找一类对象的多个属性的集合
	 * 
	 * @param propertyNames 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findProperties(String... propertyNames) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList);
		
	
		return criteria.list();
	}
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性(的集合)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName))
								   .add(Restrictions.eq(knownProperty, knownPropertyValue));  
		
		
		return criteria.list();
	}
	
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性(的集合)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName,Order order) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName))
								   .add(Restrictions.eq(knownProperty, knownPropertyValue));
		
		criteria.addOrder(order);
		
		return criteria.list();
	}
	
	
	/**
	 * 已知对象的多个属性,查找其互不相同的属性的集合

	 * 
	
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findDistinctPropertyByProperties(String[] knownProperties,Object[] knownPropertyValues,String propertyName,Order order) 
	{
		Assert.isTrue(knownProperties.length == knownPropertyValues.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(
										   Projections.distinct(Projections.property(propertyName)) );
		
		for(int i=0; i<knownProperties.length; i++)
			criteria.add(Restrictions.eq(knownProperties[i], knownPropertyValues[i]));
		
		criteria.addOrder(order);
		
		return criteria.list();
	}
	
	
	/**
	 * 根据一个已知属性,查找一类对象的多个属性
	 * 
	 * @param propertyNames
	 *  * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值 
	 * 
	 * @return 结果可能有多行,表示有多个对象符合情况
	 */
	@Override
	public List findPropertiesByProperty(String knownProperty,Object knownPropertyValue,String[] propertyNames)
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList)
										.add(Restrictions.eq(knownProperty, knownPropertyValue));
		
	
		return criteria.list();
	}
	
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@Override
	public List findPropertyByCriterions( String propertyName,Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName));  
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return criteria.list();
	}
	
	
	
	/**
	 * 查找指定条件的对象的多个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@Override
	public List findPropertiesByCriterions( String[] propertyNames,Criterion... criterions) 
	{
	
		Session session = sessionFactory.getCurrentSession();	

		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList);
		
		
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return criteria.list();
	}
	
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	@Override
	public List findPropertyByCriteria(DetachedCriteria criteria,String propertyName) 
	{
		return findPropertyByCriteria(criteria, -1, -1,propertyName);
	}

	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findPropertyByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String propertyName)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		
		//设置待查询的属性
		executableCriteria.setProjection(Projections.property(propertyName));
		
		
		return executableCriteria.list();
	}
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	@Override
	public List findPropertiesByCriteria(DetachedCriteria criteria,String... propertyNames) 
	{
		return findPropertiesByCriteria(criteria, -1, -1,propertyNames);
	}

	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findPropertiesByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String... propertyNames)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		
		//设置待查询的属性
	
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		executableCriteria.setProjection(projectionList);
		
		
		return executableCriteria.list();
	}
	


	//-------------------------------------------------------------------------

	
	

	//-------------------------------------------------------------------------
	// 查找特定条件的对象的属性  且结果只有一行  findUniqueProperty
	//-------------------------------------------------------------------------
	

	/**
	 * 查找一类对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@Override
	public Object findUniqueProperty(String propertyName) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName));  
		
		
		return criteria.uniqueResult();
	}
	
	
	/**
	 * 查找一类对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * 
	 * @param propertyNames 
	 * @return
	 */
	@Override
	public Object[] findUniqueProperties(String... propertyNames) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList);
		
	
		return (Object[]) criteria.uniqueResult();
	}
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	@Override
	public Object findUniquePropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName))
								   .add(Restrictions.eq(knownProperty, knownPropertyValue));  
		
		
		return criteria.uniqueResult();
	}
	
	/**
	 * 根据一个已知ID,查找一类对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param id 已知的ID
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	@Override
	public Object findUniquePropertyById(Object id,String propertyName) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName))
								   .add(Restrictions.idEq(id));  
		
		
		return criteria.uniqueResult();
	}
	
	
	/**
	 * 
	 * 假设符合给出的条件的对象只有一个,则
	 * 根据一个已知属性,查找这个对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * 
	 * 
	 * @param propertyNames
	 *  * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值 
	 * @return
	 */
	@Override
	public Object[] findUniquePropertiesByProperty(String knownProperty,Object knownPropertyValue,String[] propertyNames)
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList)
										.add(Restrictions.eq(knownProperty, knownPropertyValue));
		
	
		return (Object[]) criteria.uniqueResult();
	}
	
	
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@Override
	public Object findUniquePropertyByCriterions( String propertyName,Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.property(propertyName));  
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return criteria.uniqueResult();
	}
	
	
	
	/**
	 * 查找指定条件的对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	@Override
	public Object[] findUniquePropertiesByCriterions( String[] propertyNames,Criterion... criterions) 
	{
	
		Session session = sessionFactory.getCurrentSession();	

		
		//增加要查询的属性
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		Criteria criteria = session.createCriteria(entityClass).setProjection(projectionList);
		
		
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return (Object[]) criteria.uniqueResult();
	}
	
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	@Override
	public Object findUniquePropertyByCriteria(DetachedCriteria criteria,String propertyName) 
	{
		return findUniquePropertyByCriteria(criteria, -1, -1,propertyName);
	}

	
	
	@Override
	public Object findUniquePropertyByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String propertyName)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		
		//设置待查询的属性
		executableCriteria.setProjection(Projections.property(propertyName));
		
		
		return executableCriteria.uniqueResult();
	}
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	@Override
	public Object[] findUniquePropertiesByCriteria(DetachedCriteria criteria,String... propertyNames) 
	{
		return findUniquePropertiesByCriteria(criteria, -1, -1,propertyNames);
	}

	
	
	@Override
	public Object[] findUniquePropertiesByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String... propertyNames)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		
		//设置待查询的属性
	
		ProjectionList projectionList = Projections.projectionList();
		
		for(String propertyName : propertyNames)                           
		{
			projectionList.add(Projections.property(propertyName));
		}
		
		executableCriteria.setProjection(projectionList);
		
		
		return (Object[]) executableCriteria.uniqueResult();
	}
	


	//-------------------------------------------------------------------------
	
	
	
	
	
	//id
	
	/**
	 * 根据一个已知属性,查找一类对象的ID(不保证唯一性,若查找到的结果大于一行,则抛出异常)
	 * @param <T>
	 * 
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ID findIdByProperty(String knownProperty,Object knownPropertyValue) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.id())
								   .add(Restrictions.eq(knownProperty, knownPropertyValue));  
		
		
		return (ID) criteria.uniqueResult();
	}
	
	/**
	 * 查找指定条件的对象的id(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ID findIdByCriterions(Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setProjection(Projections.id());  
		
		
		addCriterions(criteria,criterions);//增加查询条件
								      
		
		return (ID) criteria.uniqueResult();
	}
	
	

	
	@SuppressWarnings("unchecked")
	@Override
	public ID findIdByCriteria(DetachedCriteria criteria)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		
		//设置待查询的属性
		executableCriteria.setProjection(Projections.id());
		
		
		return (ID) executableCriteria.uniqueResult();
	}

	
	
	@Override
	public Iterable<T> findAll(Sort sort)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(org.springframework.data.domain.Sort.Order order : sort)
		{
			criteria.addOrder( order.isAscending() ?  Order.asc(order.getProperty()) : Order.desc(order.getProperty()) );	
		}
		
		
		return criteria.list();

	}

	@Override
	public Page<T> findAll(Pageable pageable)
	{
		return findAll(pageable,(Criterion[])null);
	}
	
	
	@Override
	public Page<T> findAll(Pageable pageable,Criterion... criterions)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setFirstResult(pageable.getOffset())
								   .setMaxResults(pageable.getPageSize());
		
		
		for(org.springframework.data.domain.Sort.Order order : pageable.getSort())
		{
			criteria.addOrder( order.isAscending() ?  Order.asc(order.getProperty()) : Order.desc(order.getProperty()) );	
		}
	
		
		addCriterions(criteria,criterions);//增加查询条件
		
		
		List list =  criteria.list();
		
		return new PageImpl<T>(list,pageable,this.count());
		
	}
	
	
	@Override
	public Page<T> findAll(Pageable pageable,DetachedCriteria detachedCriteria)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(session)
									.setFirstResult(pageable.getOffset())
								    .setMaxResults(pageable.getPageSize());
		
		
		for(org.springframework.data.domain.Sort.Order order : pageable.getSort())
		{
			criteria.addOrder( order.isAscending() ?  Order.asc(order.getProperty()) : Order.desc(order.getProperty()) );	
		}
	
		
		List list =  criteria.list();
		
		return new PageImpl<T>(list,pageable,this.count());
		
	}




}
