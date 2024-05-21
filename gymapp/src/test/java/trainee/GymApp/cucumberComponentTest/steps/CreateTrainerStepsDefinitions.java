package trainee.GymApp.cucumberComponentTest.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import trainee.GymApp.CucumberUrlConstants.UrlConstants;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.TrainingType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateTrainerStepsDefinitions {

    @LocalServerPort
    private int port;

    private final TestRestTemplate testRestTemplate;

    private ResponseEntity<CredentialsDTO> responseEntity;

    private TrainerDTO trainerDTO;

    private String firstName;
    private String lastName;
    private String trainingType;

    @Given("trainer correct user data")
    public void createCorrectUserData(DataTable dataTable) {
        List<Map<String, String>> trainerDataTableList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> trainerData : trainerDataTableList) {
            firstName = trainerData.get("firstName");
            lastName = trainerData.get("lastName");
            trainingType = trainerData.get("trainingType");
        }
        trainerDTO = TrainerDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .trainingType(new TrainingType(1, trainingType))
                .build();
    }

    @When("trainer user sends a POST request with correct data")
    public void sendPostRequestCorrectDataToTrainer() {
        String url = String.format(UrlConstants.TRAINER_URL_FORMAT, port);
        responseEntity = testRestTemplate.postForEntity(url, trainerDTO, CredentialsDTO.class);
    }

    @Then("trainer response contains status code {int}")
    public void validateResponseCreatedTrainerStatusCode(int statusCode) {
        Assertions.assertEquals(statusCode, responseEntity.getStatusCode().value());
    }

    @And("returns trainer username")
    public void returnUsernameInFormat() {
        String username = Objects.requireNonNull(responseEntity.getBody()).getUserName();
        Assertions.assertTrue(username.toLowerCase().contains(firstName.toLowerCase()));
        Assertions.assertTrue(username.toLowerCase().contains(lastName.toLowerCase()));
    }

    @And("trainer password of length {int}")
    public void validateTrainerLengthPassword(int passwordLength) {
        Assertions.assertEquals(passwordLength, Objects.requireNonNull(responseEntity.getBody()).getPassword().length());
    }

}
