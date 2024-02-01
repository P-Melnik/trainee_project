package trainee.GymApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Training implements Entity {

    private long id;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private long trainingTypeId;
    private LocalDate trainingDate;
    private int trainingDuration;

}
