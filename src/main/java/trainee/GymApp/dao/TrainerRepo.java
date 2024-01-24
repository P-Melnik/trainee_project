package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainer;

public interface TrainerRepo extends AbstractRepo<Trainer> {

    void updateTrainer(Trainer trainer);
}
