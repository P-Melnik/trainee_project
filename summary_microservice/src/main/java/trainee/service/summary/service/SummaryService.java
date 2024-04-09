package trainee.service.summary.service;

import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

public interface SummaryService {

    void manage(WorkloadDTO requestWorkloadDTO);

    Optional<Workload> findByUsername(String username);

}
