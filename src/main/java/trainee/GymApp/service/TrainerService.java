package trainee.GymApp.service;

import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer getTrainerById(long id);

    List<Trainer> findAll();

    void createTrainer(TrainerDTO trainerDTO);

    void updateTrainer(Trainer trainer);

}
