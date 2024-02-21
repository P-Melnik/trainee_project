package trainee.GymApp.repotests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import trainee.GymApp.config.H2TestConfig;
import trainee.GymApp.config.HibernateConfig;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, H2TestConfig.class})
@Transactional
@ActiveProfiles("test")
public class TrainingRepoImplTest {

    @Autowired
    private TrainingRepo trainingRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Test
    void testCreateAndFindById() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        System.out.println(training);
        Training retrievedTraining = trainingRepo.findById(training.getId());
        System.out.println(retrievedTraining);
        Assertions.assertNotNull(retrievedTraining);
        Assertions.assertEquals(training.getId(), retrievedTraining.getId());
    }

    @Test
    void testUpdate() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        training.setTrainingName("Updated Training");
        trainingRepo.update(training);
        Training updatedTraining = trainingRepo.findById(training.getId());
        Assertions.assertNotNull(updatedTraining);
        Assertions.assertEquals("Updated Training", updatedTraining.getTrainingName());
    }

    @Test
    void testFindAll() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        List<Training> trainings = trainingRepo.findAll();
        Assertions.assertNotNull(trainings);
        Assertions.assertFalse(trainings.isEmpty());
    }

    @Test
    void testDelete() {
        Training training = createSampleTraining();
        trainingRepo.create(training);
        boolean deleted = trainingRepo.delete(training.getId());
        Assertions.assertTrue(deleted);
        Training deletedTraining = trainingRepo.findById(training.getId());
        Assertions.assertNull(deletedTraining);
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
