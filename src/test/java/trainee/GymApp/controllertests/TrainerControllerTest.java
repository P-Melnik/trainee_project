package trainee.GymApp.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.controllers.TrainerController;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTest {

    private static final String USERNAME = "Ivan.Petrov";
    private static final String PASSWORD = "pppppppppp";
    private static final TrainingType testTrainingType = new TrainingType(1, "GYM");
    private static final Trainer testTrainer = new Trainer(testTrainingType, new User("Ivan", "Petrov", USERNAME, PASSWORD, true));
    private static final TrainerDTO testTrainerDTO = new TrainerDTO(testTrainingType, "Ivan", "Petrov", true);
    private static final TrainerDTO testUpdateTrainerDTO = new TrainerDTO(testTrainingType, "Ivan", "Ivanov", true);
    private static final CredentialsDTO testCredentials = new CredentialsDTO(USERNAME, PASSWORD);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private MockMvc mvc;

    @Mock
    private Facade facade;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(trainerController).build();
    }

    @Test
    public void testGetTrainer() throws Exception {
        Mockito.when(this.facade.getTrainerByUserName(USERNAME)).thenReturn(testTrainer);
        mvc.perform(MockMvcRequestBuilders.get("/trainer/{username}", USERNAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("password", PASSWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Petrov"));
    }

    @Test
    public void testRegisterTrainer() throws Exception {
        Mockito.when(this.facade.createTrainerProfile(testTrainerDTO)).thenReturn(testCredentials);
        mvc.perform(MockMvcRequestBuilders.post("/trainer")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .content(asJsonString(testTrainerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(USERNAME))
                .andExpect(jsonPath("$.password").value(PASSWORD));
    }

    @Test
    public void testUpdateTrainer() throws Exception {
        Mockito.when(this.facade.getTrainerByUserName(USERNAME)).thenReturn(testTrainer);
        mvc.perform(MockMvcRequestBuilders.put("/trainer/{username}", USERNAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .header("password", PASSWORD)
                        .content(asJsonString(testUpdateTrainerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Ivanov"));
    }

    private String asJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

