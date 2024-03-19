package trainee.GymApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingsForResponse {

    private String trainingName;
    private LocalDate trainingDate;
    private String trainingType;
    private Number duration;
    private String name;

    public static TrainingsForResponse mapForTrainee(Training training) {
        return new TrainingsForResponse(training.getTrainingName(), training.getTrainingDate(), training.getTrainingType().getTrainingTypeName(),
                training.getTrainingDuration(), training.getTrainer().getUser().getUsername());
    }

    public static TrainingsForResponse mapForTrainer(Training training) {
        return new TrainingsForResponse(training.getTrainingName(), training.getTrainingDate(), training.getTrainingType().getTrainingTypeName(),
                training.getTrainingDuration(), training.getTrainee().getUser().getUsername());
    }
}
