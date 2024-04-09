package trainee.service.summary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.service.summary.dao.Repo;
import trainee.service.summary.exceptions.WorkloadCalculationException;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private Repo storageRepo;

    @Override
    public void manage(WorkloadDTO requestWorkloadDTO) {
        String username = requestWorkloadDTO.getUserName();
        if (username == null) {
            log.error("Trying to save for null username");
            throw new WorkloadCalculationException(null);
        }
        if (ActionType.ADD.equals(requestWorkloadDTO.getActionType())) {
            storageRepo.add(requestWorkloadDTO);
        } else {
            storageRepo.delete(requestWorkloadDTO);
        }
    }

    @Override
    public Optional<Workload> findByUsername(String username) {
        return storageRepo.findByUserName(username);
    }

}
