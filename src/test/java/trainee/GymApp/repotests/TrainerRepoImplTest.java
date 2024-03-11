package trainee.GymApp.repotests;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.User;
import trainee.GymApp.utils.UserUtil;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class TrainerRepoImplTest {

    @Autowired
    private TrainerRepo trainerRepo;

    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    @Test
    public void testCreateAndFindById() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        Trainer retrievedTrainer = trainerRepo.findById(trainer.getId());
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals(trainer.getId(), retrievedTrainer.getId());
    }

    @Test
    public void testUpdate() {
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
    public void testFindAll() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        List<Trainer> trainers = trainerRepo.findAll();
        Assertions.assertNotNull(trainers);
        Assertions.assertFalse(trainers.isEmpty());
    }

    @Test
    public void testFindByUserName() {
        Trainer trainer = createSampleTrainer();
        trainerRepo.create(trainer);
        Trainer retrievedTrainer = trainerRepo.findByUserName(trainer.getUser().getUserName()).get();
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals(trainer.getUser().getUserName(), retrievedTrainer.getUser().getUserName());
    }

    private Trainer createSampleTrainer() {
        User user = new User("trainer", "lastname", UserUtil.generateLogin("trainer", "lastname"),
                UserUtil.generatePassword(), true);
        return new Trainer(trainingTypeRepo.getTrainingType("GYM"), user);
    }
}
