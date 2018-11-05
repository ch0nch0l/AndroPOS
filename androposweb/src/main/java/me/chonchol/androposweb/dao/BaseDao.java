package me.chonchol.androposweb.dao;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class BaseDao {

    @PersistenceContext
    protected EntityManager em;

    protected Session getCurrentSession(){
        return em.unwrap(Session.class);
    }

    protected org.hibernate.Query hibernateQuery(String query, Class dtoClazz){
        return getCurrentSession().createQuery(query).setResultTransformer(Transformers.aliasToBean(dtoClazz));
    }

    protected org.hibernate.Query hibernateUniqueQuery(String query){
        return getCurrentSession().createQuery(query);
    }

    protected javax.persistence.Query persistenceQuery(String query){
        return em.createNativeQuery(query);
    }

    protected javax.persistence.Query persistenceQuery(String query, Class entityClazz){
        return em.createNativeQuery(query, entityClazz);
    }
}
