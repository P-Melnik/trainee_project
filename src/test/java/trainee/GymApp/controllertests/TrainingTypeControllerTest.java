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
import trainee.GymApp.controllers.TrainingTypeController;
import trainee.GymApp.entity.TrainingType;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {

    private static final Set<TrainingType> typeSet = new HashSet<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private MockMvc mvc;

    @Mock
    private Facade facade;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(trainingTypeController).build();
    }

    @Test
    public void testGetTrainingTypes() throws Exception {
        typeSet.add(new TrainingType(1, "GYM"));
        typeSet.add(new TrainingType(2, "SWIMMING"));
        Mockito.when(this.facade.getAllTrainingTypes()).thenReturn(typeSet);
        mvc.perform(MockMvcRequestBuilders.get("/training-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].trainingTypeName").value("GYM"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].trainingTypeName").value("SWIMMING"));
    }

    private String asJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

