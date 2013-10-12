package com.zhhcb.dao.base;



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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;






import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


  

/*
 * 封装了一些与集合相关的方法...如统计查询,分页查询
 * NOTE: 工具类,无状态
 * 
 * @author KEN
 * * @author KEN
 * @since 2012.07.31
 * 
 * 
 */

public interface BaseCollectionDao<T, ID extends Serializable> extends BaseEntityDao<T, ID>
{
	
	/**
	 * 原始的HQL查询
	 * 参考了HibernateTemplate
	 * Execute an HQL query.
	 * @param queryString a query expressed in Hibernate's query language
	 * @return a {@link List} containing the results of the query execution
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
		
	List<T> find(String queryString);

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

	List<T> find(String queryString, Object value);
	
	
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

	List<T> find(final String queryString, final Object... values);
	
	
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

	List<T> findByNamedParam(String queryString, String paramName, Object value);
	
	
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


	List<T> findByNamedParam(final String queryString, final String[] paramNames, final Object[] values);
	
	
	
	
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

	List<T> findByParamMap(final String queryString, final Map<String,Object> paramMap);

	
	
	
	

	
	
	
	
	/**
	 * 参考了HibernateTemplate
	 * Delete all given persistent instances.
	 * <p>This can be combined with any of the find methods to delete by query
	 * in two lines of code.
	 * @param entities the persistent instances to delete
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#delete(Object)
	 */
	
	void deleteAll(final Collection<T> entities);
	
	
	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	Iterable<T> findAll();
	
	
	/**
	 * Returns all instances of the type with the given IDs.
	 * 
	 * @param ids
	 * @return
	 */
	Iterable<T> findAll(Iterable<ID> ids);
	
	
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
	List<T> getAll();
	
	
	/**
	 * 
	 * 可使用查询缓存的getAll()
	 *  since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 
	 * @param cacheable
	 * @return
	 */

	List<T> getAll(boolean cacheable);
	
	/**
	 * 可使用查询缓存,并可排序
	 * since 1.1
	 * @see com.cscw.dao.support.BaseCollectionDao#getAll(java.lang.Class)
	 * 
	 */
	
	List<T> getAll(boolean cacheable,Order order);
	
	////////===========----------------------------
	
	
	
	
	
	
	//-------------------------------------------------------------------------
	// Convenience query methods for iteration 
	//-------------------------------------------------------------------------

	/**
	 * 参考了HibernateTemplate
	 * Execute a query for persistent instances.
	 * <p>Returns the results as an {@link Iterator}. Entities returned are
	 * initialized on demand. See the Hibernate API documentation for details.
	 * @param queryString a query expressed in Hibernate's query language
	 * @return an {@link Iterator} containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 * @see org.hibernate.Query#iterate
	 */
	Iterator<T> iterate(String queryString);
	
	

	
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
	Iterator<T> iterate(final String queryString, final Object... values);
	
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
	 
	List<T> getAll(boolean isAsc,String orderPropertyName, Criterion... criterions);
	
	
	
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
	 
	List<T> findByProperty(final String propertyName,final Object value);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一个属性值查询实体返回一个集合
	 * 
	 * @param propertyName
	 * @param value
	 * @param orderTypes
	 * @return
	 */
	List<T> findByProperty(final String propertyName,final Object value,Order... orderTypes);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	 
	List<T> findByProperties(final String[] propertyNames,final Object[] values);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	 
	List<T> findByProperties(final Map<String,Object> propertyNameValues);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	 
	List<T> findByCriterions( Criterion... criterions);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组条件查询返回一个集合
	 * 并可以设置排序方式
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	List<T> findByCriterions(Order orderType, Criterion... criterions); 
	
	
	/**
	 * 查找指定条件(和特定关联属性的子条件)的实体 集合
	 * 
	 * @param criterions      当前实体的查询条件
	 * @param associationPath 实体的关联路径,如 实体的属性(对象)的属性
	 * @param subCriterions   对当前类的associationPath路径的属性 增加 查询条件
	 * @return
	 */
	List<T> findByCriteriaWithSubCriterion(Criterion[] criterions, String associationPath, Criterion[] subCriterions);

	
	
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
	
