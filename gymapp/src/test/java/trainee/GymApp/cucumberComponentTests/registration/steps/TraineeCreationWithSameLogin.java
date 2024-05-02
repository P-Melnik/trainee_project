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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class TraineeCreationWithSameLogin {

    private final TestRestTemplate restTemplate;

    private ResponseEntity<CredentialsDTO> responseEntity1;
    private ResponseEntity<CredentialsDTO> responseEntity2;
    private TraineeDTO traineeDTO1;
    private TraineeDTO traineeDTO2;



    @Given("trainees data with same firstname and lastname")
    public void createTraineeUsersData(DataTable dataTable) {
        List<Map<String, String>> traineeDataTableList = dataTable.asMaps(String.class, String.class);
        List<TraineeDTO> dtoList = new ArrayList<>();
        for (Map<String, String> traineeData : traineeDataTableList) {
            dtoList.add(TraineeDTO.builder()
                    .firstName(traineeData.get("firstname"))
                    .lastName(traineeData.get("lastname"))
                    .build());
        }
        traineeDTO1 = dtoList.get(0);
        traineeDTO2 = dtoList.get(1);
    }

    @When("user1, user2 send POST request")
    public void sendPostForTraineesWithSameLogin() {
        String url = "http://localhost:8081/trainee";
        responseEntity1 = restTemplate.postForEntity(url, traineeDTO1, CredentialsDTO.class);
        responseEntity2 = restTemplate.postForEntity(url, traineeDTO2, CredentialsDTO.class);
    }

    @Then("returns first credentials where username in the format firstName.lastName")
    public void returnTrainee1login() {
        Assertions.assertEquals(String.format("%s.%s", traineeDTO1.getFirstName().toLowerCase(), traineeDTO1.getLastName().toLowerCase()),
                Objects.requireNonNull(responseEntity1.getBody()).getUserName().toLowerCase());

    }

    @And("returns second credentials where username in the format firstName.lastName1")
    public void returnTrainee2login() {
        Assertions.assertEquals(String.format("%s.%s", traineeDTO2.getFirstName().toLowerCase(), traineeDTO2.getLastName().toLowerCase()),
                Objects.requireNonNull(responseEntity2.getBody()).getUserName().toLowerCase());
    }

}
