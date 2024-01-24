package trainee.GymApp.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.dao.TrainerRepo;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TrainerRepoImpl implements TrainerRepo {

    private Storage storage;

    @Autowired
    public TrainerRepoImpl(Storage storage) {
        this.storage = storage;
    }

    private final String TYPE = "trainer";

    @Override
    public void create(Trainer trainer) {
        log.debug("Creating trainer: " + trainer);
        storage.save(trainer);
    }

    @Override
    public Trainer findById(long id) {
        log.debug("Find trainer by id trainer:" + id);
        return (Trainer) storage.findById(TYPE, id);
    }

    @Override
    public List<Trainer> findAllByType() {
        log.debug("Fetching all Trainers");
        List<Object> objList = storage.findAllByType(TYPE);
        return objList.stream()
                .map(obj -> (Trainer) obj)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        log.debug("Updating Trainer: " + trainer);
        storage.update(trainer);
    }

}
