package trainee.GymApp.mappers;

import trainee.GymApp.dto.ActionType;
import trainee.GymApp.dto.WorkloadDTO;
import trainee.GymApp.dto.TrainingDTO;

public class WorkloadTrainingMapper {

    public static WorkloadDTO map(TrainingDTO trainingDTO) {
        return new WorkloadDTO(trainingDTO.getTrainer().getUser().getUsername(),
                trainingDTO.getTrainer().getUser().getFirstName(), trainingDTO.getTrainer().getUser().getLastName(),
                trainingDTO.getTrainer().getUser().isActive(), trainingDTO.getTrainingDate(),
                trainingDTO.getTrainingDuration());
    }
}
