package trainee.GymApp.service;

import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;

public interface TrainerService extends Service<Trainer, TrainerDTO> {

    void update(Trainer trainer);

}
