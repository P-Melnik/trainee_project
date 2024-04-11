package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.dto.TrainingDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody TrainingDTO trainingDTO) {
        facade.createTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
