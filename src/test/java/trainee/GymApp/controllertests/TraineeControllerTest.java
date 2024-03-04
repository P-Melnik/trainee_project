package trainee.GymApp.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.controllers.TraineeController;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TraineeFullDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.User;
import trainee.GymApp.mappers.ControllersMapper;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TraineeControllerTest {

    private static final String PASSWORD = "pppppppppp";
    private static final String USERNAME = "John.Doe";

    @InjectMocks
    private TraineeController traineeController;

    @Mock
    private Facade facade;

    User user = new User("John", "Doe", "John.Doe", "pppppppppp", true);
    Trainee trainee = new Trainee(LocalDate.of(1991, 1, 1), "street 1", user, new HashSet<>());
    TraineeDTO traineeDTO = new TraineeDTO("John", "Doe", true, LocalDate.of(1991, 1, 1), "street 1");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        CredentialsDTO credentialsDTO = new CredentialsDTO(USERNAME, PASSWORD);
        Mockito.doReturn(credentialsDTO).when(this.facade).createTraineeProfile(traineeDTO);
        ResponseEntity<CredentialsDTO> response = traineeController.register(traineeDTO);
        assertEquals(response.getBody(), credentialsDTO);
        verify(facade, times(1)).createTraineeProfile(traineeDTO);
    }

    @Test
    void get() {
        Mockito.doReturn(trainee).when(this.facade).getTraineeByUserName(USERNAME, PASSWORD);
        TraineeFullDTO traineeFullDTO = ControllersMapper.mapTraineeToFullDTO(trainee);
        ResponseEntity<TraineeFullDTO> response = traineeController.getTrainee(USERNAME, PASSWORD);
        assertEquals(traineeFullDTO, response.getBody());
        verify(facade, times(1)).getTraineeByUserName(USERNAME, PASSWORD);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(this.facade).deleteTrainee(USERNAME, PASSWORD);
        ResponseEntity<HttpStatus> response = traineeController.delete(USERNAME, PASSWORD);
        assertEquals(response.getClass(), ResponseEntity.class);
        verify(facade, times(1)).deleteTrainee(USERNAME, PASSWORD);
    }

}
