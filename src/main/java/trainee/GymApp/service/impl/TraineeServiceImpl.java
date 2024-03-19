package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.exceptions.ChangePasswordException;
import trainee.GymApp.exceptions.ChangeStatusException;
import trainee.GymApp.exceptions.DeleteException;
import trainee.GymApp.exceptions.UpdateException;
import trainee.GymApp.exceptions.UserNotFoundException;
import trainee.GymApp.exceptions.ValidationException;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.utils.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepo traineeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Trainee getById(long id) {
        log.debug("Fetching trainee:" + id);
        return traineeRepo.findById(id);
    }

    @Override
    public CredentialsDTO create(TraineeDTO traineeDTO) {
        log.info("Creating trainee: " + traineeDTO);
        validateTrainee(traineeDTO);
        String pass = UserUtil.generatePassword();
        User user = new User(traineeDTO.getFirstName(), traineeDTO.getLastName(),
                UserUtil.generateLogin(traineeDTO.getFirstName(), traineeDTO.getLastName()),
                encoder.encode(pass), traineeDTO.isActive());
        Trainee trainee = new Trainee(traineeDTO.getDateOfBirth(), traineeDTO.getAddress(), user, new HashSet<>());
        traineeRepo.create(trainee);
        return new CredentialsDTO(trainee.getUser().getUsername(), pass);
    }

    @Override
    public Trainee update(Trainee trainee) {
        log.info("Updating trainee: " + trainee);
        return traineeRepo.update(trainee).orElseThrow(() -> new UpdateException(trainee.getUser().getUsername()));
    }

    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeRepo.findAll();
    }

    @Override
    public void deleteByUserName(String userName) {
        log.debug("deleting trainee " + userName);
        if (!traineeRepo.deleteByUserName(userName)) {
            throw new DeleteException(userName);
        }
    }

    @Override
    public Trainee findByUserName(String userName) {
        log.debug("fetching trainee " + userName);
        return traineeRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        log.info("checking password for " + userName);
        if (userRepo.changePassword(userName, newPassword) == 0) {
            throw new ChangePasswordException(userName);
        }
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        log.debug("changing password " + userName);
        return userRepo.checkPassword(userName, password);
    }

    @Override
    public void changeStatus(String userName) {
        log.debug("changing status for " + userName);
        if (userRepo.changeStatus(userName) == 0) {
            throw new ChangeStatusException(userName);
        }
    }

    @Override
    public void updateTrainers(String userName, Trainer trainer) {
        log.debug("updating trainee trainers " + userName);
        Trainee trainee = traineeRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
        trainee.getTrainers().add(trainer);
        update(trainee);
    }

    @Override
    public Set<Trainer> getUnassignedTrainers(String userName) {
        return traineeRepo.getUnassignedTrainers(userName);
    }

    @Override
    public List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName) {
        return traineeRepo.getWithCriteria(traineeUserName, fromDate, toDate, trainerName, trainingName);
    }

    private void validateTrainee(TraineeDTO traineeDTO) {
        log.debug("validating trainee registration");
        if (traineeDTO.getFirstName() != null && traineeDTO.getLastName() != null && traineeDTO.getAddress() != null
                && traineeDTO.getDateOfBirth() != null && traineeDTO.getDateOfBirth().isBefore(LocalDate.now())) {
            return;
        } else {
            throw new ValidationException();
        }
    }

}
