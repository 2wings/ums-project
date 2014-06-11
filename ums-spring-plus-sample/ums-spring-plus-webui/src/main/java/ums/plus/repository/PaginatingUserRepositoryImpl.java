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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.google.inject.Inject;
import com.mysema.query.types.OrderSpecifier;

import ums.plus.domain.User;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Repository
public class PaginatingUserRepositoryImpl implements PaginatingUserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaginatingUserRepositoryImpl.class);

    protected static final int NUMBER_OF_PERSONS_PER_PAGE = 5;

    private QueryDslJpaRepository<User, Long> userRepository;

    @PersistenceContext
    EntityManager em;

    // Test Only
    @Inject
    public PaginatingUserRepositoryImpl(QueryDslJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PaginatingUserRepositoryImpl() {

    }

    @PostConstruct
    public void initialize() {
        JpaEntityInformation<User, Long> UserEntityInfo = new JpaMetamodelEntityInformation<User, Long>(User.class,
                em.getMetamodel());
        userRepository = new QueryDslJpaRepository<User, Long>(UserEntityInfo, em);
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public User findUserByUsername(String username) {
        return userRepository.findOne(UserPredicates.usernameIs(username));
    }

    // public List<User> getUsers(int pageSize, int pageNum) {
    // CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    // CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    // Root<User> from = criteriaQuery.from(User.class);
    // CriteriaQuery<User> select = criteriaQuery.select(from);
    // TypedQuery<User> typedQuery = em.createQuery(select);
    // typedQuery.setFirstResult(0);
    // typedQuery.setMaxResults(pageSize);
    // List<User> userList = typedQuery.getResultList();
    // return userList;
    // }

    // public List<User> getAllUsers() {
    // List<User> ret = new ArrayList();
    // Query query = em.createNativeQuery("select * from user", User.class);
    // ret = query.getResultList();
    // return ret;
    // }

    /**
     * DOC crazyLau Comment method "search".
     * 
     * @param string
     * @return
     */
    // public List<User> searchById(String id) {
    // Query query = em.createNativeQuery("select * from user u where u.id = ? ", User.class);
    // query.setParameter(1, id);
    // List list = query.getResultList();
    // return list;
    // }
    //
    // public List<User> searchByUsername(String username) {
    // Query query = em.createNativeQuery("select * from user u where u.username = ? ", User.class);
    // query.setParameter(1, username);
    // List list = query.getResultList();
    // return list;
    // }

    /**
     * DOC crazyLau Comment method "findUserCount".
     * 
     * @param searchTerm
     * @return
     */
    public long findUserCount(String searchTerm) {
        return userRepository.count();
    }

    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "lastName");
    }

    /**
     * DOC crazyLau Comment method "findUserForPage".
     * 
     * @param searchTerm
     * @param pageIndex
     * @return
     */
    public List<User> findUsersForPage(String searchTerm, int page) {
        LOGGER.debug("Finding persons for page " + page + " with search term: " + searchTerm);

        // Passes the predicate and the page specification to the repository
        Page requestedPage = userRepository.findAll(UserPredicates.usernameIs(searchTerm),
                constructPageSpecification(page));

        return requestedPage.getContent();
    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, NUMBER_OF_PERSONS_PER_PAGE, sortByLastNameAsc());
        return pageSpecification;
    }

    /* (non-Javadoc)
     * @see ums.plus.repository.PaginatingUserRepository#findAllUsers()
     */
    @Override
    public List<User> findAllUsers() {
        LOGGER.debug("Finding all persons");

        return userRepository.findAll(sortByLastNameAsc());
    }

}
