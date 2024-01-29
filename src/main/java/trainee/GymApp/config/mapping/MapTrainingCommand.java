package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Training;

@Component
public class MapTrainingCommand implements MappingCommand {

    public String getType() {
        return "training";
    }

    @Override
    public Object execute(Object jsonElement, ObjectMapper objectMapper) {
        return objectMapper.convertValue(jsonElement, Training.class);
    }
}
