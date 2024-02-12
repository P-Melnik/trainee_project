package trainee.GymApp;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import trainee.GymApp.config.HibernateConfig;
import trainee.GymApp.dao.TrainingTypeRepo;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;
import trainee.GymApp.service.TrainingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@ComponentScan
@Transactional
@EnableTransactionManagement
public class GymAppApplication {

    // Simple logic to test methods and db state
//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext applicationContext =
//                new AnnotationConfigApplicationContext(HibernateConfig.class);
//
//        TraineeService traineeService = applicationContext.getBean(TraineeService.class);
//        TrainerService trainerService = applicationContext.getBean(TrainerService.class);
//        TrainingService trainingService = applicationContext.getBean(TrainingService.class);
//        TrainingTypeRepo trainingTypeRepo = applicationContext.getBean(TrainingTypeRepo.class);
//
//        waitForInput();
//
//        TraineeDTO traineeDTO = new TraineeDTO("Pavel", "Melnik", true, LocalDate.of(1991, 9, 13), "street");
//
//        TrainingType trainingType = trainingTypeRepo.getTrainingType("GYM");
//
//        traineeService.create(traineeDTO);
//        traineeService.create(traineeDTO);
//        trainerService.create(new TrainerDTO(trainingType, "Ivan", "Petrov", true));
//        trainerService.create(new TrainerDTO(trainingType, "Petr", "Ivanov", true));
//
//        List<Trainee> list = traineeService.findAll();
//        System.out.println(list);
//
//        waitForInput();
//
//        traineeService.deleteByUserName("Pavel.Melnik1");
//
//        waitForInput();
//
//        traineeService.changeStatus("Pavel.Melnik");
//
//        waitForInput();
//
//        Trainer trainer = trainerService.findByUserName("Ivan.Petrov");
//        System.out.println(trainer);
//        traineeService.updateTrainers("Pavel.Melnik",trainer);
//
//        waitForInput();
//
//        List<Trainer> notAssigned = traineeService.notAssignedTrainers("Pavel.Melnik");
//        System.out.println(notAssigned);
//
//        waitForInput();
//        Number integerValue = 100;
//        TrainingDTO trainingDTO = new TrainingDTO(traineeService.findByUserName("Pavel.Melnik"), trainerService.findByUserName("Ivan.Petrov"), "hard training", trainer.getTrainingType()
//        , LocalDate.of(2023, 1, 2), integerValue);
//        trainingService.create(trainingDTO);
//
//        TrainingDTO trainingDTO2 = new TrainingDTO(traineeService.findByUserName("Pavel.Melnik"), trainerService.findByUserName("Ivan.Petrov"), "hard training2", trainer.getTrainingType()
//                , LocalDate.of(2023, 1, 3), integerValue);
//        trainingService.create(trainingDTO2);
//
//        waitForInput();
//
//        List<Training> trainings = traineeService.getWithCriteria("Pavel.Melnik",
//                LocalDate.of(2023, 1, 1), null, null, null);
//        System.out.println(trainings);
//
//        waitForInput();
//
//        traineeService.deleteByUserName("Pavel.Melnik");
//
//        waitForInput();
//
//        applicationContext.close();
//    }
//
//    private static void waitForInput() {
//        System.out.println("Press 'Enter' to continue.");
//        new Scanner(System.in).nextLine();
//    }

}
