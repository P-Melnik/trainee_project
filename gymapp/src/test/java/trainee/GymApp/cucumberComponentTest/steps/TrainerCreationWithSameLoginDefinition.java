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
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.entity.TrainingType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class TrainerCreationWithSameLoginDefinition {

    private final TestRestTemplate restTemplate;

    private ResponseEntity<CredentialsDTO> responseEntity1;
    private ResponseEntity<CredentialsDTO> responseEntity2;
    private TrainerDTO trainerDTO1;
    private TrainerDTO trainerDTO2;

    @LocalServerPort
    private int port;

    @Given("trainers with same data")
    public void createTraineeUsersData(DataTable dataTable) {
        List<Map<String, String>> trainerDataTableList = dataTable.asMaps(String.class, String.class);
        List<TrainerDTO> dtoList = new ArrayList<>();
        for (Map<String, String> trainerData : trainerDataTableList) {
            dtoList.add(TrainerDTO.builder()
                    .firstName(trainerData.get("firstName"))
                    .lastName(trainerData.get("lastName"))
                    .trainingType(new TrainingType(Integer.parseInt(trainerData.get("id")), trainerData.get("trainingType")))
                    .build());
        }
        trainerDTO1 = dtoList.get(0);
        trainerDTO2 = dtoList.get(1);
    }

    @When("trainers send POST request")
    public void sendPostForTraineesWithSameLogin() {
        String url = "http://localhost:" + port + "/trainer";
        responseEntity1 = restTemplate.postForEntity(url, trainerDTO1, CredentialsDTO.class);
        responseEntity2 = restTemplate.postForEntity(url, trainerDTO2, CredentialsDTO.class);
    }

    @Then("returns trainer first credentials where username in the format firstName.lastName")
    public void returnTrainee1login() {
        Assertions.assertEquals(String.format("%s.%s", trainerDTO1.getFirstName().toLowerCase(), trainerDTO1.getLastName().toLowerCase()),
                Objects.requireNonNull(responseEntity1.getBody()).getUserName().toLowerCase());

    }

    @And("returns trainer second credentials where username in the format firstName.lastName1")
    public void returnTrainee2login() {
        Assertions.assertEquals(String.format("%s.%s1", trainerDTO2.getFirstName().toLowerCase(), trainerDTO2.getLastName().toLowerCase()),
                Objects.requireNonNull(responseEntity2.getBody()).getUserName().toLowerCase());
    }
}
