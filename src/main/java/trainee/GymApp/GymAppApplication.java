package trainee.GymApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;

import java.time.LocalDate;


@SpringBootApplication
public class GymAppApplication {


    public static void main(String[] args) {

        SpringApplication.run(GymAppApplication.class, args);

    }

    // some implementation -> facade methods
    @Bean
    public CommandLineRunner demo(Facade facade) {
        return args -> {
            // Create Trainee
            TraineeDTO traineeDTO = new TraineeDTO("John", "Doe", true,
                    LocalDate.of(1991, 10, 10),
                    "123321");
            facade.createTrainee(traineeDTO);

            // Create Trainer
            TrainerDTO trainerDTO = new TrainerDTO(1, "Anton", "Ivanov", true);
            facade.createTrainer(trainerDTO);

            // Create Training
            TrainingDTO trainingDTO = new TrainingDTO(1, 2, "swimming", 1,
                    LocalDate.of(2023, 1, 1),
                    60);
            facade.createTraining(trainingDTO);

            // Get Trainee by ID
            Trainee retrievedTrainee = facade.getTrainee(1);
            System.out.println("Retrieved Trainee: " + retrievedTrainee);

            // Get Trainer by ID
            Trainer retrievedTrainer = facade.getTrainer(1);
            System.out.println("Retrieved Trainer: " + retrievedTrainer);

            // Get Training by ID
            Training retrievedTraining = facade.getTraining(1);
            System.out.println("Retrieved Training: " + retrievedTraining);

            // Find all Trainees
            System.out.println("All Trainees: " + facade.findAllTrainees());

            // Find all Trainers
            System.out.println("All Trainers: " + facade.findAllTrainers());

            // Find all Trainings
            System.out.println("All Trainings: " + facade.findAllTrainings());
        };
    }
}
