package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.entity.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_BY_USERNAME = "SELECT u FROM User u WHERE u.userName = :userName";
    private static final String UPDATE_PASSWORD = "UPDATE User u SET u.password = :newPassword WHERE u.userName = :userName";
    private static final String CHECK_PASSWORD = "SELECT count(u) FROM User u WHERE u.userName = :userName AND u.password = :password";
    private static final String CHANGE_STATUS = "UPDATE User u SET u.isActive = NOT u.isActive WHERE u.userName = :userName";

    @Override
    public Optional<User> update(User user) {
        log.debug("Updating user: " + user);
        return Optional.of(entityManager.merge(user));
    }

    @Override
    public void create(User user) {
        log.debug("Creating user: " + user);
        entityManager.persist(user);
    }

    @Override
    public User findById(long id) {
        log.debug("Find user by id: " + id);
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUserName(String userName) {
        log.debug("Finding user by userName: " + userName);
        try {
            return entityManager.createQuery(SELECT_BY_USERNAME, User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            log.warn("User not found for userName: " + userName);
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        log.debug("Fetching all Users");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);

        TypedQuery<User> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public int changePassword(String userName, String password) {
        log.debug("Changing password for user: " + userName);
        Query query = entityManager.createQuery(UPDATE_PASSWORD);
        query.setParameter("newPassword", password);
        query.setParameter("userName", userName);
        return query.executeUpdate();
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        log.debug("Checking if password matches for user: " + userName);
        Query query = entityManager.createQuery(CHECK_PASSWORD);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        Long count = (Long) query.getSingleResult();
        return count == 1;
    }

    @Override
    public int changeStatus(String userName) {
        log.debug("Changing status for user: " + userName);
        Query query = entityManager.createQuery(CHANGE_STATUS);
        query.setParameter("userName", userName);
        return query.executeUpdate();
    }
}
