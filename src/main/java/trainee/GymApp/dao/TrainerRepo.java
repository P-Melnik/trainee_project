package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TrainerRepo extends Repo<Trainer> {

    Trainer findByUserName(String userName);

    void changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String newPassword);

    void changeStatus(String userName);

    List<Trainer> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName);

    List<Trainer> getUnassignedTrainers(Set<Trainer> set);
}
