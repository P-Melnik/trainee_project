package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
public class TrainerRepoImpl implements TrainerRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL = "SELECT t FROM Trainer t";
    private static final String FIND_BY_USERNAME = "SELECT t FROM Trainer t WHERE t.user.userName = :userName";
    private static final String FIND_TRAINEES = "SELECT tr FROM Trainer tr WHERE tr.user.userName = :trainerUserName";

    @Override
    public Trainer findById(long id) {
        log.debug("Find trainer by id trainer:" + id);
        return entityManager.find(Trainer.class, id);
    }

    @Override
    public void create(Trainer trainer) {
        log.debug("Creating trainer: " + trainer);
        entityManager.persist(trainer);
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        log.debug("Updating Trainer: " + trainer);
        return Optional.of(entityManager.merge(trainer));
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Fetching all Trainers");
        return entityManager.createQuery(SELECT_ALL, Trainer.class).getResultList();
    }

    @Override
    public Optional<Trainer> findByUserName(String userName) {
        log.debug("Find trainer by username: " + userName);
        TypedQuery<Trainer> query = entityManager.createQuery(FIND_BY_USERNAME, Trainer.class);
        query.setParameter("userName", userName);
        return Optional.of(query.getSingleResult());
    }

    @Override
    public Set<Trainee> getTrainees(String userName) {
        TypedQuery<Trainer> query = entityManager.createQuery(FIND_TRAINEES, Trainer.class);
        query.setParameter("trainerUserName", userName);
        Trainer trainer = query.getSingleResult();
        return trainer.getTrainees();
    }

    @Override
    public List<Training> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName) {
        log.debug("fetching trainers with criteria");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = query.from(Training.class);

        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");
        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(trainerJoin.get("user").get("userName"), trainerUserName));
        if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), java.sql.Date.valueOf(fromDate)));
        }
        if (toDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(trainingRoot.get("trainingDate"), java.sql.Date.valueOf(toDate)));
        }
        if (traineeName != null) {
            predicates.add(criteriaBuilder.equal(traineeJoin.get("user").get("userName"), traineeName));
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

}
