package trainee.GymApp.dao;

import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrainerRepo extends Repo<Trainer> {

    Optional<Trainer> findByUserName(String userName);

    List<Training> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName);

    Set<Trainee> getTrainees(String userName);

}
