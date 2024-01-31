package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;
import trainee.GymApp.service.TraineeService;

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

    @Override
    public void create(TraineeDTO traineeDTO) {
        log.info("Creating trainee: " + traineeDTO);
        User user = new User(0L, traineeDTO.getFirstName(), traineeDTO.getLastName(), UserUtil.generateLogin(traineeDTO.getFirstName(), traineeDTO.getLastName()), UserUtil.generatePassword(), traineeDTO.isActive());
        Trainee trainee = new Trainee(0L, traineeDTO.getDateOfBirth(), traineeDTO.getAddress(), user);
        traineeRepo.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        log.info("Updating trainee: " + trainee);
        traineeRepo.update(trainee);
    }

    @Override
    public void delete(long traineeId) {
        log.info("Deleting trainee:" + traineeId);
        traineeRepo.delete(traineeId);
    }

    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeRepo.findAll();
    }
}
