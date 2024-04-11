package trainee.GymApp.sevicetests;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.service.impl.TraineeServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class TraineeServiceImplTest {

    @Autowired
    private TraineeServiceImpl traineeService;

    @Test
    public void createTraineeAndFindByUsernameTest() {
        TraineeDTO traineeDTO = new TraineeDTO("a", "b", LocalDate.of(1991, 10, 10), "street1");
        CredentialsDTO credentialsDTO = traineeService.create(traineeDTO);
        Trainee trainee = traineeService.findByUserName("a.b");
        assertEquals(credentialsDTO.getUserName(), trainee.getUser().getUsername());
    }

    @Test
    public void updateAndDeleteTraineeTest() {
        TraineeDTO traineeDTO = new TraineeDTO("b", "c", LocalDate.of(1991, 10, 10), "street1");
        traineeService.create(traineeDTO);
        Trainee trainee = traineeService.findByUserName("b.c");
        trainee.setAddress("updated");
        Trainee updatedTrainee = traineeService.update(trainee);
        assertEquals(updatedTrainee.getAddress(), "updated");
        traineeService.deleteByUserName("b.c");
        assertThrows(EmptyResultDataAccessException.class, () -> traineeService.findByUserName("b.c"));
    }

}
