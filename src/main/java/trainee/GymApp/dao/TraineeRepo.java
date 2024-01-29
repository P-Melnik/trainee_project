package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainee;

public interface TraineeRepo extends Repo<Trainee> {

    void update(Trainee trainee);

    void delete(long id);

}
