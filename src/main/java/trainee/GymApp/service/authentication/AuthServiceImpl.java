package trainee.GymApp.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.GymApp.service.TraineeService;
import trainee.GymApp.service.TrainerService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;

    private BruteForceProtector bruteForceProtector;

    public boolean authenticate(String username, String password) {
        return traineeService.checkPassword(username, password) || trainerService.checkPassword(username, password);
    }

}
