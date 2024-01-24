package trainee.GymApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {

    private long id;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private long trainingTypeId;
    private LocalDate trainingDate;
    private int trainingDuration;

}
