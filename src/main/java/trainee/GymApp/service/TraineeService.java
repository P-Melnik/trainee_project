package trainee.GymApp.service;

import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TraineeService extends Service<Trainee, TraineeDTO> {

    Trainee update(Trainee trainee);

    void deleteByUserName(String userName);

    Trainee findByUserName(String userName);

    void changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String password);

    void changeStatus(String userName);

    Set<Trainer> getUnassignedTrainers(String userName);

    void updateTrainers(String userName, Trainer trainer);

    List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName);

}
