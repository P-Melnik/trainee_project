package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
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

    @Override
    public Trainer getById(long id) {
        log.debug("Fetching trainer:" + id);
        return trainerRepo.findById(id);
    }

    @Transactional
    @Override
    public void create(TrainerDTO trainerDTO) {
        log.info("Creating trainer: " + trainerDTO);
        User user = new User(trainerDTO.getFirstName(), trainerDTO.getLastName(), UserUtil.generateLogin(trainerDTO.getFirstName(), trainerDTO.getLastName()), UserUtil.generatePassword(), trainerDTO.isActive());
        Trainer trainer = new Trainer(trainerDTO.getTrainingType(), user);
        trainerRepo.create(trainer);
    }

    @Transactional
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
        return trainerRepo.findByUserName(userName);
    }

    @Transactional
    @Override
    public void changePassword(String userName, String newPassword) {
        trainerRepo.changePassword(userName, newPassword);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        return trainerRepo.checkPassword(userName, password);
    }

    @Transactional
    @Override
    public void changeStatus(String username) {
        trainerRepo.changeStatus(username);
    }

    @Override
    public List<Trainer> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainerRepo.getWithCriteria(trainerUserName, fromDate, toDate, traineeName);
    }

}
