package trainee.service.summary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.service.summary.models.Workload;
import trainee.service.summary.storage.Storage;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class StorageRepo implements Repo {

    private final Storage storage;

    @Autowired
    public StorageRepo(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Optional<Workload> findByUserName(String username) {
        return storage.getWorkloadByUsername(username);
    }

    @Override
    public Optional<Workload> create(String username) {
        return storage.create(username);
    }

    @Override
    public void add(String username, LocalDate localDate, double duration) {
        storage.add(username, localDate, duration);
    }

    @Override
    public void delete(String username, LocalDate localDate, double duration) {
        storage.delete(username, localDate, duration);
    }
}
