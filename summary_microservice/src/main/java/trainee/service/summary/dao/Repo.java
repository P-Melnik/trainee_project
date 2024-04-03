package trainee.service.summary.dao;

import trainee.service.summary.models.Workload;

import java.time.LocalDate;
import java.util.Optional;

public interface Repo {

    Optional<Workload> findByUserName(String username);

    Optional<Workload> create(String username);

    void add(String username, LocalDate localDate, double duration);

    void delete(String username, LocalDate localDate, double duration);

}
