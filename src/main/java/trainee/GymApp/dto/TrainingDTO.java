package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {

    private Trainee trainee;
    private Trainer trainer;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Number trainingDuration;

}
