package trainee.GymApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.authentication.AuthService;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;
import trainee.GymApp.service.authentication.UnauthorizedAccessException;

import java.time.LocalDate;
import java.util.List;

@Service
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final AuthService authService;

    @Autowired
    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, AuthService authService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.authService = authService;
    }

    public void createTraineeProfile(TraineeDTO traineeDTO) {
        traineeService.create(traineeDTO);
    }

    public void createTrainerProfile(TrainerDTO trainerDTO) {
        trainerService.create(trainerDTO);
    }

    public Trainee getTraineeByUserName(String userName, String password) {
        authenticate(userName, password);
        return traineeService.findByUserName(userName);
    }

    public Trainer getTrainerByUserName(String userName, String password) {
        authenticate(userName, password);
        return trainerService.findByUserName(userName);
    }

    public Training getTraining(long trainingId) {

        return trainingService.getById(trainingId);
    }

    public List<Trainee> findAllTrainees(String userName, String password) {
        authenticate(userName, password);
        return traineeService.findAll();
    }

    public List<Trainer> findAllTrainers(String userName, String password) {
        authenticate(userName, password);
        return trainerService.findAll();
    }

    public List<Training> findAllTrainings(String userName, String password) {
        authenticate(userName, password);
        return trainingService.findAll();
    }

    public void createTraining(TrainingDTO trainingDTO, String userName, String password) {
        authenticate(userName, password);
        trainingService.create(trainingDTO);
    }

    public void updateTrainee(Trainee trainee, String userName, String password) {
        authenticate(userName, password);
        traineeService.update(trainee);
    }

    public void updateTrainer(Trainer trainer, String userName, String password) {
        authenticate(userName, password);
        trainerService.update(trainer);
    }

    public void deleteTrainee(String userName, String password) {
        authenticate(userName, password);
        traineeService.deleteByUserName(userName);
    }

    public void changePassword(String userName, String password) {
        authenticate(userName, password);
        traineeService.changePassword(userName, password);
    }

    public List<Trainer> getWithCriteria(String trainerUserName, LocalDate fromDate, LocalDate toDate, String traineeName, String password) {
        authenticate(trainerUserName, password);
        return trainerService.getWithCriteria(trainerUserName, fromDate, toDate, traineeName);
    }

    private void authenticate(String username, String password) {
        if (!authService.authenticate(username, password)) {
            throw new UnauthorizedAccessException("Authentication failed");
        }
    }
}
