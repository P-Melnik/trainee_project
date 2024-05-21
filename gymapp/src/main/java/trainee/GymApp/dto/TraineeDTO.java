package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

}
