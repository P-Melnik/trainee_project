package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.TrainingType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {

    private TrainingType trainingType;
    private String firstName;
    private String lastName;
    private boolean isActive;

}
