package trainee.GymApp.cucumberIntegrationTest.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.LoginResponse;
import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.dto.TrainerDTO;
import trainee.GymApp.dto.TrainingDTO;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.impl.JwtService;
import trainee.service.summary.models.Workload;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RequiredArgsConstructor
public class SaveTrainingAndGetReportDefinition {

    private final TestRestTemplate testRestTemplate;
    private final JwtService jwtService;

    UserDetails userDetails;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ResponseEntity<HttpStatus> httpStatusResponseEntity;

    private final User traineeUser = new User();
    private final User trainerUser = new User();
    private final Trainee trainee = new Trainee();
    private final Trainer trainer = new Trainer();

    private String trainerUserName;
    private String passwordTrainer;
    private String token;
    private double duration;

    @Given("create trainee with firstname {string} and lastname {string}")
    public void createTrainee(String firstName, String lastName) {
        TraineeDTO traineeDTO = TraineeDTO.builder().firstName(firstName).lastName(lastName)
                .dateOfBirth(LocalDate.of(1991, 1, 1)).address("Oak st.").build();
        String url = "http://localhost:8080/trainee";
        ResponseEntity<CredentialsDTO> credentialsDTOResponseEntity = testRestTemplate.postForEntity(url, traineeDTO, CredentialsDTO.class);
        System.out.println(Objects.requireNonNull(credentialsDTOResponseEntity.getBody()).getUserName());
        String traineeUserName = Objects.requireNonNull(credentialsDTOResponseEntity.getBody()).getUserName();
        traineeUser.setFirstName(firstName);
        traineeUser.setLastName(lastName);
        traineeUser.setUserName(traineeUserName);
        traineeUser.setPassword(credentialsDTOResponseEntity.getBody().getPassword());
        trainee.setUser(traineeUser);
        trainee.setDateOfBirth(LocalDate.of(1991, 1, 1));
        trainee.setAddress("Oak st.");
    }

    @And("create trainer with firstname {string} and lastname {string}")
    public void createTrainer(String firstName, String lastName) {
        TrainerDTO trainerDTO = TrainerDTO.builder().firstName(firstName).lastName(lastName)
                .trainingType(new TrainingType(1, "GYM")).build();
        String url = "http://localhost:8080/trainer";
        ResponseEntity<CredentialsDTO> credentialsDTOResponseEntity = testRestTemplate.postForEntity(url, trainerDTO, CredentialsDTO.class);
        trainerUserName = Objects.requireNonNull(credentialsDTOResponseEntity.getBody()).getUserName();
        passwordTrainer = credentialsDTOResponseEntity.getBody().getPassword();
        trainerUser.setFirstName(firstName);
        trainerUser.setLastName(lastName);
        trainerUser.setUserName(trainerUserName);
        trainerUser.setPassword(passwordTrainer);
        trainer.setUser(trainerUser);
        trainer.setTrainingType(trainerDTO.getTrainingType());
    }

    @And("login as trainer")
    public void loginViaTrainerProfile() {
        LoginRequest loginRequest = LoginRequest.builder().username(trainerUserName)
                .password(passwordTrainer).build();
        String url = "http://localhost:8080/login";
        token = Objects.requireNonNull(testRestTemplate.postForEntity(url, loginRequest, LoginResponse.class).getBody()).getToken();
        userDetails = org.springframework.security.core.userdetails.User.withUsername(trainerUserName)
                .password(passwordTrainer).build();
    }

    @When("create a training on date {string} with duration {string} and training name {string} and check token")
    public void createFirstTraining(String date, String trainingDuration, String trainingName) {
        LocalDate localDate = LocalDate.parse(date, formatter);
        duration = Double.parseDouble(trainingDuration);
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingName)
                .trainingDate(localDate)
                .trainingDuration(duration)
                .build();
        String url = "http://localhost:8080/trainings";
        Assertions.assertTrue(jwtService.isTokenValid(token, userDetails));
        httpStatusResponseEntity = testRestTemplate.postForEntity(url, trainingDTO, HttpStatus.class);
    }

    @Then("returns status code {int} after adding training")
    public void validateStatusCode(int statusCode) {
        Assertions.assertEquals(statusCode, httpStatusResponseEntity.getStatusCode().value());
    }

    @And("summary service contains a report with training data of {string}")
    public void validateFirstReport(String trainer) {
        String url = "http://localhost:8080/workload/" + trainer;
        ResponseEntity<Workload> responseEntity
                = testRestTemplate.getForEntity(url, Workload.class);
        Workload workload = responseEntity.getBody();
        Assertions.assertNotNull(workload);
        Assertions.assertEquals(duration, workload.getMonthlyWorkload(2024, "april").getWorkloadDuration());
    }

    @When("add a training on date {string} with duration {string} and name of {string} and check token")
    public void createSecondTraining(String date, String trainingDuration, String trainingName) {
        LocalDate localDate = LocalDate.parse(date, formatter);
        duration = Double.parseDouble(trainingDuration);
        TrainingDTO trainingDTO = TrainingDTO.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingName)
                .trainingDate(localDate)
                .trainingDuration(duration)
                .build();
        String url = "http://localhost:8080/trainings";
        Assertions.assertTrue(jwtService.isTokenValid(token, userDetails));
        httpStatusResponseEntity = testRestTemplate.postForEntity(url, trainingDTO, HttpStatus.class);
    }

    @Then("returns status code {int} after adding a training in the same month")
    public void validateStatusCode2(int statusCode) {
        Assertions.assertEquals(statusCode, httpStatusResponseEntity.getStatusCode().value());
    }

    @And("the report shows a total duration of {int} in april 2024 for {string}")
    public void validateSecondReport(int secondDuration, String trainer) {
        String url = "http://localhost:8080/workload/" + trainer;
        ResponseEntity<Workload> responseEntity
                = testRestTemplate.getForEntity(url, Workload.class);
        Workload workload = responseEntity.getBody();
        Assertions.assertNotNull(workload);
        Assertions.assertEquals(secondDuration, workload.getMonthlyWorkload(2024, "april").getWorkloadDuration());
    }
}

