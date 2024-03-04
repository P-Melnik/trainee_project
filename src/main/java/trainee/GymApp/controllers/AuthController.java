package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.facade.Facade;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private Facade facade;

    @GetMapping
    public ResponseEntity<HttpStatus> login(@RequestHeader(name = "username") String username, @RequestHeader(name = "password") String password) {
        facade.authenticate(username, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change")
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(name = "username") String username, @RequestHeader(name = "oldPassword") String oldPassword,
                                                     @RequestHeader(name = "newPassword") String newPassword) {
        facade.changePassword(username, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
