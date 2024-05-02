package trainee.GymApp.cucumberComponentTests.registration.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TraineeDTO;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CreateTraineeWithEmptyLastNameStepsDefinition {

    private final TestRestTemplate testRestTemplate;

    private ResponseEntity<CredentialsDTO> responseEntity;
    private TraineeDTO traineeDTO;

    private String firstname;
    private String lastname;

    @Given("trainee data with empty last name")
    public void createIncorrectUserData(DataTable dataTable) {
        List<Map<String, String>> traineeDataTableList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> traineeData : traineeDataTableList) {
            firstname = traineeData.get("firstname");
            lastname = traineeData.get("lastname");
        }
        traineeDTO = TraineeDTO.builder()
                .firstName(firstname)
                .lastName(lastname)
                .build();
    }

    @When("trainee user sends a POST request with the last first name")
    public void sendPostRequestInvalidData() {
        String url = "http://localhost:8081/trainee";
        responseEntity = testRestTemplate.postForEntity(url, traineeDTO, CredentialsDTO.class);
    }

    @Then("trainee response for data with empty last name contains status code {int}")
    public void validateResponse(int status) {
        Assertions.assertEquals(status, responseEntity.getStatusCode().value());
    }
}
