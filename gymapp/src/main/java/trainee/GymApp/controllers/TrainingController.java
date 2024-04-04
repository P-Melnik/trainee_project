package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.client.WorkloadSummaryClient;
import trainee.GymApp.dto.ActionType;
import trainee.GymApp.dto.RequestWorkloadDTO;
import trainee.GymApp.entity.Training;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.mappers.WorkloadTrainingMapper;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private Facade facade;

    @Autowired
    private WorkloadSummaryClient workloadSummaryClient;

    @PostMapping
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody TrainingDTO trainingDTO) {
        facade.createTraining(trainingDTO);
        workloadSummaryClient.manageWorkload(trainingDTO.getTrainer().getUser().getUsername(),
                WorkloadTrainingMapper.map(trainingDTO, ActionType.ADD));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") long id) {
        Training training = facade.getTrainingById(id);
        RequestWorkloadDTO requestWorkloadDTO = new RequestWorkloadDTO(training.getTrainer().getUser().getUsername(),
                training.getTrainer().getUser().getFirstName(), training.getTrainer().getUser().getLastName(),
                training.getTrainer().getUser().isActive(), training.getTrainingDate(), training.getTrainingDuration(), ActionType.DELETE);
        facade.deleteTraining(id);
        workloadSummaryClient.manageWorkload(training.getTrainer().getUser().getUsername(), requestWorkloadDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
