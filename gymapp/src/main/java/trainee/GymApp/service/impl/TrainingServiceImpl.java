package trainee.GymApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.TrainingService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private TraineeRepo traineeRepo;

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Override
    public Training getById(long id) {
        log.debug("Fetching training:" + id);
        return trainingRepo.findById(id);
    }

    @Override
    public List<Training> findAll() {
        log.debug("Fetching all trainings");
        return trainingRepo.findAll();
    }

    @Override
    public void createTraining(TrainingDTO trainingDTO) {
        Training training = new Training(trainingDTO.getTrainee(), trainingDTO.getTrainer(), trainingDTO.getTrainingName(),
                trainingDTO.getTrainingType(), trainingDTO.getTrainingDate(), trainingDTO.getTrainingDuration());
        trainingRepo.create(training);
    }

    @Override
    public CredentialsDTO create(TrainingDTO trainingDTO) {
        return new CredentialsDTO();
    }
}
