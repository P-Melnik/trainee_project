package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.client.WorkloadSummaryClient;
import trainee.GymApp.dto.ActionType;
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
}
