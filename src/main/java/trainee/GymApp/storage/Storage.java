package trainee.GymApp.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Storage {

    private Map<String, Object> storage = new ConcurrentHashMap<>();

    public Map<String, Object> getStorage() {
        return storage;
    }

    public void save(Object o) {
        if (o instanceof Trainee) {
            storage.put("trainee:" + ((Trainee) o).getId(), o);
            log.info("Saved Trainee with key: {}", "trainee:" + ((Trainee) o).getId());
        } else if (o instanceof Trainer) {
            storage.put("trainer:" + ((Trainer) o).getId(), o);
            log.info("Saved Trainer with key: {}", "trainer:" + ((Trainer) o).getId());
        } else if (o instanceof Training) {
            storage.put("training:" + ((Training) o).getId(), o);
            log.info("Saved Training with key: {}", "training:" + ((Training) o).getId());
        } else if (o instanceof TrainingType) {
            storage.put("trainingType:" + ((TrainingType) o).getId(), o);
            log.info("Saved TrainingType with key: {}", "trainingType:" + ((TrainingType) o).getId());
        }

    }

    public List<Object> findAllByType(String type) {
        List<Object> result = storage.entrySet().stream()
                .filter(entry -> entry.getKey().contains(type))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        log.info("Found {} objects of type: {}", result.size(), type);
        return result;
    }

    public Object findById(String type, long id) {
        String keyToFind = type + ":" + id;
        if (storage.containsKey(keyToFind)) {
            log.info("Found object with key: {}", keyToFind);
            return storage.get(keyToFind);
        } else {
            log.warn("Object not found with key: {}", keyToFind);
            return null;
        }
    }

    public void update(Object o) {
        if (o instanceof Trainee) {
            if (storage.containsKey("trainee:" + ((Trainee) o).getId())) {
                storage.put("trainee:" + ((Trainee) o).getId(), o);
                log.info("Updated Trainee with key: {}", "trainee:" + ((Trainee) o).getId());
            } else {
                log.warn("Trainee not found with key: {}", "trainee:" + ((Trainee) o).getId());
            }
        } else if (o instanceof Trainer) {
            if (storage.containsKey("trainer:" + ((Trainer) o).getId())) {
                storage.put("trainer:" + ((Trainer) o).getId(), o);
                log.info("Updated Trainer with key: {}", "trainer:" + ((Trainer) o).getId());
            } else {
                log.warn("Trainer not found with key: {}", "trainer:" + ((Trainer) o).getId());
            }
        } else if (o instanceof Training) {
            if (storage.containsKey("training:" + ((Training) o).getId())) {
                storage.put("training:" + ((Training) o).getId(), o);
                log.info("Updated Training with key: {}", "training:" + ((Training) o).getId());
            } else {
                log.warn("Training not found with key: {}", "training:" + ((Training) o).getId());
            }
        } else {
            log.warn("Unsupported object type for update");
        }
    }

    public void delete(String type, long id) {
        String keyToFind = type + ":" + id;
        if (storage.containsKey(keyToFind)) {
            storage.remove(keyToFind);
            log.info("Deleted object with key: {}", keyToFind);
        } else {
            log.warn("Object not found for deletion with key: {}", keyToFind);
        }
    }

}
