package trainee.GymApp.service;

import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService extends Service<Trainee, TraineeDTO> {

    void update(Trainee trainee);

    void delete(long traineeId);

    void deleteByUserName(String userName);

    Trainee findByUserName(String userName);

    void changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String password);

    void changeStatus(String userName);

    List<Trainer> notAssignedTrainers(String userName);

    void updateTrainers(String userName, Trainer trainer);

    List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName);

}
