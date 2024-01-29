package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import trainee.GymApp.entity.Trainer;

@Component
public class MapTrainerCommand implements MappingCommand {

    public String getType() {
        return "trainer";
    }

    @Override
    public Object execute(Object jsonElement, ObjectMapper objectMapper) {
        return objectMapper.convertValue(jsonElement, Trainer.class);
    }
}
