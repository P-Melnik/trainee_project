package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.TrainingType;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Repository
public class TrainingTypeRepoImpl implements TrainingTypeRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_TRAINING_TYPE = "SELECT tt FROM TrainingType tt WHERE tt.trainingTypeName = :trainingType";
    private static final String SELECT_ALL = "SELECT t FROM TrainingType t";

    @Transactional
    @Override
    public TrainingType getTrainingType(String trainingType) {
        log.debug("fetching training type");
        TypedQuery<TrainingType> query = entityManager.createQuery(SELECT_TRAINING_TYPE, TrainingType.class);
        query.setParameter("trainingType", trainingType);
        return query.getSingleResult();
    }

    @Override
    public Set<TrainingType> getAll() {
        return new HashSet<>(entityManager.createQuery(SELECT_ALL, TrainingType.class).getResultList());
    }
}
