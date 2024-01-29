package trainee.GymApp.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.entity.Training;
import trainee.GymApp.storage.Storage;

import java.util.List;

@Slf4j
@Repository
public class TrainingRepoImpl implements TrainingRepo {

    private final Storage storage;

    @Autowired
    public TrainingRepoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void create(Training training) {
        log.debug("Creating training: " + training);
        storage.save(training);
    }

    @Override
    public Training findById(long id) {
        log.debug("Find training by id training:" + id);
        return storage.findById(Training.class, id);
    }

    @Override
    public List<Training> findAll() {
        log.debug("Fetching all Trainings");
        return storage.findAll(Training.class);
    }

    @Override
    public void update(Training training) {
        log.warn("trying to update training");
    }

    @Override
    public void delete(long id) {
        log.warn("trying to delete training");
    }
}
