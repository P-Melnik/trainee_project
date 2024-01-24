package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.service.Generators;
import trainee.GymApp.service.TraineeService;

import java.util.List;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepo traineeRepo;

    @Override
    public Trainee getTraineeById(long id) {
        log.debug("Fetching trainee:" + id);
        return traineeRepo.findById(id);
    }

    @Override
    public void createTrainee(TraineeDTO traineeDTO) {
        log.info("Creating trainee: " + traineeDTO);
        Trainee trainee = new Trainee();
        trainee.setId(Generators.generateTraineeId());
        trainee.setActive(traineeDTO.isActive());
        trainee.setAddress(traineeDTO.getAddress());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        trainee.setUserId(Generators.generateUserId());
        trainee.setFirstName(traineeDTO.getFirstName());
        trainee.setLastName(traineeDTO.getLastName());
        trainee.setPassword(Generators.generatePassword());
        trainee.setUserName(Generators.generateUserName(trainee.getFirstName(),
                trainee.getLastName()));
        traineeRepo.create(trainee);
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        log.info("Updating trainee: " + trainee);
        traineeRepo.updateTrainee(trainee);
    }

    @Override
    public void deleteTraineeById(long traineeId) {
        log.info("Deleting trainee:" + traineeId);
        traineeRepo.deleteById(traineeId);
    }

    public List<Trainee> findAll() {
        log.debug("Fetching all trainees");
        return traineeRepo.findAllByType();
    }
}
