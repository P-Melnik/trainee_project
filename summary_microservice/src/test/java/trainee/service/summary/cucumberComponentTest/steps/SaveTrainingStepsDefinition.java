package trainee.service.summary.cucumberComponentTest.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.service.SummaryServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

public class SaveTrainingStepsDefinition {

    @Autowired
    private SummaryServiceImpl summaryService;

    @When("WorkloadDTO is send to DB")
    public void sendWorkloadToDB() {
        WorkloadDTO workloadDTO = new WorkloadDTO("A.A", "A", "A",
                true, LocalDate.of(2024, 4, 4), 60.0, ActionType.ADD);
        summaryService.manage(workloadDTO);
    }

    @Then("DB finds workload by username")
    public void findVIAUsername() {
        Optional<Workload> foundWorkload = summaryService.findByUsername("A.A");
        Assertions.assertTrue(foundWorkload.isPresent());
    }

}
