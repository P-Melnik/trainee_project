package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainer;

public interface TrainerRepo extends Repo<Trainer> {

    void update(Trainer trainer);
}
