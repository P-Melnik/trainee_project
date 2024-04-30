package trainee.service.summary.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import trainee.service.summary.models.Workload;

import java.util.Optional;

public interface Repo extends MongoRepository<Workload, String> {

    Optional<Workload> findByUsername(String username);

}
