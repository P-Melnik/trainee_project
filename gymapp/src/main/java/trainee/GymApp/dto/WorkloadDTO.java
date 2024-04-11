package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkloadDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private double trainingDuration;

}
