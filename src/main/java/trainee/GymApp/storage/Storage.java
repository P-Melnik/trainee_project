package trainee.GymApp.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Storage {

    private final Map<String, Object> storage = new ConcurrentHashMap<>();

    private static final Map<Object, String> mapInst = new HashMap<>();

    static {
        mapInst.put(Trainee.class, "trainee");
        mapInst.put(Trainer.class, "trainer");
        mapInst.put(Training.class, "training");
        mapInst.put(TrainingType.class, "trainingType");
        mapInst.put(User.class, "user");
    }

    IdGenerator traineeIdGenerator = new IdGenerator();
    IdGenerator trainerIdGenerator = new IdGenerator();
    IdGenerator traininigIdGenerator = new IdGenerator();
    IdGenerator traininigTypeIdGenerator = new IdGenerator();
    IdGenerator userIdGenerator = new IdGenerator();

    public Map<String, Object> getStorage() {
        return storage;
    }

    public void save(Object o) {
        String type = mapInst.get(o.getClass());
        if (type != null) {
            long id = getId(o);
            IdGenerator generator = getIdGenerator(o);
            if (id != 0) {
                assert generator != null;
                generator.getGeneratedId();
            } else {
                if (o instanceof Identifiable) {
                    assert generator != null;
                    setId((Identifiable) o, generator);
                }
            }
            String key = generateKey(type, getId(o));
            storage.put(key, o);
            log.info("Saved {} with key: {}", o.getClass().getSimpleName(), key);
        } else {
            log.warn("No entity type found for class: {}", o.getClass());
        }
    }

    public void update(Object o) {
        String type = mapInst.get(o.getClass());
        if (type != null) {
            long id = getId(o);
            String key = generateKey(type, id);

            if (storage.containsKey(key)) {
                storage.put(key, o);
                log.info("Updated {} with key: {}", o.getClass().getSimpleName(), key);
            } else {
                log.warn("{} not found with key: {}", o.getClass().getSimpleName(), key);
            }
        } else {
            log.warn("Unsupported object type for update");
        }
    }

    public <T extends Identifiable> T findById(Class<T> entityType, long id) {
        String type = mapInst.get(entityType);
        if (type != null) {
            String key = generateKey(type, id);
            return entityType.cast(storage.get(key));
        } else {
            log.warn("No entity type found for class: {}", entityType.getSimpleName());
            return null;
        }
    }

    public <T extends Identifiable> List<T> findAll(Class<T> entityType) {
        String type = mapInst.get(entityType);
        if (type != null) {
            return storage.values().stream()
                    .filter(entityType::isInstance)
                    .map(entityType::cast)
                    .collect(Collectors.toList());
        } else {
            log.warn("No entity type found for class: {}", entityType.getSimpleName());
            return List.of();
        }
    }

    public <T extends Identifiable> void delete(Class<T> entityType, long id) {
        String type = mapInst.get(entityType);
        if (type != null) {
            String key = generateKey(type, id);
            if (storage.containsKey(key)) {
                storage.remove(key);
                log.info("Deleted {} with key: {}", type, key);
            } else {
                log.warn("{} not found for deletion with key: {}", type, key);
            }
        } else {
            log.warn("No entity type found for class: {}", entityType.getSimpleName());
        }
    }


    private String generateKey(String type, long id) {
        return type + ":" + id;
    }

    private long getId(Object o) {
        if (o instanceof Identifiable) {
            return ((Identifiable) o).getId();
        }
        return 0L;
    }

    private void setId(Identifiable identifiable, IdGenerator idGenerator) {
        long id = idGenerator.getGeneratedId();
        identifiable.setId(id);
    }

    private IdGenerator getIdGenerator(Object o) {
        if (o instanceof Trainee) {
            return traineeIdGenerator;
        } else if (o instanceof Trainer) {
            return trainerIdGenerator;
        } else if (o instanceof Training) {
            return traininigIdGenerator;
        } else if (o instanceof TrainingType) {
            return traininigTypeIdGenerator;
        } else if (o instanceof User) {
            return userIdGenerator;
        }
        return null;
    }
}
