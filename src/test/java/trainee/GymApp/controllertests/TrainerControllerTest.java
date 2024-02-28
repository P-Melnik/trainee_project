package trainee.GymApp.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import trainee.GymApp.Facade;
import trainee.GymApp.controllers.TrainerController;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainerFullDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;
import trainee.GymApp.mappers.ControllersMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTest {

    private static final String PASSWORD = "pppppppppp";
    private static final String USERNAME = "John.Doe";

    @InjectMocks
    private TrainerController trainerController;

    @Mock
    private Facade facade;

    TrainingType trainingType = new TrainingType(1, "GYM");
    User user = new User("John", "Doe", "John.Doe", "pppppppppp", true);
    Trainer trainer = new Trainer(trainingType, user);
    TrainerDTO trainerDTO = new TrainerDTO(trainingType, "John", "Doe", true);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        CredentialsDTO credentialsDTO = new CredentialsDTO(USERNAME, PASSWORD);
        Mockito.doReturn(credentialsDTO).when(this.facade).createTrainerProfile(trainerDTO);
        ResponseEntity<CredentialsDTO> response = trainerController.register(trainerDTO);
        assertEquals(response.getBody(), credentialsDTO);
        verify(facade, times(1)).createTrainerProfile(trainerDTO);
    }

    @Test
    void get() {
        Mockito.doReturn(trainer).when(this.facade).getTrainerByUserName(USERNAME, PASSWORD);
        TrainerFullDTO trainerFullDTO = ControllersMapper.mapTrainerToFullDTO(trainer);
        ResponseEntity<TrainerFullDTO> response = trainerController.getTrainer(USERNAME, PASSWORD);
        assertEquals(trainerFullDTO, response.getBody());
        verify(facade, times(1)).getTrainerByUserName(USERNAME, PASSWORD);
    }
}
