package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainerFullDTO;
import trainee.GymApp.dto.TrainingsForResponse;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.mappers.ControllersMapper;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<CredentialsDTO> register(@Valid @RequestBody TrainerDTO trainerDTO) {
        CredentialsDTO credentialsDTO = facade.createTrainerProfile(trainerDTO);
        return new ResponseEntity<>(credentialsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerFullDTO> getTrainer(@PathVariable(name = "username") String username, @RequestHeader(name = "password") String password) {
        Trainer trainer = facade.getTrainerByUserName(username, password);
        return new ResponseEntity<>(ControllersMapper.mapTrainerToFullDTO(trainer), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TrainerFullDTO> update(@PathVariable(name = "username") String username,
                                                 @RequestHeader(name = "password") String password,
                                                 @Valid @RequestBody TrainerDTO trainerDTO) {
        Trainer trainer = facade.getTrainerByUserName(username, password);
        facade.updateTrainer(ControllersMapper.processTrainerUpdate(trainer, trainerDTO), username, password);
        return new ResponseEntity<>(ControllersMapper.mapTrainerToFullDTO(trainer), HttpStatus.OK);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingsForResponse>> getTrainings(@PathVariable(name = "username") String username, @RequestHeader(name = "password") String password, @RequestParam(name = "from", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate from,
                                                                   @RequestParam(name = "to", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate to, @RequestParam(name = "trainee-username", required = false) String traineeUsername) {
        List<Training> list = facade.getTrainerTrainingsWithCriteria(username, password, from, to, traineeUsername);
        List<TrainingsForResponse> response = list.stream().map(TrainingsForResponse::mapForTrainer).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable(name = "username") String username, @RequestHeader(name = "password") String password, @RequestParam(name = "isActive") boolean isActive) {
        Trainer trainer = facade.getTrainerByUserName(username, password);
        if (trainer.getUser().isActive() != isActive) {
            facade.changeTrainerStatus(username, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
