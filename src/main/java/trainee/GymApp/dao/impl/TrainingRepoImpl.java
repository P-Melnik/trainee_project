package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.entity.Training;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TrainingRepoImpl implements TrainingRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL = "SELECT t FROM Training t";

    @Override
    public void create(Training training) {
        log.debug("Creating training: " + training);
        entityManager.persist(training);
    }

    @Override
    public Training findById(long id) {
        log.debug("Find training by id: " + id);
        return entityManager.find(Training.class, id);
    }

    @Override
    public List<Training> findAll() {
        log.debug("Fetching all Trainings");
        return entityManager.createQuery(SELECT_ALL, Training.class).getResultList();
    }

    @Override
    public Optional<Training> update(Training training) {
        log.debug("Updating training: " + training);
        return Optional.of(entityManager.merge(training));
    }

}
