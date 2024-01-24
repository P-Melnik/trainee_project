package trainee.GymApp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TraineeDTO {

    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate dateOfBirth;
    private String address;


}
