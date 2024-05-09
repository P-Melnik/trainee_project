package trainee.service.summary.cucumberComponentTest.steps;

import io.cucumber.java.en.And;
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

public class DeleteTrainingStepsDefinition {

    @Autowired
    private SummaryServiceImpl summaryService;

    @When("new WorkloadDTO is send to DB")
    public void sendNewWorkloadTODB() {
        WorkloadDTO workloadDTO = new WorkloadDTO("B.B", "B", "B",
                true, LocalDate.of(2024, 4, 4), 60.0, ActionType.ADD);
        summaryService.manage(workloadDTO);
    }

    @And("Send workload with actionType delete")
    public void sendDelete() {
        WorkloadDTO workloadDto = new WorkloadDTO("B.B", "B", "B",
                true, LocalDate.of(2024, 4, 4), 60.0, ActionType.DELETE);
        summaryService.manage(workloadDto);
    }

    @Then("found workload by username is empty")
    public void lookForWorkload() {
        Optional<Workload> foundWorkload = summaryService.findByUsername("B.B");
        Assertions.assertTrue(foundWorkload.isEmpty());
    }

}
