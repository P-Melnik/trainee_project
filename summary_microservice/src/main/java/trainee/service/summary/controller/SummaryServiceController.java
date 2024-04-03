package trainee.service.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import trainee.service.summary.models.RequestWorkloadDTO;
import trainee.service.summary.models.Workload;
import trainee.service.summary.service.SummaryService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class SummaryServiceController {

    @Autowired
    private SummaryService summaryService;

    @PostMapping("/workload/{username}")
    ResponseEntity<HttpStatus> manageTrainingSummary(@PathVariable(value = "username") String username,
                                                     @Valid @RequestBody RequestWorkloadDTO workloadDTO) {
        summaryService.manage(workloadDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("workload/{username}")
    ResponseEntity<Workload> getTrainingWorkload(@PathVariable(value = "username") String username) {
        Optional<Workload> optionalWorkload = summaryService.findByUsername(username);
        return optionalWorkload.map(workload -> new ResponseEntity<>(workload, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}