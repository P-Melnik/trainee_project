package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {

    @NotNull
    private Trainee trainee;
    @NotNull
    private Trainer trainer;
    @NotNull
    private String trainingName;
    @NotNull
    private TrainingType trainingType;
    @NotNull
    private LocalDate trainingDate;
    @NotNull
    private double trainingDuration;

}
