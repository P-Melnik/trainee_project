package trainee.GymApp.repotests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.User;
import trainee.GymApp.utils.UserUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
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

        Trainee foundTrainee = traineeRepo.findByUserName(trainee.getUser().getUsername()).get();
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
    public void testFindAll() {
        Trainee trainee1 = createSampleTrainee();
        Trainee trainee2 = createSampleTrainee();

        traineeRepo.create(trainee1);
        traineeRepo.create(trainee2);

        List<Trainee> trainees = traineeRepo.findAll();
        Assertions.assertNotNull(trainees);
        Assertions.assertEquals(2, trainees.size());
    }

    @Test
    public void testDeleteByUserName() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);
        Assertions.assertTrue(traineeRepo.deleteByUserName(trainee.getUser().getUsername()));

    }

    @Test
    public void testGetWithCriteria() {
        Trainee trainee = createSampleTrainee();
        traineeRepo.create(trainee);

        List<Training> result = traineeRepo.getWithCriteria(
                trainee.getUser().getUsername(),
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 12, 31),
                "TrainerUsername",
                "TrainingName"
        );

        Assertions.assertNotNull(result);
    }

    private Trainee createSampleTrainee() {
        User user = new User("andrew", "ivanov", UserUtil.generateLogin("andrew", "ivanov"),
                UserUtil.generatePassword());
        return new Trainee(LocalDate.of(1991, 9, 9),
                "street1", user, new HashSet<>());
    }
}
