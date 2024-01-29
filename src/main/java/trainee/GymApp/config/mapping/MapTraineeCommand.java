package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Trainee;

@Component
public class MapTraineeCommand implements MappingCommand {

    public String getType() {
        return "trainee";
    }

    @Override
    public Object execute(Object jsonElement, ObjectMapper objectMapper) {
        return objectMapper.convertValue(jsonElement, Trainee.class);
    }
}
