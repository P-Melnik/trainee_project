package trainee.service.summary.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkloadDTO {

    @NotNull
    private String userName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private boolean isActive;
    @NotNull
    private LocalDate trainingDate;
    @NotNull
    private double trainingDuration;
    @NotNull
    private ActionType actionType;

}
