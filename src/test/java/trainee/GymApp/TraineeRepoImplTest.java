package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.impl.TraineeRepoImpl;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.storage.Storage;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TraineeRepoImplTest {

    @InjectMocks
    private TraineeRepoImpl traineeRepo;
    @Mock
    private Storage storage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Trainee trainee = new Trainee();
        traineeRepo.create(trainee);
        verify(storage, times(1)).save(trainee);
    }

    @Test
    public void testFindById() {
        long id = 1;
        Trainee expectedTrainee = new Trainee();

        when(storage.findById(Trainee.class, id)).thenReturn(expectedTrainee);

        Trainee foundTrainee = traineeRepo.findById(id);

        Assertions.assertEquals(expectedTrainee, foundTrainee);
        verify(storage, times(1)).findById(Trainee.class, id);
    }

    @Test
    public void testFindAllByType() {
        List<Trainee> objList = Arrays.asList(
                new Trainee(),
                new Trainee()
        );
        when(storage.findAll(Trainee.class)).thenReturn(objList);

        List<Trainee> traineeList = traineeRepo.findAll();

        Assertions.assertEquals(objList.size(), traineeList.size());
        verify(storage, times(1)).findAll(Trainee.class);
    }

    @Test
    public void updateTest() {
        Trainee trainee = new Trainee();

        traineeRepo.update(trainee);

        verify(storage, times(1)).update(trainee);
    }

    @Test
    public void deleteTest() {
        long traineeId = 1;

        traineeRepo.delete(traineeId);

        verify(storage, times(1)).delete(Trainee.class, traineeId);
    }

}
