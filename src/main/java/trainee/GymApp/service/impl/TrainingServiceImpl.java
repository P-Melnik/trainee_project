package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.TrainingService;

import java.util.List;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepo trainingRepo;

    @Override
    public Training getById(long id) {
        log.debug("Fetching training:" + id);
        return trainingRepo.findById(id);
    }

    @Transactional
    @Override
    public void create(TrainingDTO trainingDTO) {
        log.info("Creating training: " + trainingDTO);
        Training training = new Training(trainingDTO.getTrainee(), trainingDTO.getTrainer(), trainingDTO.getTrainingName(), trainingDTO.getTrainingType(), trainingDTO.getTrainingDate(), trainingDTO.getTrainingDuration());
        trainingRepo.create(training);
    }

    public List<Training> findAll() {
        log.debug("Fetching all trainings");
        return trainingRepo.findAll();
    }
}
