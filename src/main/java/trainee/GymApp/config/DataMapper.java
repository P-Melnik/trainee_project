package trainee.GymApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DataMapper {

    private final ObjectMapper objectMapper;

    public DataMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Object> mapJsonToObjects(String filePath) throws IOException {
        File file = new File(filePath);
        List<Object> dataList = new ArrayList<>();

        List<?> jsonArray = objectMapper.readValue(file, List.class);
        log.debug("Mapped JSON to List<?>: " + jsonArray);

        for (Object jsonElement : jsonArray) {
            if (jsonElement instanceof Map<?, ?>) {
                String type = (String) ((java.util.Map<?, ?>) jsonElement).get("type");

                switch (type.toLowerCase()) {
                    case "trainee":
                        dataList.add(mapTrainee(jsonElement));
                        log.debug("Mapped trainee from JSON: " + jsonElement);
                        break;
                    case "trainer":
                        dataList.add(mapTrainer(jsonElement));
                        log.debug("Mapped trainer from JSON: " + jsonElement);
                        break;
                    case "trainingType":
                        dataList.add(mapTrainingType(jsonElement));
                        log.debug("Mapped trainingType from JSON: " + jsonElement);
                        break;
                    case "training":
                        dataList.add(mapTraining(jsonElement));
                        log.debug("Mapped training from JSON: " + jsonElement);
                        break;
                    default:
                        log.error("Error mapping JSON");
                        throw new IllegalArgumentException("Unknown type: " + type);
                }
            }
        }
        return dataList;
    }

    private Trainee mapTrainee(Object jsonElement) {
        return objectMapper.convertValue(jsonElement, Trainee.class);
    }

    private Trainer mapTrainer(Object jsonElement) {
        return objectMapper.convertValue(jsonElement, Trainer.class);
    }

    private Training mapTraining(Object jsonElement) {
        return objectMapper.convertValue(jsonElement, Training.class);
    }

    private TrainingType mapTrainingType(Object jsonElement) {
        return objectMapper.convertValue(jsonElement, TrainingType.class);
    }
}
