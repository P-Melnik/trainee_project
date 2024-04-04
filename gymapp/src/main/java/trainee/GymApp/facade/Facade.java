package trainee.GymApp.facade;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.client.WorkloadSummaryClient;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.LoginResponse;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainerForSet;
import trainee.GymApp.dto.TrainersToAdd;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.metrics.CountProcessedTrainingsMetric;
import trainee.GymApp.metrics.CountRegisterMetric;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;
import trainee.GymApp.service.TrainingTypeService;
import trainee.GymApp.service.authentication.AuthService;
import trainee.GymApp.service.authentication.UnauthorizedAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final AuthService authService;
    private final CountRegisterMetric countRegisterMetric;
    private final CountProcessedTrainingsMetric countProcessedTrainingsMetric;

    @Autowired
    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService,
                  TrainingTypeService trainingTypeService,AuthService authService, CountRegisterMetric countRegisterMetric,
                  CountProcessedTrainingsMetric countProcessedTrainingsMetric) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
        this.authService = authService;
        this.countRegisterMetric = countRegisterMetric;
        this.countProcessedTrainingsMetric = countProcessedTrainingsMetric;
    }

    public CredentialsDTO createTraineeProfile(TraineeDTO traineeDTO) {
        log.info("Performing user registration as trainee within the transaction with ID: {}", MDC.get("transactionId"));
        countRegisterMetric.incrementCounter();
        return traineeService.create(traineeDTO);
    }

    public CredentialsDTO createTrainerProfile(TrainerDTO trainerDTO) {
        log.info("Performing user registration as trainer within the transaction with ID: {}", MDC.get("transactionId"));
        countRegisterMetric.incrementCounter();
        return trainerService.create(trainerDTO);
    }

    public Trainee getTraineeByUserName(String userName) {
        log.info("Fetching trainee within the transaction with ID: {}", MDC.get("transactionId"));
        return traineeService.findByUserName(userName);
    }

    public Trainer getTrainerByUserName(String userName) {
        log.info("Fetching trainer within the transaction with ID: {}", MDC.get("transactionId"));
        return trainerService.findByUserName(userName);
    }

    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Processing new training within the transaction with ID: {}", MDC.get("transactionId"));
        countProcessedTrainingsMetric.incrementCounter();
        trainingService.createTraining(trainingDTO);
    }

    public void updateTrainee(Trainee trainee) {
        log.info("Updating trainee profile within the transaction with ID: {}", MDC.get("transactionId"));
        traineeService.update(trainee);
    }

    @Transactional
    public Set<TrainerForSet> updateTrainersForTrainee(String username, Set<TrainersToAdd> trainers) {
        log.info("Updating trainers list for trainee within the transaction with ID: {}", MDC.get("transactionId"));
        for (TrainersToAdd trainer : trainers) {
            Trainer newTrainer = trainerService.findByUserName(trainer.getUsername());
            traineeService.updateTrainers(username, newTrainer);
        }
        return traineeService.findByUserName(username).getTrainers().stream().map(TrainerForSet::map).collect(Collectors.toSet());
    }

    public void updateTrainer(Trainer trainer) {
        log.info("Updating trainer profile within the transaction with ID: {}", MDC.get("transactionId"));
        trainerService.update(trainer);
    }

    public void deleteTrainee(String userName) {
        log.info("Deleting trainee within the transaction with ID: {}", MDC.get("transactionId"));
        traineeService.deleteByUserName(userName);
    }

    public void changePassword(String userName, String oldPassword, String newPassword) {
        log.info("Change password request within the transaction with ID: {}", MDC.get("transactionId"));
        traineeService.changePassword(userName, newPassword);
    }

    public List<Training> getTraineeTrainingsWithCriteria(String traineeUserName, LocalDate from, LocalDate to, String trainerUserName, String trainingType) {
        log.info("Fetching trainings within the transaction with ID: {}", MDC.get("transactionId"));
        return traineeService.getWithCriteria(traineeUserName, from, to, trainerUserName, trainingType);
    }

    public List<Training> getTrainerTrainingsWithCriteria(String trainerUserName, LocalDate from, LocalDate to, String traineeUserName) {
        log.info("Fetching trainings within the transaction with ID: {}", MDC.get("transactionId"));
        return trainerService.getWithCriteria(trainerUserName, from, to, traineeUserName);
    }

    public void changeTraineeStatus(String userName) {
        log.info("Activation/De-Activation trainee within the transaction with ID: {}", MDC.get("transactionId"));
        traineeService.changeStatus(userName);
    }

    public void changeTrainerStatus(String userName) {
        log.info("Activation/De-Activation trainer within the transaction with ID: {}", MDC.get("transactionId"));
        trainerService.changeStatus(userName);
    }

    public Set<Trainer> getNotAssignedTrainers(String userName) {
        log.info("Fetching not-assigned trainers within the transaction with ID: {}", MDC.get("transactionId"));
        return traineeService.getUnassignedTrainers(userName);
    }

    public Set<TrainingType> getAllTrainingTypes() {
        log.info("Fetching all training-types within the transaction with ID: {}", MDC.get("transactionId"));
        return trainingTypeService.getAll();
    }

    public Training getTrainingById(long id) {
        return trainingService.getById(id);
    }

    public void deleteTraining(long id) {
        trainingService.delete(id);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<LoginResponse> response = authService.login(loginRequest);
        if (response.isEmpty()) {
            throw new UnauthorizedAccessException(authService.handleLoginFailure(loginRequest));
        }
        return response.get();
    }

}
