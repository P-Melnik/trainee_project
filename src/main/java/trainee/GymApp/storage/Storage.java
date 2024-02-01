package trainee.GymApp.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trainee.GymApp.config.DataMapper;
import trainee.GymApp.entity.Entity;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Storage {

    private final Map<String, Entity> storage = new ConcurrentHashMap<>();

    private static final Map<Object, String> mapInst = new HashMap<>();

    private static final Map<String, IdGenerator> mapGenerators = new HashMap<>();

    static {
        mapInst.put(Trainee.class, "trainee");
        mapInst.put(Trainer.class, "trainer");
        mapInst.put(Training.class, "training");
        mapInst.put(TrainingType.class, "trainingType");
        mapInst.put(User.class, "user");
        mapGenerators.put("trainee", new IdGenerator());
        mapGenerators.put("trainer", new IdGenerator());
        mapGenerators.put("training", new IdGenerator());
        mapGenerators.put("trainingType", new IdGenerator());
        mapGenerators.put("user", new IdGenerator());
    }

    @Autowired
    private DataMapper dataMapper;

    @Value("${initializationData.path}")
    private String filePath;

    public Map<String, Entity> getStorage() {
        return storage;
    }

    public void save(Entity o) {
        String prefix = mapInst.get(o.getClass());
        if (prefix != null) {
            long id = getId(o);
            IdGenerator generator = getIdGenerator(o);
            if (id != 0) {
                generator.getGeneratedId();
            } else {
                setId(o, generator);
            }
            String key = generateKey(prefix, getId(o));
            storage.put(key, o);
            log.info("Saved {} with key: {}", o.getClass().getSimpleName(), key);
        } else {
            log.error("No entity type found for class: {}", o.getClass());
        }
    }

    public void update(Entity o) {
        String prefix = mapInst.get(o.getClass());
        if (prefix != null) {
            long id = getId(o);
            String key = generateKey(prefix, id);
            if (storage.containsKey(key)) {
                storage.put(key, o);
                log.info("Updated {} with key: {}", o.getClass().getSimpleName(), key);
            } else {
                log.error("{} not found with key: {}", o.getClass().getSimpleName(), key);
            }
        } else {
            log.error("Unsupported object type for update");
        }
    }

    public <T extends Entity> T findById(Class<T> entityType, long id) {
        String prefix = mapInst.get(entityType);
        if (prefix != null) {
            String key = generateKey(prefix, id);
            return entityType.cast(storage.get(key));
        } else {
            log.error("No entity type found for class: {}", entityType.getSimpleName());
            return null;
        }
    }

    public <T extends Entity> List<T> findAll(Class<T> entityType) {
        String prefix = mapInst.get(entityType);
        if (prefix != null) {
            return storage.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(prefix))
                    .map(entry -> entityType.cast(entry.getValue()))
                    .collect(Collectors.toList());
        } else {
            log.error("No entity type found for class: {}", entityType.getSimpleName());
            return List.of();
        }
    }

    public <T extends Entity> void delete(Class<T> entityType, long id) {
        String prefix = mapInst.get(entityType);
        if (prefix != null) {
            String key = generateKey(prefix, id);
            if (storage.containsKey(key)) {
                storage.remove(key);
                log.info("Deleted {} with key: {}", prefix, key);
            } else {
                log.error("{} not found for deletion with key: {}", prefix, key);
            }
        } else {
            log.error("No entity type found for class: {}", entityType.getSimpleName());
        }
    }

    private String generateKey(String type, long id) {
        return type + ":" + id;
    }

    private long getId(Entity o) {
        return o.getId();
    }

    private void setId(Entity o, IdGenerator idGenerator) {
        long id = idGenerator.getGeneratedId();
        o.setId(id);
    }

    private IdGenerator getIdGenerator(Entity o) {
        String prefix = mapInst.get(o.getClass());
        return mapGenerators.get(prefix);
    }

    public void initializeData() {
        try {
            List<Entity> dataList = dataMapper.mapJsonToObjects(filePath);
            for (Entity o : dataList) {
                save(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
