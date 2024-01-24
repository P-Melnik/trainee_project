package trainee.GymApp.dao.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@NoArgsConstructor
public class TraineeRepoImpl implements trainee.GymApp.dao.TraineeRepo {

    private Storage storage;

    @Autowired
    public TraineeRepoImpl(Storage storage) {
        this.storage = storage;
    }

    private final String TYPE = "trainee";

    @Override
    public void create(Trainee trainee) {
        log.debug("Creating trainee: " + trainee);
        storage.save(trainee);
    }

    @Override
    public Trainee findById(long id) {
        log.debug("Find trainee by id trainee:" + id);
        return (Trainee) storage.findById(TYPE, id);
    }

    @Override
    public List<Trainee> findAllByType() {
        log.debug("Fetching all Trainees");
        List<Object> objList = storage.findAllByType(TYPE);
        return objList.stream()
                .map(obj -> (Trainee) obj)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        log.debug("Updating Trainee: " + trainee);
        storage.update(trainee);
    }

    @Override
    public void deleteById(long id) {
        log.debug("Deleting Trainee by id trainee:" + id);
        storage.delete(TYPE, id);
    }
}
