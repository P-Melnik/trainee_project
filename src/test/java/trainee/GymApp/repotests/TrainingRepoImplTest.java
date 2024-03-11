package trainee.GymApp.repotests;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.utils.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class TrainingRepoImplTest {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Test
    public void testCreateAndFindById() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        System.out.println(training);
        Training retrievedTraining = trainingRepo.findById(training.getId());
        System.out.println(retrievedTraining);
        Assertions.assertNotNull(retrievedTraining);
        Assertions.assertEquals(training.getId(), retrievedTraining.getId());
    }

    @Test
    public void testUpdate() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        training.setTrainingName("Updated Training");
        trainingRepo.update(training);
        Training updatedTraining = trainingRepo.findById(training.getId());
        Assertions.assertNotNull(updatedTraining);
        Assertions.assertEquals("Updated Training", updatedTraining.getTrainingName());
    }

    private Training createSampleTraining() {
        User user = new User("trainee", "ivanov", UserUtil.generateLogin("trainee", "ivanov"),
                UserUtil.generatePassword(), true);
        User user2 = new User("trainer", "stepanov", UserUtil.generateLogin("trainer", "stepanov"),
                UserUtil.generatePassword(), true);
        Trainee trainee = new Trainee(LocalDate.of(1991, 9, 9),
                "street1", user, new HashSet<>());
        Trainer trainer = new Trainer(trainingTypeRepo.getTrainingType("GYM"), user2);
        return new Training(trainee, trainer, "trainingName", trainingTypeRepo.getTrainingType("GYM"),
                LocalDate.of(2023, 1, 1), 100);
    }
}
