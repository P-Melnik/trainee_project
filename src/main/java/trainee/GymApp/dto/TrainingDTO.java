package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {

    private long traineeId;
    private long trainerId;
    private String trainingName;
    private long trainingType;
    private LocalDate trainingDate;
    private int trainingDuration;

}
