package trainee.GymApp.sevicetests;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.impl.TrainerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class TrainerServiceImplTest {

    private final TrainingType trainingType = new TrainingType(1, "GYM");

    @Autowired
    private TrainerServiceImpl trainerService;

    @Test
    public void createTraineeAndFindByUsernameTest() {
        TrainerDTO trainerDTO = new TrainerDTO(trainingType, "a", "b");
        CredentialsDTO credentials = trainerService.create(trainerDTO);
        Trainer trainer = trainerService.findByUserName("a.b");
        assertEquals(credentials.getUserName(), trainer.getUser().getUsername());
    }

    @Test
    public void updateTraineeTest() {
        TrainerDTO trainerDTO = new TrainerDTO(trainingType, "b", "c");
        trainerService.create(trainerDTO);
        Trainer trainer = trainerService.findByUserName("b.c");
        trainer.getUser().setFirstName("upd");
        Trainer updatedTrainer = trainerService.update(trainer);
        assertEquals(updatedTrainer.getUser().getFirstName(), "upd");
    }
}
