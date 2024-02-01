package trainee.GymApp.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.storage.Storage;

import java.util.List;

@Slf4j
@Repository
public class TrainerRepoImpl implements TrainerRepo {

    private final Storage storage;

    @Autowired
    public TrainerRepoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void update(Trainer trainer) {
        log.debug("Updating Trainer: " + trainer);
        storage.update(trainer);
    }

    @Override
    public void create(Trainer trainer) {
        log.debug("Creating trainer: " + trainer);
        storage.save(trainer);
    }

    @Override
    public Trainer findById(long id) {
        log.debug("Find trainer by id trainer:" + id);
        return storage.findById(Trainer.class, id);
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Fetching all Trainers");
        return storage.findAll(Trainer.class);
    }

    @Override
    public void delete(long id) {
        log.warn("Trying to delete trainer profile");
    }

}
