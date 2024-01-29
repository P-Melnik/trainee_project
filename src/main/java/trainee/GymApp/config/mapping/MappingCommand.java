package trainee.GymApp.config.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface MappingCommand {

    String getType();

    Object execute(Object jsonElement, ObjectMapper objectMapper);
}
