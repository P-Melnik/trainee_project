package trainee.GymApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trainee.GymApp.config.mapping.MappingCommand;
import trainee.GymApp.entity.Entity;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.entity.Trainer;
import trainee.GymApp.entity.Training;
import trainee.GymApp.entity.TrainingType;
import trainee.GymApp.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DataMapper {

    private final ObjectMapper objectMapper;

    private static final Map<String, MappingCommand> mapCommands = new HashMap<>();

    static {
        mapCommands.put("Trainee", (jsonElement, objectMapper) -> objectMapper.convertValue(jsonElement, Trainee.class));
        mapCommands.put("Trainer", (jsonElement, objectMapper) -> objectMapper.convertValue(jsonElement, Trainer.class));
        mapCommands.put("Training", (jsonElement, objectMapper) -> objectMapper.convertValue(jsonElement, Training.class));
        mapCommands.put("TrainingType", (jsonElement, objectMapper) -> objectMapper.convertValue(jsonElement, TrainingType.class));
        mapCommands.put("User", (jsonElement, objectMapper) -> objectMapper.convertValue(jsonElement, User.class));
    }

    @Autowired
    public DataMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Entity> mapJsonToObjects(String filePath) throws IOException {
        File file = new File(filePath);
        List<Entity> dataList = new ArrayList<>();

        List<?> jsonArray = objectMapper.readValue(file, List.class);
        log.debug("Mapped JSON to List<?>: " + jsonArray);

        for (Object jsonElement : jsonArray) {
            if (jsonElement instanceof Map<?, ?>) {
                String type = (String) ((java.util.Map<?, ?>) jsonElement).get("type");

                MappingCommand command = mapCommands.get(type);

                dataList.add(command.execute(jsonElement, objectMapper));
                log.debug("Mapped " + type + " from JSON: " + jsonElement);
            }
        }
        return dataList;
    }
}
