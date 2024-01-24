package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class FacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private Facade facade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTraineeById() {
        long traineeId = 1;
        Trainee expectedTrainee = new Trainee();
        when(traineeService.getTraineeById(traineeId)).thenReturn(expectedTrainee);

        Trainee actualTrainee = facade.getTraineeById(traineeId);

        Assertions.assertEquals(expectedTrainee, actualTrainee);
        verify(traineeService, times(1)).getTraineeById(traineeId);
    }

    @Test
    void testGetTrainerById() {
        long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();
        when(trainerService.getTrainerById(trainerId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = facade.getTrainerById(trainerId);

        Assertions.assertEquals(expectedTrainer, actualTrainer);
        verify(trainerService, times(1)).getTrainerById(trainerId);
    }

    @Test
    void testGetTrainingById() {
        long trainingId = 1L;
        Training expectedTraining = new Training();
        when(trainingService.getTrainingById(trainingId)).thenReturn(expectedTraining);

        Training actualTraining = facade.getTrainingById(trainingId);

        Assertions.assertEquals(expectedTraining, actualTraining);
        verify(trainingService, times(1)).getTrainingById(trainingId);
    }

    @Test
    void testFindAllTrainees() {
        List<Trainee> expectedTrainees = Arrays.asList(
                new Trainee(),
                new Trainee()
        );
        when(traineeService.findAll()).thenReturn(expectedTrainees);

        List<Trainee> actualTrainees = facade.findAllTrainees();

        Assertions.assertEquals(expectedTrainees.size(), actualTrainees.size());
        verify(traineeService, times(1)).findAll();
    }

}
