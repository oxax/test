/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oxax.persistence.service;

import com.oxax.persistence.entity.OnlineStore;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Morph
 */
@Stateless
@LocalBean
public class PersistenceService implements Serializable {

    @PersistenceContext(unitName = "mywildflyappPU")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T create(T t) {
        em.persist(t);
        em.flush();
        return t;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T find(Class<T> type, Object id) {
        return em.find(type, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> void delete(T t) {
        System.out.println("Removing :::: " + t);
        em.remove(em.merge(t));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T update(T t) {
        return em.merge(t);
    }

    private static <T> T getSingleResult(Query query) {
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> List<T> findWithQuery(String sql, int start, int max) {
        try {
            
            //em.createQuery("SELECT o FROM Product o WHERE o.productType.name =").getResultList();
            
            return em.createQuery(sql).setFirstResult(start).setMaxResults(max).getResultList();
        } catch (Exception e) {
            System.err.println(e);
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T findSingleWithNativeQuery(String sql, Class<T> c){
        return (T)em.createNativeQuery(sql, c).getSingleResult();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T findSingleWithQuery(String sql) {
        try {
            
            //em.createQuery("SELECT o FROM PolicyDetailInfo o WHERE o.branchCode.name").getResultList();
            
            return (T)em.createQuery(sql).getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<OnlineStore> getAllOnlineStore(){
        return em.createQuery("SELECT o FROM OnlineStore o", OnlineStore.class).getResultList();
    }
    
}
