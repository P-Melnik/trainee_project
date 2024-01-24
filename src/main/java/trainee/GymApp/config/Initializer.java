package trainee.GymApp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.service.Generators;
import trainee.GymApp.storage.Storage;

import java.io.IOException;
import java.util.List;

@Component
public class Initializer {

    @Value("${initializationData.path}")
    private String filePath;

    private final DataMapper dataMapper;
    private final Storage storage;

    @Autowired
    public Initializer(DataMapper dataMapper, Storage storage) {
        this.dataMapper = dataMapper;
        this.storage = storage;
    }

    @PostConstruct
    public void initializeData() {
        try {
            List<Object> dataList = dataMapper.mapJsonToObjects(filePath);
            for (Object o : dataList) {
                if (o instanceof Trainee) {
                    Generators.generateUserId();
                    Generators.generateTraineeId();
                } else if (o instanceof Trainer) {
                    Generators.generateUserId();
                    Generators.generateTrainerId();
                } else if (o instanceof Training) {
                    Generators.generateTrainingId();
                } else if (o instanceof TrainingType) {
                    Generators.generateTrainingTypeId();
                }
                storage.save(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
