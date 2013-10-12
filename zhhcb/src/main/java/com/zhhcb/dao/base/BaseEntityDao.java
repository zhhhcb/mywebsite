package com.zhhcb.dao.base;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.LockOptions;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;

/*
 * Session接口的子集--BaseEntityDao,其实现GeneralDAOImpl是简单调用Session的对应方法
 * PS: 抛弃了Deprecated的方法; 向get/load方法增加范型,增加NaturalId的支持(Hibernate 4.1)
 * 
 * @see Session
 * @author Ken
 *  * @author KEN
 * @since 2012.07.31
 */

public interface BaseEntityDao<T, ID extends Serializable>
{

	
	//Get--------
	/**
	 * Return the persistent instance of the given entity class with the given identifier,
	 * or null if there is no such persistent instance. (If the instance is already associated
	 * with the session, return that instance. This method never returns an uninitialized instance.)
	 *
	 * @param clazz a persistent class
	 * @param id an identifier
	 *
	 * @return a persistent instance or null
	 */
	T get(ID id);
	
	
	/**
	 * Retrives an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	T findOne(ID id);

	

	/**
	 * Return the persistent instance of the given entity class with the given identifier,
	 * or null if there is no such persistent instance. (If the instance is already associated
	 * with the session, return that instance. This method never returns an uninitialized instance.)
	 * Obtain the specified lock mode if the instance exists.
	 *
	 * @param clazz a persistent class
	 * @param id an identifier
	 * @param lockOptions the lock mode
	 *
	 * @return a persistent instance or null
	 */
	T get(ID id, LockOptions lockOptions);





	//Get--------end---------------------------------
	
	
	
	
	//Load--------


	/**
	 * Return the persistent instance of the given entity class with the given identifier,
	 * assuming that the instance exists. This method might return a proxied instance that
	 * is initialized on-demand, when a non-identifier method is accessed.
	 * <br><br>
	 * You should not use this method to determine if an instance exists (use <tt>get()</tt>
	 * instead). Use this only to retrieve an instance that you assume exists, where non-existence
	 * would be an actual error.
	 *
	 * @param theClass a persistent class
	 * @param id a valid identifier of an existing persistent instance of the class
	 *
	 * @return the persistent instance or proxy
	 */
	T load(ID id);
	
	
	/**
	 * Return the persistent instance of the given entity class with the given identifier,
	 * obtaining the specified lock mode, assuming the instance exists.
	 *
	 * @param theClass a persistent class
	 * @param id a valid identifier of an existing persistent instance of the class
	 * @param lockOptions contains the lock level
	 * @return the persistent instance or proxy
	 */
	T load(ID id, LockOptions lockOptions);

	

	
	//Load--------end----------------
	
	
	
	//Save-----------------

	 
	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	<S extends T> S save(S entity);

	/**
	 * Saves all given entities.
	 * 
	 * @param entities
	 * @return the saved entities
	 * @throws IllegalArgumentException in case the given entity is (@literal null}.
	 */
	<S extends T> Iterable<S> save(Iterable<S> entities);
		


	
	//Save--------------end--------------------
	
	
	//persist
	/**
	 * Make a transient instance persistent. This operation cascades to associated
	 * instances if the association is mapped with {@code cascade="persist"}
	 * <p/>
	 * The semantics of this method are defined by JSR-220.
	 *
	 * @param object a transient instance to be made persistent
	 */
	void persist(T entity);
	
	
	
	
	//saveOrUpdate------------------
	/**
	 * Either {@link #save(Object)} or {@link #update(Object)} the given
	 * instance, depending upon resolution of the unsaved-value checks (see the
	 * manual for discussion of unsaved-value checking).
	 * <p/>
	 * This operation cascades to associated instances if the association is mapped
	 * with {@code cascade="save-update"}
	 *
	 * @param object a transient or detached instance containing new or updated state
	 *
	 * @see Session#save(java.lang.Object)
	 * @see Session#update(Object object)
	 */
	void saveOrUpdate(T entity);



	//saveOrUpdate-----------------end------------
	
	
	//update-----------------------------
	/**
	 * Update the persistent instance with the identifier of the given detached
	 * instance. If there is a persistent instance with the same identifier,
	 * an exception is thrown. This operation cascades to associated instances
	 * if the association is mapped with {@code cascade="save-update"}
	 *
	 * @param object a detached instance containing updated state
	 */
	void update(T entity);



	
	//replicate-----------------
	/**
	 * Persist the state of the given detached instance, reusing the current
	 * identifier value.  This operation cascades to associated instances if
	 * the association is mapped with {@code cascade="replicate"}
	 *
	 * @param object a detached instance of a persistent class
	 * @param replicationMode The replication mode to use
	 */
	void replicate(T entity, ReplicationMode replicationMode);

	

