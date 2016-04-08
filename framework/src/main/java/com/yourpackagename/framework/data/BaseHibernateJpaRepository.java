package com.yourpackagename.framework.data;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: Y Kamesh Rao
 * @created: 4/7/12 8:58 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public class BaseHibernateJpaRepository<T extends Entity, ID extends Serializable> implements BaseJpaRepository<T, ID> {
    protected @Autowired SessionFactory sessionFactory;
    protected Class<T> clazz;


    @SuppressWarnings("unchecked")
    public void setupEntityClass(Class clazz) {
        this.clazz = clazz;
    }


    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }


    @Transactional
    public T insert(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().save(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional
    public T update(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().update(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional
    public T insertOrUpdate(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().saveOrUpdate(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional(readOnly = true)
    public T findById(ID id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }



    @Transactional
    public Criteria createCriteria() {
        return sessionFactory.getCurrentSession().createCriteria(clazz);
    }
    
    public Collection<T> findAllByPage(int pageNum, int countPerPage, Order order) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(clazz);
        c.setMaxResults(countPerPage);
        c.setFirstResult(pageNum * countPerPage);
        return c.list();
    }




	public Collection<T> findAll() {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(clazz);
		return c.list();
	}


	public Collection<T> findByQuery(String query, boolean uniqueResult) {
		ArrayList<T> results = new ArrayList<T>();
		Query q = sessionFactory.getCurrentSession().createQuery(query);
		if(uniqueResult) 
			results.add((T)q.uniqueResult());
		else
			results = (ArrayList<T>)q.list();
		
		return results;
	}
}
