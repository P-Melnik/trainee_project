package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.facade.Facade;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TraineeFullDTO;
import trainee.GymApp.dto.TrainerForSet;
import trainee.GymApp.dto.TrainersToAdd;
import trainee.GymApp.dto.TrainingsForResponse;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.mappers.ControllersMapper;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainee")
public class TraineeController {

    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<CredentialsDTO> register(@Valid @RequestBody TraineeDTO traineeDTO) {
        CredentialsDTO credentialsDTO = facade.createTraineeProfile(traineeDTO);
        return new ResponseEntity<>(credentialsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeFullDTO> getTrainee(@PathVariable(name = "username") String username) {
        Trainee trainee = facade.getTraineeByUserName(username);
        return new ResponseEntity<>(ControllersMapper.mapTraineeToFullDTO(trainee), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TraineeFullDTO> update(@PathVariable(name = "username") String username,
                                                 @Valid @RequestBody TraineeDTO traineeDTO) {
        Trainee trainee = facade.getTraineeByUserName(username);
        facade.updateTrainee(ControllersMapper.processTraineeUpdate(trainee, traineeDTO));
        return new ResponseEntity<>(ControllersMapper.mapTraineeToFullDTO(trainee), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "username") String username) {
        facade.deleteTrainee(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/not-trainers")
    public ResponseEntity<Set<TrainerForSet>> notAssignedTrainers(@PathVariable(name = "username") String username) {
        Set<Trainer> set = facade.getNotAssignedTrainers(username);
        Set<TrainerForSet> response = set.stream().map(TrainerForSet::map).collect(Collectors.toSet());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{username}/trainers")
    public ResponseEntity<Set<TrainerForSet>> updateTrainers(@PathVariable(name = "username") String username, @Valid @RequestBody Set<TrainersToAdd> trainers) {
        return new ResponseEntity<>(facade.updateTrainersForTrainee(username, trainers), HttpStatus.OK);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingsForResponse>> getTrainings(@PathVariable(name = "username") String username, @RequestParam(name = "from", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate from,
                                                                   @RequestParam(name = "to", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate to, @RequestParam(name = "trainer-name", required = false) String trainerName,
                                                                   @RequestParam(name = "training-type", required = false) String trainingType) {
        List<Training> list = facade.getTraineeTrainingsWithCriteria(username, from, to, trainerName, trainingType);
        List<TrainingsForResponse> response = list.stream().map(TrainingsForResponse::mapForTrainee).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable(name = "username") String username, @RequestParam(name = "isActive") boolean isActive) {
        Trainee trainee = facade.getTraineeByUserName(username);
        if (trainee.getUser().isActive() != isActive) {
            facade.changeTraineeStatus(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
