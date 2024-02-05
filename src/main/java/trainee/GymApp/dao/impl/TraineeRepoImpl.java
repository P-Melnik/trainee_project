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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class TraineeRepoImpl implements TraineeRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TrainerRepo trainerRepo;

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
    public void delete(long id) {
        log.debug("Deleting Trainee by id: " + id);
        Trainee trainee = findById(id);
        if (trainee != null) {
            entityManager.remove(trainee);
        }
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Fetching all Trainees");
        return entityManager.createQuery("SELECT t FROM Trainee t", Trainee.class).getResultList();
    }

    @Override
    public void deleteByUserName(String userName) {
        log.debug("Deleting trainee by username: " + userName);

        String jpql = "DELETE FROM Trainee t " +
                "WHERE EXISTS (SELECT 1 FROM User u WHERE u.id = t.user.id AND u.userName = :userName)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("userName", userName);

        query.executeUpdate();
    }

    @Override
    public Trainee findByUserName(String userName) {
        log.debug("Find trainee by username: " + userName);
        String jpql = "SELECT t FROM Trainee t WHERE t.user.userName = :userName";
        TypedQuery<Trainee> query = entityManager.createQuery(jpql, Trainee.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        userRepo.changePassword(userName, newPassword);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        return userRepo.checkPassword(userName, password);
    }

    // Activate / deactivate
    @Override
    public void changeStatus(String userName) {
        userRepo.changeStatus(userName);
    }

    // Get trainers list that are not assigned on trainee by trainee's username.
    @Override
    public List<Trainer> notAssignedTrainers(String userName) {
        log.debug("fetching not assigned trainers");

        Trainee trainee = findByUserName(userName);
        Set<Trainer> set = trainee.getTrainers();
        return trainerRepo.getUnassignedTrainers(set);
    }

    // Update trainees trainers list
    @Override
    public void updateTrainers(String userName, Trainer trainer) {
        log.debug("updating trainers");
        Trainee trainee = findByUserName(userName);
        trainee.getTrainers().add(trainer);
        update(trainee);
    }

    // Get Trainee Trainings List by trainee username and criteria (from date, to date, trainer
    //name, training type).
    @Override
    public List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName) {
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
