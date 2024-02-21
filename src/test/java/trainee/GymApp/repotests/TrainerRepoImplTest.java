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
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, H2TestConfig.class})
@Transactional
@ActiveProfiles("test")
public class TrainerRepoImplTest {

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Test
    void testCreateAndFindById() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        Trainer retrievedTrainer = trainerRepo.findById(trainer.getId());
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals(trainer.getId(), retrievedTrainer.getId());
    }

    @Test
    void testUpdate() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        User user = new User("trainer2", "lastname2", UserUtil.generateLogin("trainer", "lastname"),
                UserUtil.generatePassword(), true);
        trainer.setUser(user);
        trainerRepo.update(trainer);
        Trainer updatedTrainer = trainerRepo.findById(trainer.getId());
        Assertions.assertNotNull(updatedTrainer);
        Assertions.assertEquals(user.getUserName(), updatedTrainer.getUser().getUserName());
    }

    @Test
    void testFindAll() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        List<Trainer> trainers = trainerRepo.findAll();
        Assertions.assertNotNull(trainers);
        Assertions.assertFalse(trainers.isEmpty());
    }

    @Test
    void testFindByUserName() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        Trainer retrievedTrainer = trainerRepo.findByUserName(trainer.getUser().getUserName());
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals(trainer.getUser().getUserName(), retrievedTrainer.getUser().getUserName());
    }

    @Test
    void testGetUnassignedTrainers() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        Set<Trainer> assignedTrainers = new HashSet<>();
        assignedTrainers.add(trainer);
        List<Trainer> unassignedTrainers = trainerRepo.getUnassignedTrainers(assignedTrainers);
        Assertions.assertNotNull(unassignedTrainers);
        Assertions.assertTrue(unassignedTrainers.isEmpty());
    }

    private Trainer createSampleTrainer() {
        User user = new User("trainer", "lastname", UserUtil.generateLogin("trainer", "lastname"),
                UserUtil.generatePassword(), true);
        return new Trainer(trainingTypeRepo.getTrainingType("GYM"), user);
    }
}