	//replicate-----------------end----------------
	

	
	
	//merge
	/**
	 * Copy the state of the given object onto the persistent object with the same
	 * identifier. If there is no persistent instance currently associated with
	 * the session, it will be loaded. Return the persistent instance. If the
	 * given instance is unsaved, save a copy of and return it as a newly persistent
	 * instance. The given instance does not become associated with the session.
	 * This operation cascades to associated instances if the association is mapped
	 * with {@code cascade="merge"}
	 * <p/>
	 * The semantics of this method are defined by JSR-220.
	 *
	 * @param object a detached instance with state to be copied
	 *
	 * @return an updated persistent instance
	 */
	T merge(T entity);

	
	
	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	void delete(ID id);
	
	
	//delete
	/**
	 * Remove a persistent instance from the datastore. The argument may be
	 * an instance associated with the receiving <tt>Session</tt> or a transient
	 * instance with an identifier associated with existing persistent state.
	 * This operation cascades to associated instances if the association is mapped
	 * with {@code cascade="delete"}
	 *
	 * @param object the instance to be removed
	 */
	void delete(T entity);

	
	/**
	 * Deletes the given entities.
	 * 
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
	 */
	void delete(Iterable<? extends T> entities);

	
	/**
	 * Deletes all entities managed by the repository.
	 */
	void deleteAll();


	//

	/**
	 * Re-read the state of the given instance from the underlying database. It is
	 * inadvisable to use this to implement long-running sessions that span many
	 * business tasks. This method is, however, useful in certain special circumstances.
	 * For example
	 * <ul>
	 * <li>where a database trigger alters the object state upon insert or update
	 * <li>after executing direct SQL (eg. a mass update) in the same session
	 * <li>after inserting a <tt>Blob</tt> or <tt>Clob</tt>
	 * </ul>
	 *
	 * @param object a persistent or detached instance
	 */
	void refresh(T entity);



	

	/**
	 * Re-read the state of the given instance from the underlying database, with
	 * the given <tt>LockMode</tt>. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however,
	 * useful in certain special circumstances.
	 *
	 * @param object a persistent or detached instance
	 * @param lockOptions contains the lock mode to use
	 */
	void refresh(T entity, LockOptions lockOptions);



	
	/**
	 * Check if this instance is associated with this <tt>Session</tt>.
	 *
	 * @param object an instance of a persistent class
	 * @return true if the given instance is associated with this <tt>Session</tt>
	 */
	boolean contains(T entity);

	
	
	
	/*
	 * 相当于 session.byNaturalId(entityClass).using(attributeName, naturalIdValue).load()
	 */
	
	T loadByNaturalId(String attributeName, Object naturalIdValue);

	/*
	 * 相当于 session.byNaturalId(entityClass).using(attributeName, naturalIdValue).getReference()
	 */
	
	T getReferenceByNaturalId(String attributeName, Object naturalIdValue);
	
	


	
	/*
	 * 相当于 session.bySimpleNaturalId(entityClass).load(naturalIdValue)
	 */
	
	T loadBySimpleNaturalId(Object naturalIdValue);
	
	
	/*
	 * 相当于 session.bySimpleNaturalId(entityClass).getReference(naturalIdValue)
	 */
	T getReferenceBySimpleNaturalId(Object naturalIdValue);
	
	
	//-------Dynamic association fetching 动态关联抓取 的Get
	
	/**
	 * Dynamic association fetching 动态关联抓取 的Get
	 * 
	 * 可运行时指定特定属性的抓取策略
	 * 
	 * @param clazz
	 * @param id
	 * @param associationPath  : 指定的属性  
	 * @param mode : The fetch mode for the referenced association
	 * @return a persistent instance or null
	 */
	
	T get(ID id,String associationPath, FetchMode mode);
		
	
	
	/**
	 * Dynamic association fetching 动态关联抓取 的Get
	 * 
	 * 可运行时指定特定属性的抓取策略
	 * 
	 * @param clazz
	 * @param id
	 * @param associationPaths  : 指定的属性(集合)  
	 * @param modes : The fetch mode for the referenced association
	 * @return a persistent instance or null
	 */
	
	T get(ID id,String[] associationPaths, FetchMode[] modes);
	
	
	
	/**
	 * Dynamic association fetching 动态关联抓取 的Get
	 * 
	 * 可运行时指定特定属性的抓取策略
	 * 
	 * @param clazz
	 * @param id
	 * @param associationPaths  : 指定的属性(集合)  
	 * @param modes : The fetch mode for the referenced association
	 * @return a persistent instance or null
	 */

	T get(ID id,Map<String,FetchMode> associationPathsModes);
}
