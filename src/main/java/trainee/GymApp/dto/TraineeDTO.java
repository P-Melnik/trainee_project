package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

}
