package ums.reactor.ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.annotation.ejb.cache.simple.CacheConfig;

import ums.reactor.domain.User;

@Stateful
@CacheConfig (maxSize=100000, idleTimeoutSeconds=300, removalTimeoutSeconds=0)
public class UserDaoBean {

    @PersistenceContext(unitName = "user-persistence")
    private EntityManager entityManager;

    public User find(String userName) {
        try {
            Query query = entityManager.createQuery("select u from User u where u.userName = :userName");
            query.setParameter("userName", userName);
            List<User> users = query.getResultList();
            if (users.size() > 0) {
                return users.get(0);
            }else{
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public User find(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    public List<User> readAll() {
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(User.class));
        List<User> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result;
    }

    public User update(User user) {
        User existingUser = entityManager.find(User.class, user.getId());

        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        // existingUser.setRole(user.getRole());
        entityManager.merge(existingUser);
        return existingUser;
    }

    public Boolean delete(User user) {
        User existingUser = entityManager.find(User.class, user.getId());
        if (existingUser == null) {
            return false;
        }

        entityManager.remove(existingUser);
        return true;
    }

    public User read(User user) {
        return user;
    }
}
