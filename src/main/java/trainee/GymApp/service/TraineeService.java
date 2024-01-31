package trainee.GymApp.service;

import trainee.GymApp.dto.TraineeDTO;
import trainee.GymApp.entity.Trainee;

public interface TraineeService extends Service<Trainee, TraineeDTO> {

    void update(Trainee trainee);

    void delete(long traineeId);

}
