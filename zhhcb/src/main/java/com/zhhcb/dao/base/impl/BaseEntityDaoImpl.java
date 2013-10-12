package com.zhhcb.dao.base.impl;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;



import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockOptions;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.repository.CrudRepository;

import com.zhhcb.dao.base.BaseEntityDao;








/*
 * Session接口的子集--GeneralDAO的实现,简单调用Session的对应方法
 * 
 * 1:取得当前Session
 * 2:简单调用Session对应方法
 * 
 * 必须配合Spring事务管理来使用,由HibernateTransactionManager来开启事务
 * 
 * @see BaseEntityDao
 * @see Session
	@author KEN
 * @since 2012.07.31
 */

public class BaseEntityDaoImpl<T, ID extends Serializable> implements BaseEntityDao<T, ID>,CrudRepository<T, ID>
{
	/**
	 * 定义ENTITY的实际类型
	 */
	protected Class<T> entityClass;
	
	//会被IOC容器注入
	protected SessionFactory sessionFactory;
		
	
	/**
	 * 构造器，通过反射取得实际类型
	 */
	@SuppressWarnings("unchecked")
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
		
		entityClass = (Class<T>) 
							((ParameterizedType)getClass().getGenericSuperclass())
							.getActualTypeArguments()[0];
	}



	
	//Load--------
	@SuppressWarnings("unchecked")
	@Override
	public T load(ID id, LockOptions lockOptions)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.load(this.entityClass, id,lockOptions);
	}

	


	@SuppressWarnings("unchecked")
	@Override
	public T load(ID id)
	{
		Session session = sessionFactory.getCurrentSession();

		return (T) session.load(entityClass, id);
	}




	@SuppressWarnings("unchecked")
	@Override
	public <S extends T> S save(S entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (S) session.save(entity);
	}

	
	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities)
	{
		for(S entity : entities)
		{
			save(entity);
		}

		return entities;
	}




	@Override
	public void saveOrUpdate(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(entity);
	}



	@Override
	public void update(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.update(entity);
	}



	@SuppressWarnings("unchecked")
	@Override
	public T merge(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.merge(entity);
	}



	@Override
	public void persist(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.persist(entity);
	}

	

	@Override
	public void delete(ID id)
	{
		Session session = sessionFactory.getCurrentSession();

		session.delete(findOne(id));
	}
	

	@Override
	public void delete(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(entity);
	}
	

	
	@Override
	public void delete(Iterable<? extends T> entities)
	{
		Session session = sessionFactory.getCurrentSession();

		for(T entity : entities)
		{
			session.delete(entity);
		}
	}




	@Override
	public void deleteAll()
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		for(Object entity : criteria.list())
		{
			session.delete(entity);
		}
	}





	@Override
	public T get(ID id)
	{
		return findOne(id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T findOne(ID id)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.get(this.entityClass, id);
	}

	
	

	@SuppressWarnings("unchecked")
	@Override
	public T get(ID id, LockOptions lockOptions)
	{
		Session session = sessionFactory.getCurrentSession();
	
		return (T) session.get(this.entityClass, id, lockOptions);
	}




	@Override
	public void refresh(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.refresh(entity);
	}



	@Override
	public void refresh(T entity, LockOptions lockOptions)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.refresh(entity, lockOptions);
	}



	@Override
	public void replicate(T entity, ReplicationMode replicationMode)
	{
		Session session = sessionFactory.getCurrentSession();
		
		session.replicate(entity, replicationMode);
	}



	
	@Override
	public boolean contains(T entity)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return session.contains(entity);
	}









	@SuppressWarnings("unchecked")
	@Override
	public T loadByNaturalId(String attributeName,Object naturalIdValue)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.byNaturalId(entityClass).using(attributeName, naturalIdValue).load();
	}



	@SuppressWarnings("unchecked")
	@Override
	public T getReferenceByNaturalId(String attributeName, Object naturalIdValue)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.byNaturalId(entityClass).using(attributeName, naturalIdValue).getReference();
	}






	@SuppressWarnings("unchecked")
	@Override
	public T loadBySimpleNaturalId(Object naturalIdValue)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.bySimpleNaturalId(entityClass).load(naturalIdValue);
	}



	@SuppressWarnings("unchecked")
	@Override
	public T getReferenceBySimpleNaturalId(Object naturalIdValue)
	{
		Session session = sessionFactory.getCurrentSession();
		
		return (T) session.bySimpleNaturalId(entityClass).getReference(naturalIdValue);
	}

	
	
	
	
	
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(ID id,String associationPath, FetchMode mode)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(this.entityClass).add(Restrictions.idEq(id));
		
		criteria.setFetchMode(associationPath, mode);
		
		return (T) criteria.uniqueResult();
	}
	
	
	
	
	
	
	
	
	
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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(ID id,String[] associationPaths, FetchMode[] modes)
	{
		if(associationPaths.length != modes.length)
			throw new IllegalArgumentException("the length of propertyNames and the length of modes must be same!");
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(this.entityClass).add(Restrictions.idEq(id));
	
		
		for(int i=0; i<associationPaths.length; i++)
			criteria.setFetchMode(associationPaths[i], modes[i]);
		
		return (T) criteria.uniqueResult();
	}
	
	
	
	
	
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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(ID id,Map<String,FetchMode> associationPathsModes)
	{
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(this.entityClass).add(Restrictions.idEq(id));
		
		for(Entry<String, FetchMode> entry : associationPathsModes.entrySet())
		{
			criteria.setFetchMode(entry.getKey(), entry.getValue());
		}
		
		return (T) criteria.uniqueResult();
	}




	@Override
	public boolean exists(ID id)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.id())      				//只查询ID
				.add( Restrictions.idEq(id))  		//设置条件
				.setMaxResults(1);									//因为这里中需要判断是否存在,所以可设置最大返回一个结果
		
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();   

		return list.size() >= 1 ? true : false;
	}





	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<T> findAll()
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		return criteria.list();
	}





	
	  
	// 调用这个方法要保证ID的属性名为id 
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<T> findAll(Iterable<ID> ids)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(entityClass);
		
		
		List<ID> list = new ArrayList<ID>();
		
		for(ID id : ids)
		{
			list.add(id);
		}
		
		
		criteria.add(Restrictions.in("id", list));
		
		return criteria.list();
	}


	 //统计指定类型实体的数量   
	@Override
	public long count() 
	{
		Session session = sessionFactory.getCurrentSession();	
		
		Criteria criteria = session.createCriteria(entityClass)
				.setProjection(Projections.rowCount());            //统计 实体: count(*)
		
		
		return (Long)criteria.uniqueResult();
	}


}
