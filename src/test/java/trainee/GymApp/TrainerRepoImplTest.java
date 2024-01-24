package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.impl.TrainerRepoImpl;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.storage.Storage;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrainerRepoImplTest {

    @InjectMocks
    private TrainerRepoImpl trainerRepo;
    @Mock
    private Storage storage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Trainer trainer = new Trainer();

        trainerRepo.create(trainer);

        verify(storage, times(1)).save(trainer);
    }

    @Test
    void testFindById() {
        long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();
        when(storage.findById("trainer", trainerId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerRepo.findById(trainerId);

        Assertions.assertEquals(expectedTrainer, actualTrainer);
        verify(storage, times(1)).findById("trainer", trainerId);
    }

    @Test
    void testFindAllByType() {
        List<Object> objList = Arrays.asList(
                new Trainer(),
                new Trainer()
        );

        when(storage.findAllByType("trainer")).thenReturn(objList);

        List<Trainer> trainerList = trainerRepo.findAllByType();

        Assertions.assertEquals(objList.size(), trainerList.size());
        verify(storage, times(1)).findAllByType("trainer");
    }

    @Test
    void testUpdateTrainer() {
        Trainer trainer = new Trainer();

        trainerRepo.updateTrainer(trainer);

        verify(storage, times(1)).update(trainer);
    }
}
