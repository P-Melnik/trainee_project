package trainee.GymApp.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.LoginResponse;
import trainee.GymApp.service.impl.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final BruteForceProtector bruteForceProtector;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        Optional<LoginResponse> loginResponse = Optional.empty();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            var user = userRepo.findByUserName(loginRequest.getUsername());
            if (bruteForceProtector.isUserLocked(loginRequest.getUsername())) {
                bruteForceProtector.resetFailedLoginAttempts(loginRequest.getUsername());
            }
            var jwt = jwtService.generateToken(user);
            log.debug("Generated JWT token");
            loginResponse = Optional.of(LoginResponse.builder().token(jwt).build());
        } catch (BadCredentialsException e) {
            log.error("Bad Cred error");
        } catch (LockedException e) {
            log.error("Locked acc");
        } catch (AuthenticationException e) {
            log.error("Auth failure");
        }
        return loginResponse;
    }

    @Override
    public String handleLoginFailure(LoginRequest loginRequest) {
        int attempts;
        if (!bruteForceProtector.isUserLocked(loginRequest.getUsername())) {
            attempts = bruteForceProtector.recordFailedLoginAttempt(loginRequest.getUsername());
        } else {
            return "Your account was locked. Time until unlock: " + bruteForceProtector.getTime(loginRequest.getUsername());
        }
        if (attempts == bruteForceProtector.getMaxAttempts()) {
            return "Incorrect credentials. You have last attempt before temporary account locking.";
        }
        return "Incorrect credentials. You have left " + (bruteForceProtector.getMaxAttempts() - attempts) + " attempts.";
    }

}
