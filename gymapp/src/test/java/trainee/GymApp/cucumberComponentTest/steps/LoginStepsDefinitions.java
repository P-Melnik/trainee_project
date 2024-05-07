package trainee.GymApp.cucumberComponentTest.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import trainee.GymApp.dto.CredentialsDTO;
import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.TraineeDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class LoginStepsDefinitions {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    private ResponseEntity<CredentialsDTO> credResponseEntity;
    private ResponseEntity<Void> loginResponseEntity;

    private String loginUrl;

    @Given("create profile with first name {string} and last name {string}")
    public void createCorrectUserData(String firstName, String lastName) {
        TraineeDTO traineeDto = TraineeDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.of(1991, 1, 1))
                .address("Oak st.")
                .build();
        String traineeUrl = "http://localhost:" + port + "/trainee";
        loginUrl = "http://localhost:" + port + "/login";
        credResponseEntity = restTemplate.postForEntity(traineeUrl, traineeDto, CredentialsDTO.class);
    }

    @When("user sends a POST request with correct credentials")
    public void getWithValidCredentials() {
        LoginRequest requestLoginDto = LoginRequest.builder()
                .username(Objects.requireNonNull(credResponseEntity.getBody()).getUserName())
                .password(credResponseEntity.getBody().getPassword())
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LoginRequest> loginRequestHttpEntity = new HttpEntity<>(requestLoginDto, httpHeaders);
        loginResponseEntity = restTemplate.postForEntity(loginUrl, loginRequestHttpEntity, Void.class);
    }

    @Then("cookie contain jwtToken")
    public void validateTokenIsNotNull() {
        List<String> cookies = loginResponseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);
        boolean tokenFound = false;
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.contains("jwtToken")) {
                    tokenFound = true;
                    break;
                }
            }
        }
        Assertions.assertTrue(tokenFound, "Token not found in the cookie");
    }

    @And("status code {int}")
    public void validationStatusCode(int statusCode) {
        Assertions.assertEquals(statusCode, loginResponseEntity.getStatusCode().value());
    }

    @When("user sends a POST request with invalid credentials")
    public void postWithInvalidCredentials() {
        String invalidPassword = String.format("%s1", Objects.requireNonNull(credResponseEntity.getBody()).getPassword());
        LoginRequest requestLoginDto = LoginRequest.builder()
                .username(credResponseEntity.getBody().getUserName())
                .password(invalidPassword)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LoginRequest> loginRequestHttpEntity = new HttpEntity<>(requestLoginDto, httpHeaders);
        loginResponseEntity = restTemplate.postForEntity(loginUrl, loginRequestHttpEntity, Void.class);
    }

    @Then("response returns status code {int}")
    public void validationStatusCodeWithInvalidCred(int statusCode) {
        Assertions.assertEquals(statusCode, loginResponseEntity.getStatusCode().value());
    }

}
