package trainee.GymApp.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainingRepo;
import trainee.GymApp.entity.Training;
import trainee.GymApp.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TrainingRepoImpl implements TrainingRepo {

    private Storage storage;
    @Autowired
    public TrainingRepoImpl(Storage storage) {
        this.storage = storage;
    }

    private final String TYPE = "training";

    @Override
    public void create(Training training) {
        log.debug("Creating training: " + training);
        storage.save(training);
    }

    @Override
    public Training findById(long id) {
        log.debug("Find training by id training:" + id);
        return (Training) storage.findById(TYPE, id);
    }

    @Override
    public List<Training> findAllByType() {
        log.debug("Fetching all Trainings");
        List<Object> objList = storage.findAllByType(TYPE);
        return objList.stream()
                .map(obj -> (Training) obj)
                .collect(Collectors.toList());
    }
}
