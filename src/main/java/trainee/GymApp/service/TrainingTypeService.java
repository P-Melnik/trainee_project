package trainee.GymApp.service;

import trainee.GymApp.entity.TrainingType;

import java.util.Set;

public interface TrainingTypeService {

    TrainingType get(String name);

    Set<TrainingType> getAll();
}
