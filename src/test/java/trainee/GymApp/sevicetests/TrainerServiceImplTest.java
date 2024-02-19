package trainee.GymApp.sevicetests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.service.impl.TrainerServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrainerServiceImplTest {

    @Mock
    private TrainerRepo trainerRepo;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrainerById() {
        long trainerId = 1;
        Trainer expectedTrainer = new Trainer();
        when(trainerRepo.findById(trainerId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.getById(trainerId);

        Assertions.assertEquals(expectedTrainer, actualTrainer);
        verify(trainerRepo, times(1)).findById(trainerId);
    }

    @Test
    void testCreateTrainer() {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstName("A");
        trainerDTO.setLastName("B");

        trainerService.create(trainerDTO);

        verify(trainerRepo, times(1)).create(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer() {
        Trainer trainer = new Trainer();

        trainerService.update(trainer);

        verify(trainerRepo, times(1)).update(trainer);
    }

    @Test
    void testFindAll() {
        List<Trainer> expectedTrainers = Arrays.asList(
                new Trainer(),
                new Trainer()
        );
        when(trainerRepo.findAll()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.findAll();

        Assertions.assertEquals(expectedTrainers.size(), actualTrainers.size());
        verify(trainerRepo, times(1)).findAll();
    }
}
