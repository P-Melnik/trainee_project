package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.TrainingType;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {

    @NotNull
    private TrainingType trainingType;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

}
