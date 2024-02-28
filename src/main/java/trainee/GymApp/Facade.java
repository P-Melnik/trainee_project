package trainee.GymApp;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainerForSet;
import trainee.GymApp.dto.TrainersToAdd;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.TrainingTypeService;
import trainee.GymApp.service.authentication.AuthService;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;
import trainee.GymApp.service.authentication.UnauthorizedAccessException;
import trainee.GymApp.utils.LoggerUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final AuthService authService;
    private final LoggerUtil loggerUtil;

    @Autowired
    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService,
                  TrainingTypeService trainingTypeService,AuthService authService, LoggerUtil loggerUtil) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
        this.authService = authService;
        this.loggerUtil = loggerUtil;
    }

    public CredentialsDTO createTraineeProfile(TraineeDTO traineeDTO) {
        loggerUtil.setParams();
        return traineeService.create(traineeDTO);
    }

    public CredentialsDTO createTrainerProfile(TrainerDTO trainerDTO) {
        loggerUtil.setParams();
        return trainerService.create(trainerDTO);
    }

    public Trainee getTraineeByUserName(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        return traineeService.findByUserName(userName);
    }

    public Trainer getTrainerByUserName(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        return trainerService.findByUserName(userName);
    }

    public void createTraining(TrainingDTO trainingDTO, String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        trainingService.createTraining(trainingDTO);
    }

    public void updateTrainee(Trainee trainee, String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        traineeService.update(trainee);
    }

    @Transactional
    public Set<TrainerForSet> updateTrainersForTrainee(String username, String password, Set<TrainersToAdd> trainers) {
        loggerUtil.setParams();
        authenticate(username, password);
        for (TrainersToAdd trainer : trainers) {
            Trainer newTrainer = trainerService.findByUserName(trainer.getUsername());
            traineeService.updateTrainers(username, newTrainer);

        }
        return traineeService.findByUserName(username).getTrainers().stream().map(TrainerForSet::map).collect(Collectors.toSet());
    }

    public void updateTrainer(Trainer trainer, String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        trainerService.update(trainer);
    }

    public void deleteTrainee(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        traineeService.deleteByUserName(userName);
    }

    public void changePassword(String userName, String oldPassword, String newPassword) {
        loggerUtil.setParams();
        authenticate(userName, oldPassword);
        traineeService.changePassword(userName, newPassword);
    }

    public List<Training> getTraineeTrainingsWithCriteria(String traineeUserName, String password, LocalDate from, LocalDate to, String trainerUserName, String trainingType) {
        loggerUtil.setParams();
        authenticate(traineeUserName, password);
        return traineeService.getWithCriteria(traineeUserName, from, to, trainerUserName, trainingType);
    }

    public List<Training> getTrainerTrainingsWithCriteria(String trainerUserName, String password, LocalDate from, LocalDate to, String traineeUserName) {
        loggerUtil.setParams();
        authenticate(trainerUserName, password);
        return trainerService.getWithCriteria(trainerUserName, from, to, traineeUserName);
    }

    public void changeTraineeStatus(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        traineeService.changeStatus(userName);
    }

    public void changeTrainerStatus(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        trainerService.changeStatus(userName);
    }

    public Set<Trainer> getNotAssignedTrainers(String userName, String password) {
        loggerUtil.setParams();
        authenticate(userName, password);
        return traineeService.getUnassignedTrainers(userName);
    }

    public Set<TrainingType> getAllTrainingTypes() {
        loggerUtil.setParams();
        return trainingTypeService.getAll();
    }

    public void authenticate(String username, String password) {
        if (!authService.authenticate(username, password)) {
            throw new UnauthorizedAccessException("Authentication failed");
        }
    }
}
