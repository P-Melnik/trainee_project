package trainee.GymApp.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.TrainingType;

@Repository
public class TrainingTypeRepoImpl implements TrainingTypeRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public TrainingType getTrainingType(String trainingType) {
        String jpql = "SELECT tt FROM TrainingType tt WHERE tt.trainingTypeName = :trainingType";
        TypedQuery<TrainingType> query = entityManager.createQuery(jpql, TrainingType.class);
        query.setParameter("trainingType", trainingType);

        return query.getSingleResult();
    }
}
