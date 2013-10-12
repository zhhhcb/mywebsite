package com.zhhcb.dao.base.impl;




/*
 * 封装了面向集合的基本操作,如果 集合排序,集合指定区间查询,分页查询等
 * @author Ken
 */


/**
 * 
 */


import java.io.Serializable;
import java.util.List;
import java.util.Map;



import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.zhhcb.dao.base.BaseTemplateDao;






/*
 	对BaseCollectionDaoImpl部分方法加入FetchMode的选项,实现了Dynamic association fetching 动态关联抓取
 * 
 * @author KEN
 * @since 2012.07.31
 */

public class BaseTemplateDaoImpl<T, ID extends Serializable> extends BaseCollectionDaoImpl<T, ID> implements BaseTemplateDao<T, ID>
{

	/**
	 * 参考了HibernateTemplate
	 * Return all persistent instances of the given entity class.
	 * Note:简单查询所有给定类型的对象
	 * @param entityClass a persistent class
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException if there is a Hibernate error
	 * @see org.hibernate.Session#createCriteria
	 * 
	 *
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String associationPath,FetchMode mode) 
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}
	
	/*
	 * 可使用查询缓存的getAll()
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable,String associationPath,FetchMode mode)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable);
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}


	/*
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable,Order order,String associationPath,FetchMode mode)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable).addOrder(order);
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}

	
	
	
	
	
	////////===========----------------------------
	
	
	
	

	
	//-------------------------------------------------------------------------
	
	
	
	
	
	
	
	

	/**
	 * 查询所有条件，可加入条件，支持排序
	 * @param <T>
	 * 
	 * @param orderPropertyName  //待排序的属性名
	 * @param isAsc
	 * @param criterions
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean isAsc,String orderPropertyName,String associationPath,FetchMode mode, Criterion... criterions) 
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
		
		criteria.setFetchMode(associationPath, mode);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(final String propertyName,final Object value,String associationPath,FetchMode mode) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add(Restrictions.eq(propertyName, value));    //增加属性相等约束
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final String[] propertyNames,final Object[] values,String associationPath,FetchMode mode) 
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final Map<String,Object> propertyNameValues,String associationPath,FetchMode mode) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterions(String associationPath,FetchMode mode, Criterion... criterions) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(Criterion criterion : criterions)    //增加查询条件
		{
			criteria.add(criterion);
		}
								
		
		criteria.setFetchMode(associationPath, mode);
		
		return criteria.list();
	}
	
	

	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 并可以设置排序方式
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterions(Order orderType, String associationPath,FetchMode mode, Criterion... criterions) 
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		if(orderType != null) 
			criteria.addOrder(orderType);
		
		for(Criterion criterion : criterions)    //增加查询条件
		{
			criteria.add(criterion);
		}

		criteria.setFetchMode(associationPath, mode);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria,String associationPath,FetchMode mode)
	{
		return findByCriteria(criteria, -1, -1,associationPath,mode);
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults,String associationPath,FetchMode mode)
	{
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		executableCriteria.setFetchMode(associationPath, mode);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperty(final String propertyName,final Object value,String associationPath,FetchMode mode)  
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.eq(propertyName, value) );   //增加属性相等约束  
		
		
		criteria.setFetchMode(associationPath, mode);
		
		return (T) criteria.uniqueResult();
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final String[] propertyNames,final Object[] values,String associationPath,FetchMode mode)  
	{
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		criteria.setFetchMode(associationPath, mode);
		
		return (T) criteria.uniqueResult();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final Map<String,Object> propertyNameValues,String associationPath,FetchMode mode)  
	{
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		criteria.setFetchMode(associationPath, mode);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriterions(String associationPath,FetchMode mode,final Criterion... criterions) 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for (Criterion criterion : criterions)
			criteria.add(criterion);
		
		criteria.setFetchMode(associationPath, mode);
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriteria(DetachedCriteria criteria,String associationPath,FetchMode mode)
	{	
		Session session = sessionFactory.getCurrentSession();	
		
		criteria.setFetchMode(associationPath, mode);
		
		return (T) criteria.getExecutableCriteria(session).uniqueResult();
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPageSize( int first, int pageSize, Order orderType,String associationPath,FetchMode mode)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setFirstResult(first)
								   .setMaxResults(pageSize);
		
		if(orderType != null) 
				criteria.addOrder(orderType);
		
		criteria.setFetchMode(associationPath, mode);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	public List<T> findByRange( int first, int last, Order orderType,String associationPath,FetchMode mode)
	{
		if(first > last ) 
			throw new IllegalArgumentException("fisrt should smaller than last!");
		
		return findByPageSize(first, last - first, orderType,associationPath, mode);
	}
	
	
	/**
	 * 查询指定页面索引的所有实体
	 * 
	 * 
	 * @param pageIndex >=1   页面索引(第几页)
	 * @param pageSize      分页大小 
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	public List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType,String associationPath,FetchMode mode)
	{
		return findByPageSize((pageIndex-1)*pageSize, pageSize, orderType,associationPath, mode);
	}
	
	
	/**
	 * 通过criteria进行分页查询
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType,String associationPath,FetchMode mode)
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
			executableCriteria.addOrder(orderType);
		
		executableCriteria.setFetchMode(associationPath, mode);
		
		return executableCriteria.list();
	}
		

	//-------------------------------------------------------------------------
	


	
	/**
	 * 参考了HibernateTemplate
	 * Return all persistent instances of the given entity class.
	 * Note:简单查询所有给定类型的对象
	 * @param entityClass a persistent class
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException if there is a Hibernate error
	 * @see org.hibernate.Session#createCriteria
	 * 
	 * 加入了
	 * HibernateTemplate
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String[] associationPaths,FetchMode[] modes) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}
	
	/*
	 * 可使用查询缓存的getAll()
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable,String[] associationPaths,FetchMode[] modes)
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable);
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}


	/*
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean cacheable,Order order,String[] associationPaths,FetchMode[] modes)
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass).setCacheable(cacheable).addOrder(order);
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}

	
	
	
	
	
	////////===========----------------------------
	
	
	
	

	
	//-------------------------------------------------------------------------
	
	
	
	
	
	
	
	

	/**
	 * 查询所有条件，可加入条件，支持排序
	 * @param <T>
	 * 
	 * @param orderPropertyName  //待排序的属性名
	 * @param isAsc
	 * @param criterions
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(boolean isAsc,String orderPropertyName,String[] associationPaths,FetchMode[] modes, Criterion... criterions) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		if (criterions != null)
			for (Criterion criterion : criterions)
				criteria.add(criterion);
					
		if (isAsc)
			criteria.addOrder(Order.asc(orderPropertyName));
		else 
			criteria.addOrder(Order.desc(orderPropertyName));
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(final String propertyName,final Object value,String[] associationPaths,FetchMode[] modes) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add(Restrictions.eq(propertyName, value));    //增加属性相等约束
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final String[] propertyNames,final Object[] values,String[] associationPaths,FetchMode[] modes) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByProperties(final Map<String,Object> propertyNameValues,String[] associationPaths,FetchMode[] modes) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return criteria.list();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterions(String[] associationPaths,FetchMode[] modes, Criterion... criterions) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(Criterion criterion : criterions)    //增加查询条件
		{
			criteria.add(criterion);
		}
								
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria,String[] associationPaths,FetchMode[] modes)
	{
		return findByCriteria(criteria, -1, -1,associationPaths,modes);
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults,String[] associationPaths,FetchMode[] modes)
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		for(int i=0; i<associationPaths.length; i++)
			executableCriteria.setFetchMode(associationPaths[i], modes[i]);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperty(final String propertyName,final Object value,String[] associationPaths,FetchMode[] modes)  
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.eq(propertyName, value) );   //增加属性相等约束  
		
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return (T) criteria.uniqueResult();
	}

	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final String[] propertyNames,final Object[] values,String[] associationPaths,FetchMode[] modes)  
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		if(propertyNames.length != values.length) 
			throw new IllegalArgumentException("the length of propertyNames and the length of values must be same!");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(int i=0; i<values.length; i++)                           //增加属性相等约束
		{
			criteria.add( Restrictions.eq(propertyNames[i], values[i]) );
		}
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperties(final Map<String,Object> propertyNameValues,String[] associationPaths,FetchMode[] modes)  
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
								   .add( Restrictions.allEq(propertyNameValues) );      //增加属性相等约束
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriterions(String[] associationPaths,FetchMode[] modes,final Criterion... criterions) 
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for (Criterion criterion : criterions)
			criteria.add(criterion);
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return (T) criteria.uniqueResult();
	}
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCriteria(DetachedCriteria criteria,String[] associationPaths,FetchMode[] modes)
	{	
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();	
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return (T) criteria.getExecutableCriteria(session).uniqueResult();
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPageSize( int first, int pageSize, Order orderType,String[] associationPaths,FetchMode[] modes)
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
								   .setFirstResult(first)
								   .setMaxResults(pageSize);
		
		if(orderType != null) 
				criteria.addOrder(orderType);
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
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
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	public List<T> findByRange( int first, int last, Order orderType,String[] associationPaths,FetchMode[] modes)
	{
		if(first > last ) 
			throw new IllegalArgumentException("fisrt should smaller than last!");
		
		return findByPageSize(first, last - first, orderType,associationPaths, modes);
	}
	
	
	/**
	 * 查询指定页面索引的所有实体
	 * 
	 * 
	 * @param pageIndex >=1   页面索引(第几页)
	 * @param pageSize      分页大小 
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@Override
	public List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType,String[] associationPaths,FetchMode[] modes)
	{
		return findByPageSize((pageIndex-1)*pageSize, pageSize, orderType,associationPaths, modes);
	}
	
	
	/**
	 * 通过criteria进行分页查询
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType,String[] associationPaths,FetchMode[] modes)
	{
		Assert.isTrue(associationPaths.length == modes.length);
		
		Session session = sessionFactory.getCurrentSession();		
		
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		
		if(orderType != null) 
			executableCriteria.addOrder(orderType);
		
		for(int i=0; i<associationPaths.length; i++)
			executableCriteria.setFetchMode(associationPaths[i], modes[i]);
		
		return executableCriteria.list();
	}


	//-------------------------------------------------------------------------
	
}
