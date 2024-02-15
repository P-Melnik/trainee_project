package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TraineeRepoImpl implements TraineeRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL = "SELECT t FROM Trainee t";
    private static final String DELETE_BY_USERNAME = "DELETE FROM Trainee t WHERE t.user.id IN (SELECT u.id FROM User u WHERE u.userName = :userName)";
    private static final String SELECT_BY_USERNAME = "SELECT t FROM Trainee t WHERE t.user.userName = :userName";

    @Override
    public Trainee findById(long id) {
        log.debug("Find trainee by id: " + id);
        return entityManager.find(Trainee.class, id);
    }

    @Override
    public void create(Trainee trainee) {
        log.debug("Creating trainee: " + trainee);
        entityManager.persist(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        log.debug("Updating Trainee: " + trainee);
        entityManager.merge(trainee);
    }

    @Override
    public boolean delete(long id) {
        log.debug("Deleting Trainee by id: " + id);
        Trainee trainee = findById(id);
        if (trainee != null) {
            entityManager.remove(trainee);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Fetching all Trainees");
        return entityManager.createQuery(SELECT_ALL, Trainee.class).getResultList();
    }

    @Override
    public boolean deleteByUserName(String userName) {
        log.debug("Deleting trainee by username: " + userName);

        Query query = entityManager.createQuery(DELETE_BY_USERNAME);
        query.setParameter("userName", userName);

        int deleteCount = query.executeUpdate();
        return deleteCount > 0;
    }

    @Override
    public Trainee findByUserName(String userName) {
        log.debug("Find trainee by username: " + userName);
        TypedQuery<Trainee> query = entityManager.createQuery(SELECT_BY_USERNAME, Trainee.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    }

    // Get Trainee Trainings List by trainee username and criteria (from date, to date, trainer
    //name, training type).
    @Override
    public List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName) {
        log.debug("fetching trainings with criteria");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = query.from(Training.class);

        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");
        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(traineeJoin.get("user").get("userName"), traineeUserName));

        if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), java.sql.Date.valueOf(fromDate)));
        }
        if (toDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(trainingRoot.get("trainingDate"), java.sql.Date.valueOf(toDate)));
        }
        if (trainerName != null) {
            predicates.add(criteriaBuilder.equal(trainerJoin.get("user").get("userName"), trainerName));
        }
        if (trainingName != null) {
            predicates.add(criteriaBuilder.equal(trainingRoot.get("trainingName"), trainingName));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
