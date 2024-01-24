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

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;

    @Autowired
    public Facade(TraineeService traineeService,
                  TrainerService trainerService,
                  TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee getTraineeById(long traineeId) {
        return traineeService.getTraineeById(traineeId);
    }

    public Trainer getTrainerById(long trainerId) {
        return trainerService.getTrainerById(trainerId);
    }

    public Training getTrainingById(long trainingId) {
        return trainingService.getTrainingById(trainingId);
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
        traineeService.createTrainee(traineeDTO);
    }

    public void createTrainer(TrainerDTO trainerDTO) {
        trainerService.createTrainer(trainerDTO);
    }

    public void createTraining(TrainingDTO trainingDTO) {
        trainingService.createTraining(trainingDTO);
    }


    public void updateTrainee(Trainee trainee) {
        traineeService.updateTrainee(trainee);
    }

    public void updateTrainer(Trainer trainer) {
        trainerService.updateTrainer(trainer);
    }


    public void deleteTraineeById(long traineeId) {
        traineeService.deleteTraineeById(traineeId);
    }

}
