package trainee.GymApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;

import java.util.List;

@Service
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public Facade(TraineeService traineeService,
                  TrainerService trainerService,
                  TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee getTrainee(long traineeId) {
        return traineeService.getById(traineeId);
    }

    public Trainer getTrainer(long trainerId) {
        return trainerService.getById(trainerId);
    }

    public Training getTraining(long trainingId) {
        return trainingService.getById(trainingId);
    }

    public List<Trainee> findAllTrainees() {
        return traineeService.findAll();
    }

    public List<Trainer> findAllTrainers() {
        return trainerService.findAll();
    }

    public List<Training> findAllTrainings() {
        return trainingService.findAll();
    }

    public void createTrainee(TraineeDTO traineeDTO) {
        traineeService.create(traineeDTO);
    }

    public void createTrainer(TrainerDTO trainerDTO) {
        trainerService.create(trainerDTO);
    }

    public void createTraining(TrainingDTO trainingDTO) {
        trainingService.create(trainingDTO);
    }

    public void updateTrainee(Trainee trainee) {
        traineeService.update(trainee);
    }

    public void updateTrainer(Trainer trainer) {
        trainerService.update(trainer);
    }

    public void deleteTraineeById(long traineeId) {
        traineeService.delete(traineeId);
    }

}
