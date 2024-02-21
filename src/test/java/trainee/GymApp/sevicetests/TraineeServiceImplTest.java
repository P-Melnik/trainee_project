package trainee.GymApp.sevicetests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.service.impl.TraineeServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TraineeServiceImplTest {

    @Mock
    private TraineeRepo traineeRepo;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTraineeById() {
        long traineeId = 1L;
        Trainee expectedTrainee = new Trainee();
        when(traineeRepo.findById(traineeId)).thenReturn(expectedTrainee);

        Trainee actualTrainee = traineeService.getById(traineeId);

        Assertions.assertEquals(expectedTrainee, actualTrainee);
        verify(traineeRepo, times(1)).findById(traineeId);
    }

    @Test
    void testCreateTrainee() {
        TraineeDTO traineeDTO = new TraineeDTO("a", "b", true, LocalDate.of(1991, 10, 10), "street1");
        traineeDTO.setFirstName("A");
        traineeDTO.setLastName("B");
        traineeService.create(traineeDTO);

        verify(traineeRepo, times(1)).create(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee() {
        Trainee trainee = new Trainee();

        traineeService.update(trainee);

        verify(traineeRepo, times(1)).update(trainee);
    }

    @Test
    void testDeleteTraineeById() {
        long traineeId = 1;

        traineeService.delete(traineeId);

        verify(traineeRepo, times(1)).delete(traineeId);
    }

    @Test
    void testFindAll() {
        List<Trainee> expectedTrainees = Arrays.asList(
                new Trainee(),
                new Trainee()
        );
        when(traineeRepo.findAll()).thenReturn(expectedTrainees);

        List<Trainee> actualTrainees = traineeService.findAll();

        Assertions.assertEquals(expectedTrainees.size(), actualTrainees.size());
        verify(traineeRepo, times(1)).findAll();
    }
}
