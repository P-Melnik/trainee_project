package trainee.GymApp.sevicetests;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.impl.TrainingServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class TrainingServiceImplTest {

    private final TrainingType trainingType = new TrainingType(1, "GYM");

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Before
    public void init() {
        TraineeDTO traineeDTO = new TraineeDTO("a", "b", LocalDate.of(1991, 1, 1), "street1");
        TrainerDTO trainerDTO = new TrainerDTO(trainingType, "c", "d");
        traineeService.create(traineeDTO);
        trainerService.create(trainerDTO);
    }

    @Test
    public void createTrainingTest() {
        TrainingDTO trainingDTO = new TrainingDTO(traineeService.findByUserName("a.b"),
                trainerService.findByUserName("c.d"), "weightlifting", trainingType,
                LocalDate.of(2024, 1, 1), 60);
        trainingService.createTraining(trainingDTO);
        List<Training> result = trainingService.findAll();
        System.out.println(Arrays.toString(result.toArray()));
        System.out.println(result.get(0));
        assertNotNull(result);
        assertEquals(result.get(0).getTrainingName(), "weightlifting");
    }

}
