package trainee.GymApp.service;

import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;

import java.util.List;

public interface TrainingService {

    Training getTrainingById(long id);

    void createTraining(TrainingDTO trainingDTO);

    List<Training> findAll();
}
