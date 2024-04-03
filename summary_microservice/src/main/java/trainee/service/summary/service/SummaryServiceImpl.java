package trainee.service.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.service.summary.dao.Repo;
import trainee.service.summary.exceptions.NotExistingWorkloadOperation;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.RequestWorkloadDTO;
import trainee.service.summary.models.Workload;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private Repo storageRepo;

    @Override
    public void manage(RequestWorkloadDTO requestWorkloadDTO) {
        LocalDate localDate = requestWorkloadDTO.getTrainingDate();
        double trainingDuration = requestWorkloadDTO.getTrainingDuration();
        String username = requestWorkloadDTO.getUserName();
        Optional<Workload> optionalWorkload = storageRepo.findByUserName(username);
        if (optionalWorkload.isPresent()) {
            if (requestWorkloadDTO.getActionType().equals(ActionType.ADD)) {
                storageRepo.add(username, localDate, trainingDuration);
            } else {
                storageRepo.delete(username, localDate, trainingDuration);
            }
        } else {
            if (requestWorkloadDTO.getActionType().equals(ActionType.ADD)) {
                Optional<Workload> createdWorkload = storageRepo.create(username);
                storageRepo.add(username, localDate, trainingDuration);
            } else {
                throw new NotExistingWorkloadOperation(username);
            }
        }
    }

    @Override
    public Optional<Workload> findByUsername(String username) {
        return storageRepo.findByUserName(username);
    }
}
