package trainee.service.summary.dao;

import trainee.service.summary.models.RequestWorkloadDTO;
import trainee.service.summary.models.Workload;

import java.util.Optional;

public interface Repo {

    Optional<Workload> findByUserName(String username);

    Optional<Workload> create(String username);

    void add(RequestWorkloadDTO requestWorkloadDTO);

    void delete(RequestWorkloadDTO requestWorkloadDTO);

}
