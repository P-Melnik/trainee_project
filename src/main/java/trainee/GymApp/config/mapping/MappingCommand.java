package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import trainee.GymApp.entity.Entity;

public interface MappingCommand {

    Entity execute(Object jsonElement, ObjectMapper objectMapper);
}
