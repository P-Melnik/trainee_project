package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.TrainingType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerFullDTO {

    private TrainingType trainingType;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Set<TraineeForSet> trainees;

}
