package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.Trainee;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeForSet {

    private String userName;
    private String firstName;
    private String lastName;

    public static TraineeForSet map(Trainee trainer) {
        return new TraineeForSet(trainer.getUser().getUsername(), trainer.getUser().getFirstName(),
                trainer.getUser().getLastName());
    }
}
