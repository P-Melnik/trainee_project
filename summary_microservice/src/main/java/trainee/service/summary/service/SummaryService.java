package trainee.service.summary.service;

import trainee.service.summary.models.RequestWorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

public interface SummaryService {

    void manage(RequestWorkloadDTO requestWorkloadDTO);

    Optional<Workload> findByUsername(String username);

}
