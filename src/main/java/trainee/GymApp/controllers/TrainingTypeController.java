package trainee.GymApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainee.GymApp.Facade;
import trainee.GymApp.entity.TrainingType;

import java.util.Set;

@RestController
@RequestMapping("/training-types")
public class TrainingTypeController {

    @Autowired
    private Facade facade;

    @GetMapping
    public Set<TrainingType> get() {
        return facade.getAllTrainingTypes();
    }

}
