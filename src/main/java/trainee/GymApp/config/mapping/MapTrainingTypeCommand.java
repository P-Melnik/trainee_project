package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.TrainingType;

@Component
public class MapTrainingTypeCommand implements MappingCommand {

    public String getType() {
        return "trainingtype";
    }

    @Override
    public Object execute(Object jsonElement, ObjectMapper objectMapper) {
        return objectMapper.convertValue(jsonElement, TrainingType.class);
    }
}
