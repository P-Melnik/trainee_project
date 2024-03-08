package trainee.GymApp.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import trainee.GymApp.Facade;
import trainee.GymApp.controllers.TrainingController;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {

    private static final String USERNAME = "Ivan.Petrov";
    private static final String PASSWORD = "pppppppppp";
    private static final Trainee testTrainee = new Trainee(LocalDate.of(1991, 1, 1),
            "street1", new User("John", "Doe", USERNAME, PASSWORD, true), new HashSet<>());
    private static final TrainingType testTrainingType = new TrainingType(1, "GYM");
    private static final Trainer testTrainer = new Trainer(testTrainingType, new User("Ivan", "Petrov", USERNAME, PASSWORD, true));
    private static final TrainingDTO testTrainingDTO = new TrainingDTO(testTrainee, testTrainer, "weightlifting", testTrainingType, LocalDate.of(2024, 2, 2), 60);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private MockMvc mvc;

    @Mock
    private Facade facade;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    public void testAddTraining() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/trainings")
                .accept(MediaType.APPLICATION_JSON)
                .header("username", USERNAME)
                .header("password", PASSWORD)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .content(asJsonString(testTrainingDTO)))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
