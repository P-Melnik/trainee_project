package trainee.GymApp.dao;

import trainee.GymApp.entity.TrainingType;

import java.util.Set;

public interface TrainingTypeRepo {

    TrainingType getTrainingType(String type);

    Set<TrainingType> getAll();
}
