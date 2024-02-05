package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeRepo extends Repo<Trainee> {

    Trainee findByUserName(String userName);

    void deleteByUserName(String userName);

    void changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String password);

    void changeStatus(String userName);

    List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName);


    List<Trainer> notAssignedTrainers(String userName);

    void updateTrainers(String userName, Trainer trainer);

}
