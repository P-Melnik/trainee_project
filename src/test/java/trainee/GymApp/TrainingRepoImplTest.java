package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.impl.TrainingRepoImpl;
import trainee.GymApp.entity.Training;
import trainee.GymApp.storage.Storage;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrainingRepoImplTest {
    @InjectMocks
    private TrainingRepoImpl trainingRepo;
    @Mock
    private Storage storage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Training training = new Training(/* initialize training */);

        trainingRepo.create(training);

        verify(storage, times(1)).save(training);
    }

    @Test
    void testFindById() {
        long trainingId = 1;
        Training expectedTraining = new Training();
        when(storage.findById(Training.class, trainingId)).thenReturn(expectedTraining);

        Training actualTraining = trainingRepo.findById(trainingId);

        Assertions.assertEquals(expectedTraining, actualTraining);
        verify(storage, times(1)).findById(Training.class, trainingId);
    }

    @Test
    void testFindAllByType() {
        List<Training> objList = Arrays.asList(
                new Training(),
                new Training()
        );
        when(storage.findAll(Training.class)).thenReturn(objList);

        List<Training> trainingList = trainingRepo.findAll();

        Assertions.assertEquals(objList.size(), trainingList.size());
        verify(storage, times(1)).findAll(Training.class);
    }
}
