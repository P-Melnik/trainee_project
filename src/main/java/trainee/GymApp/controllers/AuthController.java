package trainee.GymApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.dto.LoginRequest;
import trainee.GymApp.dto.LoginResponse;
import trainee.GymApp.facade.Facade;

@RestController
public class AuthController {

    @Autowired
    private Facade facade;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = facade.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/change")
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(name = "username") String username, @RequestHeader(name = "oldPassword") String oldPassword,
                                                     @RequestHeader(name = "newPassword") String newPassword) {
        facade.changePassword(username, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
