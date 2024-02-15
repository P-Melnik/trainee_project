package trainee.GymApp.repotests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import trainee.GymApp.config.H2TestConfig;
import trainee.GymApp.config.HibernateConfig;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, H2TestConfig.class})
@Transactional
@ActiveProfiles("test")
public class TraineeRepoImplTest {

    @Autowired
    private TraineeRepo traineeRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createTest() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);
        Trainee retrievedTrainee = traineeRepo.findById(trainee.getId());
        Assertions.assertNotNull(retrievedTrainee);
        Assertions.assertEquals(trainee.getId(), retrievedTrainee.getId());
    }

    @Test
    public void testFindByUserName() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);

        Trainee foundTrainee = traineeRepo.findByUserName(trainee.getUser().getUserName());
        System.out.println(foundTrainee);
        Assertions.assertNotNull(foundTrainee);
        Assertions.assertEquals(trainee.getId(), foundTrainee.getId());
    }

    @Test
    public void testUpdateTrainee() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);

        trainee.setAddress("Updated Address");
        traineeRepo.update(trainee);

        Trainee updatedTrainee = traineeRepo.findById(trainee.getId());
        Assertions.assertNotNull(updatedTrainee);
        Assertions.assertEquals("Updated Address", updatedTrainee.getAddress());
    }

    @Test
    public void testDeleteTrainee() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);

        Assertions.assertTrue(traineeRepo.delete(trainee.getId()));

        Trainee deletedTrainee = traineeRepo.findById(trainee.getId());
        Assertions.assertNull(deletedTrainee);
    }

    @Test
    public void testFindAll() {
        Trainee trainee1 = createSampleTrainee();
        Trainee trainee2 = createSampleTrainee();

        traineeRepo.create(trainee1);
        traineeRepo.create(trainee2);

        List<Trainee> trainees = traineeRepo.findAll();
        Assertions.assertNotNull(trainees);
        Assertions.assertEquals(2, trainees.size());
    }

    @Transactional
    @Test
    public void testDeleteByUserName() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);
        Assertions.assertTrue(traineeRepo.deleteByUserName(trainee.getUser().getUserName()));

    }

    @Test
    void testGetWithCriteria() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);

        List<Training> result = traineeRepo.getWithCriteria(
                trainee.getUser().getUserName(),
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 12, 31),
                "TrainerUsername",
                "TrainingName"
        );

        Assertions.assertNotNull(result);
    }

    private Trainee createSampleTrainee() {
        User user = new User("andrew", "ivanov", UserUtil.generateLogin("andrew", "ivanov"),
                UserUtil.generatePassword(), true);
        return new Trainee(LocalDate.of(1991, 9, 9),
                "street1", user, new HashSet<>());
    }
}
