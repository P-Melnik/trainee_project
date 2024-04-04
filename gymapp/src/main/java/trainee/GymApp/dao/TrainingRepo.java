package trainee.GymApp.dao;

import trainee.GymApp.entity.Training;

public interface TrainingRepo extends Repo<Training> {

    boolean deleteById(long id);

}
