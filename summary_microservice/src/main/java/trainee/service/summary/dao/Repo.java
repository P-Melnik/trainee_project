package trainee.service.summary.dao;

import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

public interface Repo {

    Optional<Workload> findByUserName(String username);

    Optional<Workload> create(String username);

    void add(WorkloadDTO requestWorkloadDTO);

    void delete(WorkloadDTO requestWorkloadDTO);

}
