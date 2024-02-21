package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;
import trainee.GymApp.service.TrainerService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Trainer getById(long id) {
        log.debug("Fetching trainer:" + id);
        return trainerRepo.findById(id);
    }

    @Override
    public void create(TrainerDTO trainerDTO) {
        log.info("Creating trainer: " + trainerDTO);
        validateTrainer(trainerDTO);
        User user = new User(trainerDTO.getFirstName(), trainerDTO.getLastName(), UserUtil.generateLogin(trainerDTO.getFirstName(), trainerDTO.getLastName()), UserUtil.generatePassword(), trainerDTO.isActive());
        Trainer trainer = new Trainer(trainerDTO.getTrainingType(), user);
        trainerRepo.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        log.info("Updating trainer: " + trainer);
        trainerRepo.update(trainer);
    }

    public List<Trainer> findAll() {
        log.debug("Fetching all trainers");
        return trainerRepo.findAll();
    }

    @Override
    public Trainer findByUserName(String userName) {
        log.debug("fetching trainer " + userName);
        return trainerRepo.findByUserName(userName);
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        log.info("changing password for " + userName);
        userRepo.changePassword(userName, newPassword);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        log.debug("checking password");
        return userRepo.checkPassword(userName, password);
    }

    @Override
    public void changeStatus(String username) {
        log.debug("change status " + username);
        userRepo.changeStatus(username);
    }

    @Override
    public List<Trainer> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainerRepo.getWithCriteria(trainerUserName, fromDate, toDate, traineeName);
    }

    private void validateTrainer(TrainerDTO trainerDTO) {
        log.debug("validating trainee registration");
        if (trainerDTO.getTrainingType() != null && trainerDTO.getFirstName() != null && trainerDTO.getLastName() != null ) {
        } else {
            throw new RuntimeException("Fields should be not blank");
        }
    }

}
