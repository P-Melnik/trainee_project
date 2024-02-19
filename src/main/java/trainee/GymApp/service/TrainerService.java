package trainee.GymApp.service;

import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService extends Service<Trainer, TrainerDTO> {

    void update(Trainer trainer);

    Trainer findByUserName(String userName);

    void changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String password);

    void changeStatus(String username);

    List<Trainer> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName);

}
