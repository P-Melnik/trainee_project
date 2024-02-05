package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepo traineeRepo;

    @Override
    public Trainee getById(long id) {
        log.debug("Fetching trainee:" + id);
        return traineeRepo.findById(id);
    }

    @Transactional
    @Override
    public void create(TraineeDTO traineeDTO) {
        log.info("Creating trainee: " + traineeDTO);
        User user = new User(traineeDTO.getFirstName(), traineeDTO.getLastName(), UserUtil.generateLogin(traineeDTO.getFirstName(), traineeDTO.getLastName()), UserUtil.generatePassword(), traineeDTO.isActive());
        Trainee trainee = new Trainee(traineeDTO.getDateOfBirth(), traineeDTO.getAddress(), user, new HashSet<>());
        traineeRepo.create(trainee);
    }

    @Transactional
    @Override
    public void update(Trainee trainee) {
        log.info("Updating trainee: " + trainee);
        traineeRepo.update(trainee);
    }

    @Transactional
    @Override
    public void delete(long traineeId) {
        log.info("Deleting trainee:" + traineeId);
        traineeRepo.delete(traineeId);
    }

    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeRepo.findAll();
    }

    @Transactional
    @Override
    public void deleteByUserName(String userName) {
        traineeRepo.deleteByUserName(userName);
    }

    @Override
    public Trainee findByUserName(String userName) {
        return traineeRepo.findByUserName(userName);
    }

    @Transactional
    @Override
    public void changePassword(String userName, String newPassword) {
        traineeRepo.changePassword(userName, newPassword);
    }

    @Transactional
    @Override
    public boolean checkPassword(String userName, String password) {
        return traineeRepo.checkPassword(userName, password);
    }

    @Transactional
    @Override
    public void changeStatus(String userName) {
        traineeRepo.changeStatus(userName);
    }

    @Override
    public List<Trainer> notAssignedTrainers(String userName) {
        return traineeRepo.notAssignedTrainers(userName);
    }

    @Transactional
    @Override
    public void updateTrainers(String userName, Trainer trainer) {
        traineeRepo.updateTrainers(userName, trainer);
    }

    @Override
    public List<Training> getWithCriteria(String traineeUserName, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingName) {
        return traineeRepo.getWithCriteria(traineeUserName, fromDate, toDate, trainerName, trainingName);
    }

}
