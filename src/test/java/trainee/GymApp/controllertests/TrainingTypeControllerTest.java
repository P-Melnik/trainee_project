package trainee.GymApp.controllertests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.controllers.TrainingTypeController;
import trainee.GymApp.entity.TrainingType;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @Mock
    private Facade facade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Set<TrainingType> set = new HashSet<>();
    TrainingType trainingType1 = new TrainingType(1, "GYM");
    TrainingType trainingType2 = new TrainingType(2, "RUNNING");

    @Test
    void get() {
        set.add(trainingType1);
        set.add(trainingType2);
        Mockito.doReturn(set).when(this.facade).getAllTrainingTypes();
        Set<TrainingType> responseSet = trainingTypeController.get();
        assertTrue(responseSet.containsAll(set));
        verify(facade, times(1)).getAllTrainingTypes();
    }
}