	List<T> findByCriteria(DetachedCriteria criteria);

	
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

	List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults);


	//-------------------------------------------------------------------------
	
	
	
	//-------------------------------------------------------------------------
	// 查找指定条件的唯一实体   findOne
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
	 
	T findOneByProperty(final String propertyName,final Object value);

	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	 
	T findOneByProperties(final String[] propertyNames,final Object[] values);
	
	
	/**
	 * 查找指定条件的实体集合: 通过一组属性值查询返回一个集合
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	T findOneByProperties(final Map<String,Object> propertyNameValues);
	
	
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
	 
	T findOneByCriterions(final Criterion... criterions);
	
	
	/**
	 * 通过整个Criteria条件获得唯一对象(不保证唯一性,若查找到的结果大于一个,则抛异常)
	 * @param criteria
	 * @return
	 */
	T findOneByCriteria(DetachedCriteria criteria);

	//-------------------------------------------------------------------------
	
	
	
	
	
	
	

	//-------------------------------------------------------------------------
	// 统计查询    count
	//-------------------------------------------------------------------------
	
	/**
	 * 
	 * 统计指定类型实体的数量   
	 * 
	 * 
	 * @return
	 */

	long count();
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	Long countByProperty(final String propertyName,final Object value);
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	Long countByProperties(final String[] propertyNames,final Object[] values);
	
	
	/**
	 * 
	 * 统计指定属性值的实体的数量   
	 * 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	Long countByProperties(final Map<String,Object> propertyNameValues);
	
	
	

	/**
	 * 通过条件统计实体总数
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Long countByCriterions( Criterion... criterions);
	
	
	/**
	 * 通过条件统计实体总数
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	
	Long countByCriteria(final DetachedCriteria criteria);
	
	
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
	boolean isPropertyUnique(final String propertyName,final Object value);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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
	boolean isEntityExistByProperty(final String propertyName,final Object value);
	
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	boolean isEntityExistByProperties(final String[] propertyNames,final Object[] values);
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	boolean isEntityExistByProperties(final Map<String,Object> propertyNameValues);
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param criteria 外部的一组对于某实体的查询条件
	 * 
	 * @return
	 */
	boolean isEntityExistByCriteria(final DetachedCriteria criteria);
	
	
	
	/**
	 * 判断指定属性条件的实体是否存在(不保证唯一性)
	 * @param <T>
	 * @param 
	 * @param propertyName
	 * @param value
	 * 
	 * @return
	 */
	boolean isEntityExistByCriterions( Criterion... criterions);
	
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
	List<T> findByPageSize( int first, int pageSize, Order orderType,Criterion... criterions);
	
	
	/**
	 * 查询指定范围的实体  [first,last)
	 * 
	 * @param first   >=0   查询的起点
	 * @param last        查询的终点
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * 
	 * @return
	 */
	List<T> findByRange( int first, int last, Order orderType);
	
	
	/**
	 * 查询指定页面索引的所有实体
	 * 
	 * 
	 * @param pageIndex >=1   页面索引(第几页)
	 * @param pageSize      分页大小 
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 */
	List<T> findByPageIndex( int pageIndex , int pageSize, Order orderType);
	
	
	/**
	 * 查询指定范围的实体  [first,last)
	 * 
	 * @param first   >=0   查询的起点
	 * @param last        查询的终点
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * 
	 * @return
	 */
	List<T> findByRange( int first, int last, Order orderType,Criterion... criterions);
	
	
	/**
	 * 通过criteria进行分页查询
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param orderType  指定排序的属性及顺序 ,若null表示按映射文件的设置
	 * @return
	 */
	List<T> findCriteriaPage(final DetachedCriteria criteria,int firstResult, int maxResults, Order orderType);
		

	//-------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------
	// 查找特定条件的对象的属性    findProperty
	//-------------------------------------------------------------------------
	

	/**
	 * 查找一类对象的一个属性的集合
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	List findProperty(String propertyName);
	
	
	/**
	 * 查找一类对象的多个属性的集合的集合
	 * 
	 * @param propertyNames 
	 * @return
	 */
	
	List findProperties(String... propertyNames);
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性的集合
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	List findPropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName);
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性(的集合)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	
	List findPropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName,Order order);
	
	
	/**
	 * 已知对象的多个属性,查找其互不相同的属性的集合

	 * 
	
	 */
	List findDistinctPropertyByProperties(String[] knownProperties,Object[] knownPropertyValues,String propertyName,Order order) ;
	
	
	/**
	 * 根据一个已知属性,查找一类对象的多个属性的集合
	 * 
	 * @param propertyNames
	 *  * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值 
	 * @return
	 */
	
	List findPropertiesByProperty(String knownProperty,Object knownPropertyValue,String[] propertyNames);
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	List findPropertyByCriterions( String propertyName,Criterion... criterions);
	
	
	
	/**
	 * 查找指定条件的对象的多个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	List findPropertiesByCriterions( String[] propertyNames,Criterion... criterions);
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	List findPropertyByCriteria(DetachedCriteria criteria,String propertyName);

	
	
	
	List findPropertyByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String propertyName);
	
	
	/**
	 * 查找指定条件的对象的一个属性
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	List findPropertiesByCriteria(DetachedCriteria criteria,String... propertyNames);

	
	
	
	List findPropertiesByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String... propertyNames);


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
	
	Object findUniqueProperty(String propertyName);
	
	/**
	 * 查找一类对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * 
	 * @param propertyNames 
	 * @return
	 */
	Object[] findUniqueProperties(String... propertyNames);
	
	
	/**
	 * 根据一个已知属性,查找一类对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */

	Object findUniquePropertyByProperty(String knownProperty,Object knownPropertyValue,String propertyName);
	
	
	/**
	 * 根据一个已知ID,查找一类对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param id 已知的ID
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	
	Object findUniquePropertyById(Object id,String propertyName); 
	
	
	/**
	 * 根据一个已知属性,查找一类对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * 
	 * @param propertyNames
	 *  * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值 
	 * @return
	 */

	Object[] findUniquePropertiesByProperty(String knownProperty,Object knownPropertyValue,String[] propertyNames);
	
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	Object findUniquePropertyByCriterions( String propertyName,Criterion... criterions) ;
	
	
	
	/**
	 * 查找指定条件的对象的多个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	Object[] findUniquePropertiesByCriterions( String[] propertyNames,Criterion... criterions);
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	
	Object findUniquePropertyByCriteria(DetachedCriteria criteria,String propertyName);

	
	

	Object findUniquePropertyByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String propertyName);
	
	
	/**
	 * 查找指定条件的对象的一个属性(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * 
	 * @param propertyName 要查询的属性
	 * @return
	 */
	
	Object[] findUniquePropertiesByCriteria(DetachedCriteria criteria,String... propertyNames);

	
	

	Object[] findUniquePropertiesByCriteria(DetachedCriteria criteria, final int firstResult, final int maxResults,String... propertyNames);
	


	//-------------------------------------------------------------------------

	
	//id
	/**
	 * 根据一个已知属性,查找一类对象的ID(不保证唯一性,若查找到的结果大于一行,则抛出异常)
	 * @param <T>
	 * @param <T>
	 * 
	 * @param knownProperty 已知的属性
	 * @param knownPropertyValue 已知的属性的值
	 * @return
	 */
	ID findIdByProperty(String knownProperty,Object knownPropertyValue);
	
	/**
	 * 查找指定条件的对象的id(不保证唯一性,若查找到的结果大于一行,则抛异常)
	 * @param <T>
	 * @param <T>
	 * 
	 * @return
	 */
	ID findIdByCriterions(Criterion... criterions);
	
	
	ID findIdByCriteria(DetachedCriteria criteria);


	
	/**
	 * Returns a Page of entities meeting the paging restriction provided in the Pageable object.
	 * @param pageable
	 * @param criterions
	 * @return
	 */
	Page<T> findAll(Pageable pageable, Criterion[] criterions);

	
	Page<T> findAll(Pageable pageable, DetachedCriteria detachedCriteria);


	
	
	
}
