package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepo traineeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrainerRepo trainerRepo;

    @Override
    public Trainee getById(long id) {
        log.debug("Fetching trainee:" + id);
        return traineeRepo.findById(id);
    }

    @Override
    public void create(TraineeDTO traineeDTO) {
        log.info("Creating trainee: " + traineeDTO);
        validateTrainee(traineeDTO);
        User user = new User(traineeDTO.getFirstName(), traineeDTO.getLastName(), UserUtil.generateLogin(traineeDTO.getFirstName(), traineeDTO.getLastName()), UserUtil.generatePassword(), traineeDTO.isActive());
        Trainee trainee = new Trainee(traineeDTO.getDateOfBirth(), traineeDTO.getAddress(), user, new HashSet<>());
        traineeRepo.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        log.info("Updating trainee: " + trainee);
        traineeRepo.update(trainee);
    }

    @Override
    public boolean delete(long traineeId) {
        log.info("Deleting trainee:" + traineeId);
        return traineeRepo.delete(traineeId);
    }

    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeRepo.findAll();
    }

    @Override
    public boolean deleteByUserName(String userName) {
        return traineeRepo.deleteByUserName(userName);
    }

    @Override
    public Trainee findByUserName(String userName) {
        log.debug("fetching trainee " + userName);
        return traineeRepo.findByUserName(userName);
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        log.info("checking password for " + userName);
        userRepo.changePassword(userName, newPassword);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        log.debug("changing password " + userName);
        return userRepo.checkPassword(userName, password);
    }

    @Override
    public void changeStatus(String userName) {
        log.debug("changing status for " + userName);
        userRepo.changeStatus(userName);
    }

    @Override
    public List<Trainer> notAssignedTrainers(Trainee trainee) {
        log.debug("fetching not assigned trainers");
        Set<Trainer> set = trainee.getTrainers();
        return trainerRepo.getUnassignedTrainers(set);
    }

    @Transactional
    @Override
    public void updateTrainers(String userName, Trainer trainer) {
        log.debug("updating trainee trainers " + userName);
        Trainee trainee = traineeRepo.findByUserName(userName);
        trainee.getTrainers().add(trainer);
        update(trainee);
    }

    @Override
    public List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName) {
        return traineeRepo.getWithCriteria(traineeUserName, fromDate, toDate, trainerName, trainingName);
    }

    private void validateTrainee(TraineeDTO traineeDTO) {
        log.debug("validating trainee registration");
        if (traineeDTO.getFirstName() != null && traineeDTO.getLastName() != null && traineeDTO.getAddress() != null
                && traineeDTO.getDateOfBirth() != null && traineeDTO.getDateOfBirth().isBefore(LocalDate.now())) {
        } else {
            throw new RuntimeException("Fields should be not blank");
        }
    }

}
