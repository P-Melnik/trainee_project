package trainee.GymApp.service;

import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;

public interface TrainingService extends Service<Training, TrainingDTO> {

    void createTraining(TrainingDTO trainingDTO);

    void delete(long id);

}
