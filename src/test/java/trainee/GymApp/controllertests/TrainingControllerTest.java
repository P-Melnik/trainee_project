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
import trainee.GymApp.controllers.TrainingController;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {

    private static final String PASSWORD = "pppppppppp";
    private static final String USERNAME = "John.Doe";

    @InjectMocks
    private TrainingController trainingController;

    @Mock
    private Facade facade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Trainee trainee = new Trainee();
    Trainer trainer = new Trainer();
    TrainingDTO trainingDTO = new TrainingDTO(trainee, trainer, "training",
            new TrainingType(1, "GYM"), LocalDate.of(2024, 1, 1),
            60);

    @Test
    void add() {
        Mockito.doNothing().when(this.facade).createTraining(trainingDTO, USERNAME, PASSWORD);
        ResponseEntity<HttpStatus> response = trainingController.add(USERNAME, PASSWORD, trainingDTO);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(facade, times(1)).createTraining(trainingDTO, USERNAME, PASSWORD);
    }
}
