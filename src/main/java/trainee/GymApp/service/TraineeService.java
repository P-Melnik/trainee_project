package trainee.GymApp.service;

import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;

import java.util.List;

public interface TraineeService {

    Trainee getTraineeById(long id);

    List<Trainee> findAll();

    void createTrainee(TraineeDTO traineeDTO);

    void updateTrainee(Trainee trainee);

    void deleteTraineeById(long traineeId);


}
