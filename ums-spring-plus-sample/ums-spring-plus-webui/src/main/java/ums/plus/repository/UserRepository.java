// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.stereotype.Repository;

import ums.plus.domain.User;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Repository
public class UserRepository  {

    
    private QueryDslJpaRepository<User, Long> userRepository;
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        JpaEntityInformation<User, Long> UserEntityInfo = new JpaMetamodelEntityInformation<User, Long>(User.class, em.getMetamodel());
        userRepository = new QueryDslJpaRepository<User, Long>(UserEntityInfo, em);
    }
    
    public User createUser(User user) {
        em.persist(user);
        return user;
    }
    
    public List<User> getUsers(int pageSize,int pageNum){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(from);
        TypedQuery<User> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(pageSize);
        List<User> userList = typedQuery.getResultList();
        return userList;
    }

    public List<User> getAllUsers() {
        List<User> ret = new ArrayList();
        Query query = em.createNativeQuery("select * from user", User.class);
        ret = query.getResultList();
        return ret;
    }

    /**
     * DOC crazyLau Comment method "search".
     * 
     * @param string
     * @return
     */
    public List<User> searchById(String id) {
        Query query = em.createNativeQuery("select * from user u where u.id = ? ", User.class);
        query.setParameter(1, id);
        List list = query.getResultList();
        return list;
    }

    public List<User> searchByUsername(String username) {
        Query query = em.createNativeQuery("select * from user u where u.username = ? ", User.class);
        query.setParameter(1, username);
        List list = query.getResultList();
        return list;
    }

    /**
     * DOC crazyLau Comment method "findUserCount".
     * @param searchTerm
     * @return
     */
    public long findUserCount(String searchTerm) {
        userRepository.count();
        return 0;
    }

    /**
     * DOC crazyLau Comment method "count".
     * @param any
     * @return
     */
    public Object count(com.mysema.query.types.Predicate any) {
        // TODO Auto-generated method stub
        return null;
    }
}
