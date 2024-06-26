package trainee.service.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import trainee.service.summary.exceptions.WorkloadCalculationException;
import trainee.service.summary.models.MonthlyWorkload;
import trainee.service.summary.models.Workload;
import trainee.service.summary.service.SummaryService;

import java.util.Optional;

@RestController
public class SummaryServiceController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("workload/{username}")
    ResponseEntity<Workload> getTrainingWorkload(@PathVariable(value = "username") String username) {
        Optional<Workload> optionalWorkload = summaryService.findByUsername(username);
        return optionalWorkload.map(workload -> new ResponseEntity<>(workload, HttpStatus.OK))
                .orElseThrow(() -> new WorkloadCalculationException(username));
    }

    @GetMapping("workload/{username}/{year}/{month}")
    ResponseEntity<MonthlyWorkload> getTrainingWorkloadMonthly(@PathVariable(value = "username") String username,
                                                               @PathVariable(value = "year") int year,
                                                               @PathVariable(value = "month") String month) {
        Optional<Workload> optionalWorkload = summaryService.findByUsername(username);
        return optionalWorkload.map(workload -> new ResponseEntity<>(optionalWorkload.get().getMonthlyWorkload(year, month), HttpStatus.OK))
                .orElseThrow(() -> new WorkloadCalculationException(username));
    }

}
