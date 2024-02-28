package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.Facade;
import trainee.GymApp.dto.TrainingDTO;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestHeader(name = "username") String username, @RequestHeader(name = "password") String password,
                                          @RequestBody TrainingDTO trainingDTO) {
        facade.createTraining(trainingDTO, username, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
