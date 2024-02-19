package trainee.GymApp.sevicetests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.service.impl.TrainingServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrainingServiceImplTest {

    @Mock
    private TrainingRepo trainingRepo;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrainingById() {
        long trainingId = 1L;
        Training expectedTraining = new Training();
        when(trainingRepo.findById(trainingId)).thenReturn(expectedTraining);

        Training actualTraining = trainingService.getById(trainingId);

        Assertions.assertEquals(expectedTraining, actualTraining);
        verify(trainingRepo, times(1)).findById(trainingId);
    }

    @Test
    void testCreateTraining() {
        TrainingDTO trainingDTO = new TrainingDTO();

        trainingService.create(trainingDTO);

        verify(trainingRepo, times(1)).create(any(Training.class));
    }

    @Test
    void testFindAll() {
        List<Training> expectedTrainings = Arrays.asList(
                new Training(),
                new Training()
        );
        when(trainingRepo.findAll()).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.findAll();

        Assertions.assertEquals(expectedTrainings.size(), actualTrainings.size());
        verify(trainingRepo, times(1)).findAll();
    }
}
