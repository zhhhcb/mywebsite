package com.zhhcb.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;


/**
 *
 * 相对于BaseCollectionDao和BaseEntityDao,增加了运行时捉取模式的动态控制!
 * 
 * @author Spring
 *
 * @param <T>
 * @param <ID>
 * 
 * 
 @author KEN
 * @since 2012.07.31
 */



public interface BaseTemplateDao<T, ID extends Serializable> extends BaseCollectionDao<T, ID>
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
	 * 加入了
	 * HibernateTemplate
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	List<T> getAll(String associationPath,FetchMode mode);
	
	/*
	 * 可使用查询缓存的getAll()
	 * since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	 
	List<T> getAll(boolean cacheable,String associationPath,FetchMode mode);


	/*
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	
	 
	List<T> getAll(boolean cacheable,Order order,String associationPath,FetchMode mode);
	
	
	
	
	
	////////===========----------------------------
	
	
	
	

	
	
	
	
	
	
	
	
	

	/**
	 * 查询所有条件，可加入条件，支持排序
	 * @param <T>
	 * 
	 * @param orderPropertyName  //待排序的属性名
	 * @param isAsc
	 * @param criterions
	 * @return
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	
	List<T> getAll(boolean isAsc,String orderPropertyName,String associationPath,FetchMode mode, Criterion... criterions);
	
	
	
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
	 
	
	List<T> findByProperty(final String propertyName,final Object value,String associationPath,FetchMode mode);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	List<T> findByProperties(final String[] propertyNames,final Object[] values,String associationPath,FetchMode mode);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	List<T> findByProperties(final Map<String,Object> propertyNameValues,String associationPath,FetchMode mode);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	List<T> findByCriterions(String associationPath,FetchMode mode, Criterion... criterions);
	
	
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
	
	
	List<T> findByCriterions(Order orderType, String associationPath,FetchMode mode, Criterion... criterions); 
	
	
	
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
	
	 
	List<T> findByCriteria(DetachedCriteria criteria,String associationPath,FetchMode mode);

	
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
	 
	List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults,String associationPath,FetchMode mode);


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
	 
	T findOneByProperty(final String propertyName,final Object value,String associationPath,FetchMode mode);

	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	T findOneByProperties(final String[] propertyNames,final Object[] values,String associationPath,FetchMode mode);
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	T findOneByProperties(final Map<String,Object> propertyNameValues,String associationPath,FetchMode mode);
	
	
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
	 
	T findOneByCriterions(String associationPath,FetchMode mode,final Criterion... criterions);
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	 
	T findOneByCriteria(DetachedCriteria criteria,String associationPath,FetchMode mode);

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
	List<T> findByPageSize( int first, int pageSize, Order orderType,String associationPath,FetchMode mode);
	
	
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
	 
	List<T> findByRange( int first, int last, Order orderType,String associationPath,FetchMode mode);
	
	
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
	 
	List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType,String associationPath,FetchMode mode);
	
	
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
	 
	List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType,String associationPath,FetchMode mode);
		

	//-------------------------------------------------------------------------
	
	
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

	List<T> getAll(String[] associationPaths,FetchMode[] modes) ;
	
	/*
	 * 可使用查询缓存的getAll()
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 * 
	 */
	
	List<T> getAll(boolean cacheable,String[] associationPaths,FetchMode[] modes);


	/*
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.BaseCollectionDao#getAll(java.lang.Class)
	 * 实现了Dynamic association fetching 动态关联抓取
	 */
	
	List<T> getAll(boolean cacheable,Order order,String[] associationPaths,FetchMode[] modes);

	
	
	
	
	
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
	
	List<T> getAll(boolean isAsc,String orderPropertyName,String[] associationPaths,FetchMode[] modes, Criterion... criterions);
	
	
	
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
	List<T> findByProperty(final String propertyName,final Object value,String[] associationPaths,FetchMode[] modes);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	List<T> findByProperties(final String[] propertyNames,final Object[] values,String[] associationPaths,FetchMode[] modes);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	List<T> findByProperties(final Map<String,Object> propertyNameValues,String[] associationPaths,FetchMode[] modes);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */

	List<T> findByCriterions(String[] associationPaths,FetchMode[] modes, Criterion... criterions);
	
	
	
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
	

	List<T> findByCriteria(DetachedCriteria criteria,String[] associationPaths,FetchMode[] modes);

	
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
	List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults,String[] associationPaths,FetchMode[] modes);


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

	T findOneByProperty(final String propertyName,final Object value,String[] associationPaths,FetchMode[] modes);

	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	T findOneByProperties(final String[] propertyNames,final Object[] values,String[] associationPaths,FetchMode[] modes);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return\
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	T findOneByProperties(final Map<String,Object> propertyNameValues,String[] associationPaths,FetchMode[] modes);
	
	
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

	T findOneByCriterions(String[] associationPaths,FetchMode[] modes,final Criterion... criterions);
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 * 
	 * 实现了Dynamic association fetching 动态关联抓取
	 */

	T findOneByCriteria(DetachedCriteria criteria,String[] associationPaths,FetchMode[] modes);

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

	List<T> findByPageSize( int first, int pageSize, Order orderType,String[] associationPaths,FetchMode[] modes);
	
	
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

	List<T> findByRange( int first, int last, Order orderType,String[] associationPaths,FetchMode[] modes);
	
	
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

	List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType,String[] associationPaths,FetchMode[] modes);
	
	
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
	List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType,String[] associationPaths,FetchMode[] modes);
		

	//-------------------------------------------------------------------------

}
