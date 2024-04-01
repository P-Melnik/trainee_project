package trainee.GymApp.service.authentication;

import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.LoginResponse;

import java.util.Optional;

public interface AuthService {

    Optional<LoginResponse> login(LoginRequest loginRequest);

    String handleLoginFailure(LoginRequest loginRequest);

}
