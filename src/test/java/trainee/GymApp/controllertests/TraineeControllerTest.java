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
import trainee.GymApp.controllers.TraineeController;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TraineeControllerTest {

    private static final String USERNAME = "John.Doe";
    private static final String PASSWORD = "pppppppppp";
    private static final Trainee testTrainee = new Trainee(LocalDate.of(1991, 1, 1),
            "street1", new User("John", "Doe", USERNAME, PASSWORD, true), new HashSet<>());
    private static final TraineeDTO testTraineeDto = new TraineeDTO("John", "Doe", true, LocalDate.of(1991, 1, 1), "street1");
    private static final TraineeDTO testUpdateTraineeDto = new TraineeDTO("John", "Doe", true, LocalDate.of(1991, 1, 1), "street2");
    private static final CredentialsDTO testCredentials = new CredentialsDTO(USERNAME, PASSWORD);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private MockMvc mvc;

    @Mock
    private Facade facade;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(traineeController).build();
    }

    @Test
    public void testGetTrainee() throws Exception {
        Mockito.when(this.facade.getTraineeByUserName(USERNAME)).thenReturn(testTrainee);
        mvc.perform(MockMvcRequestBuilders.get("/trainee/{username}", USERNAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("password", PASSWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.address").value("street1"));
    }

    @Test
    public void testRegisterTrainee() throws Exception {
        Mockito.when(this.facade.createTraineeProfile(testTraineeDto)).thenReturn(testCredentials);
        mvc.perform(MockMvcRequestBuilders.post("/trainee")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .content(asJsonString(testTraineeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(USERNAME))
                .andExpect(jsonPath("$.password").value(PASSWORD));
    }

    @Test
    public void updateTraineeTest() throws Exception {
        Mockito.when(this.facade.getTraineeByUserName(USERNAME)).thenReturn(testTrainee);
        mvc.perform(MockMvcRequestBuilders.put("/trainee/{username}", USERNAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .header("password", PASSWORD)
                        .content(asJsonString(testUpdateTraineeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("street2"));
    }

    private String asJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

