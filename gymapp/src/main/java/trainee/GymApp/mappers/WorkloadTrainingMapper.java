package trainee.GymApp.mappers;

import trainee.GymApp.dto.ActionType;
import trainee.GymApp.dto.RequestWorkloadDTO;
import trainee.GymApp.dto.TrainingDTO;

public class WorkloadTrainingMapper {

    public static RequestWorkloadDTO map(TrainingDTO trainingDTO, ActionType actionType) {
        return new RequestWorkloadDTO(trainingDTO.getTrainer().getUser().getUsername(),
                trainingDTO.getTrainer().getUser().getFirstName(), trainingDTO.getTrainer().getUser().getLastName(),
                trainingDTO.getTrainer().getUser().isActive(), trainingDTO.getTrainingDate(),
                trainingDTO.getTrainingDuration(), actionType);
    }
}
