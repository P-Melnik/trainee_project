package trainee.GymApp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDTO {

    private long traineeId;
    private long trainerId;
    private String trainingName;
    private long trainingType;
    private LocalDate trainingDate;
    private int trainingDuration;


}
