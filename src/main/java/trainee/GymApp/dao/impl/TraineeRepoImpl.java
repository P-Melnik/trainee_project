package trainee.GymApp.dao.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TraineeRepo;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.storage.Storage;

import java.util.List;

@Slf4j
@Repository
@NoArgsConstructor
public class TraineeRepoImpl implements TraineeRepo {

    private Storage storage;

    @Autowired
    public TraineeRepoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void update(Trainee trainee) {
        log.debug("Updating Trainee: " + trainee);
        storage.update(trainee);
    }

    @Override
    public void create(Trainee trainee) {
        log.debug("Creating trainee: " + trainee);
        storage.save(trainee);
    }

    @Override
    public Trainee findById(long id) {
        log.debug("Find trainee by id trainee:" + id);
        return storage.findById(Trainee.class, id);
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Fetching all Trainees");
        return storage.findAll(Trainee.class);
    }

    @Override
    public void delete(long id) {
        log.debug("Deleting Trainee by id trainee:" + id);
        storage.delete(Trainee.class, id);
    }
}
