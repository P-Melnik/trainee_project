package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;
import trainee.GymApp.exceptions.ChangePasswordException;
import trainee.GymApp.exceptions.ChangeStatusException;
import trainee.GymApp.exceptions.UserNotFoundException;
import trainee.GymApp.exceptions.ValidationException;
import trainee.GymApp.utils.UserUtil;
import trainee.GymApp.service.TrainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Override
    public Trainer getById(long id) {
        log.debug("Fetching trainer:" + id);
        return trainerRepo.findById(id);
    }

    @Override
    public CredentialsDTO create(TrainerDTO trainerDTO) {
        log.info("Creating trainer: " + trainerDTO);
        validateTrainer(trainerDTO);
        TrainingType trainingType = trainingTypeRepo.getTrainingType(trainerDTO.getTrainingType().getTrainingTypeName());
        User user = new User(trainerDTO.getFirstName(), trainerDTO.getLastName(), UserUtil.generateLogin(trainerDTO.getFirstName(), trainerDTO.getLastName()), UserUtil.generatePassword(), trainerDTO.isActive());
        Trainer trainer = new Trainer(trainingType, user);
        trainerRepo.create(trainer);
        return new CredentialsDTO(trainer.getUser().getUserName(), trainer.getUser().getPassword());
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
        return trainerRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        log.info("changing password for " + userName);
        if (userRepo.changePassword(userName, newPassword) == 0) {
            throw new ChangePasswordException(userName);
        }
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        log.debug("checking password");
        return userRepo.checkPassword(userName, password);
    }

    @Override
    public void changeStatus(String username) {
        log.debug("change status " + username);
        if (userRepo.changeStatus(username) == 0) {
            throw new ChangeStatusException(username);
        }
    }

    public Set<Trainee> getTrainees(String username) {
        return trainerRepo.getTrainees(username);
    }

    @Override
    public List<Training> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainerRepo.getWithCriteria(trainerUserName, fromDate, toDate, traineeName);
    }

    private void validateTrainer(TrainerDTO trainerDTO) {
        log.debug("validating trainee registration");
        if (trainerDTO.getTrainingType() != null && trainerDTO.getFirstName() != null && trainerDTO.getLastName() != null ) {
            log.debug("Trainer fields validated");
        } else {
            throw new ValidationException();
        }
    }

}
