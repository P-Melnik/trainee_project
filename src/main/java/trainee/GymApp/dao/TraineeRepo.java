package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainee;

public interface TraineeRepo extends AbstractRepo<Trainee> {

    void updateTrainee(Trainee trainee);

    void deleteById(long id);

}
