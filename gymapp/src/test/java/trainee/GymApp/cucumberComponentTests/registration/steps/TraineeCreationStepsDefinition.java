package trainee.GymApp.cucumberComponentTests.registration.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
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
import java.util.Objects;

@RequiredArgsConstructor
public class TraineeCreationStepsDefinition {

    private final TestRestTemplate restTemplate;

    private ResponseEntity<CredentialsDTO> responseEntity;

    private TraineeDTO traineeDto;

    private String firstName;
    private String lastName;


    @Given("trainee correct data")
    public void createCorrectUserData(DataTable dataTable) {
        List<Map<String, String>> traineeDataTableList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> traineeData : traineeDataTableList) {
            firstName = traineeData.get("firstname");
            lastName = traineeData.get("lastname");
        }
        traineeDto = TraineeDTO.builder().firstName(firstName).lastName(lastName).build();
    }

    @When("user sends a POST request with correct data")
    public void sendPostRequestCorrectDataToTrainee() {
        String url = "http://localhost:8080/trainee";
        responseEntity = restTemplate.postForEntity(url, traineeDto, CredentialsDTO.class);
    }

    @Then("response contains status code {int}")
    public void validateResponseCreatedTraineeStatusCode(int statusCode) {
        Assertions.assertEquals(statusCode, responseEntity.getStatusCode().value());
    }

    @And("returns username")
    public void returnUsernameInFormat() {
        String username = Objects.requireNonNull(responseEntity.getBody()).getUserName();
        Assertions.assertTrue(username.toLowerCase().contains(firstName.toLowerCase()));
        Assertions.assertTrue(username.toLowerCase().contains(lastName.toLowerCase()));
    }

    @And("password of length {int}")
    public void validateTraineeLengthPassword(int passwordLength) {
        Assertions.assertEquals(passwordLength, Objects.requireNonNull(responseEntity.getBody()).getPassword().length());
    }

}
