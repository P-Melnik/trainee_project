package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.Trainer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerForSet {

    private String userName;
    private String firstName;
    private String lastName;
    private long specializationId;

    public static TrainerForSet map(Trainer trainer) {
        return new TrainerForSet(trainer.getUser().getUsername(), trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(), trainer.getTrainingType().getId());
    }
}
