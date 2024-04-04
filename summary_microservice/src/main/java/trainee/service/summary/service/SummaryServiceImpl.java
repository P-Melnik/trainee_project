package trainee.service.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.service.summary.dao.Repo;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.RequestWorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private Repo storageRepo;

    @Override
    public void manage(RequestWorkloadDTO requestWorkloadDTO) {
        String username = requestWorkloadDTO.getUserName();
        if (requestWorkloadDTO.getActionType().equals(ActionType.ADD)) {
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
