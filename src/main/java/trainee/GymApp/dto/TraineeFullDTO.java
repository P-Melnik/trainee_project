package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeFullDTO {

    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate dateOfBirth;
    private String address;
    private Set<TrainerForSet> trainers;

}
