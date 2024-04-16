package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.dto.ActionType;
import trainee.GymApp.dto.WorkloadDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.exceptions.DeleteException;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.mappers.WorkloadTrainingMapper;
import trainee.GymApp.messaging.Sender;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Value("${queue.name.trainer.workload}")
    private String queueName;

    @Autowired
    private Facade facade;

    @Autowired
    private Sender sender;

    @PostMapping
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody TrainingDTO trainingDTO) {
        facade.createTraining(trainingDTO);
        WorkloadDTO workloadDTO = WorkloadTrainingMapper.map(trainingDTO, ActionType.ADD);
        sender.sendTrainerWorkloadMessage(queueName, workloadDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") long id) {
        Training training = facade.getTrainingById(id);
        if (training != null) {
            WorkloadDTO workloadDTO = new WorkloadDTO(training.getTrainer().getUser().getUsername(),
                    training.getTrainer().getUser().getFirstName(), training.getTrainer().getUser().getLastName(),
                    training.getTrainer().getUser().isActive(), training.getTrainingDate(), training.getTrainingDuration(), ActionType.DELETE);
            facade.deleteTraining(id);
            sender.sendTrainerWorkloadMessage(queueName, workloadDTO);
        } else {
            throw new DeleteException(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
