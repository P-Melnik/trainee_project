package trainee.GymApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trainee.GymApp.config.mapping.MappingCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DataMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public DataMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Object> mapJsonToObjects(String filePath, List<MappingCommand> commands) throws IOException {
        File file = new File(filePath);
        List<Object> dataList = new ArrayList<>();

        List<?> jsonArray = objectMapper.readValue(file, List.class);
        log.debug("Mapped JSON to List<?>: " + jsonArray);

        for (Object jsonElement : jsonArray) {
            if (jsonElement instanceof Map<?, ?>) {
                String type = (String) ((java.util.Map<?, ?>) jsonElement).get("type");

                MappingCommand command = commands.stream()
                        .filter(c -> c.getType().equalsIgnoreCase(type))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + type));

                dataList.add(command.execute(jsonElement, objectMapper));
                log.debug("Mapped " + type + " from JSON: " + jsonElement);
            }
        }
        return dataList;
    }
}
