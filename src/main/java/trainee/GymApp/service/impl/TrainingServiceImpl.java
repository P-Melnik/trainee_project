package trainee.GymApp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.Generators;
import trainee.GymApp.service.TrainingService;

import java.util.List;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepo trainingRepo;

    @Override
    public Training getTrainingById(long id) {
        log.debug("Fetching training:" + id);
        return trainingRepo.findById(id);
    }

    @Override
    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Creating training: " + trainingDTO);
        Training training = new Training();
        training.setId(Generators.generateTrainingId());
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setTrainingName(trainingDTO.getTrainingName());
        training.setTrainingDuration(trainingDTO.getTrainingDuration());
        training.setTrainingTypeId(trainingDTO.getTrainingType());
        training.setTrainerId(trainingDTO.getTrainerId());
        training.setTraineeId(trainingDTO.getTraineeId());
        trainingRepo.create(training);
    }

    public List<Training> findAll() {
        log.debug("Fetching all trainings");
        return trainingRepo.findAllByType();
    }
}
