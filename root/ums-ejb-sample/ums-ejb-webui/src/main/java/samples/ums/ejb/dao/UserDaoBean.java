package samples.ums.ejb.dao;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

import samples.ums.ejb.domain.Role;
import samples.ums.ejb.domain.User;

import java.util.List;

@Stateful
public class UserDaoBean {

    @PersistenceContext(unitName = "user-persistence", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

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
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public User update(User user) {
        User existingUser = entityManager.find(User.class, user.getId());

        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
//        existingUser.setRole(user.getRole());
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
