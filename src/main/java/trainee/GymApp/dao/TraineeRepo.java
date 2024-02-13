package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeRepo extends Repo<Trainee> {

    Trainee findByUserName(String userName);

    boolean deleteByUserName(String userName);

    List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName);

}
