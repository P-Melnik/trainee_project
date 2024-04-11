package trainee.GymApp.mappers;

import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TraineeForSet;
import trainee.GymApp.dto.TraineeFullDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainerForSet;
import trainee.GymApp.dto.TrainerFullDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;

import java.util.stream.Collectors;

public class ControllersMapper {

    public static TraineeFullDTO mapTraineeToFullDTO(Trainee trainee) {
        return new TraineeFullDTO(trainee.getUser().getFirstName(), trainee.getUser().getLastName(),
                trainee.getUser().isActive(), trainee.getDateOfBirth(), trainee.getAddress(),
                trainee.getTrainers().stream().map(TrainerForSet::map).collect(Collectors.toSet()));
    }

    public static TrainerFullDTO mapTrainerToFullDTO(Trainer trainer) {
        return new TrainerFullDTO(trainer.getTrainingType(), trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(), trainer.getUser().isActive(),
                trainer.getTrainees().stream().map(TraineeForSet::map).collect(Collectors.toSet()));
    }

    public static Trainee processTraineeUpdate(Trainee trainee, TraineeDTO traineeDTO) {
        trainee.getUser().setFirstName(traineeDTO.getFirstName());
        trainee.getUser().setLastName(traineeDTO.getLastName());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        trainee.setAddress(traineeDTO.getAddress());
        return trainee;
    }

    public static Trainer processTrainerUpdate(Trainer trainer, TrainerDTO trainerDTO) {
        trainer.getUser().setFirstName(trainerDTO.getFirstName());
        trainer.getUser().setLastName(trainerDTO.getLastName());
        trainer.setTrainingType(trainerDTO.getTrainingType());
        return trainer;
    }

}
