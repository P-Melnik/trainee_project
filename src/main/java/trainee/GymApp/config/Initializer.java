package trainee.GymApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trainee.GymApp.config.mapping.*;
import trainee.GymApp.storage.Storage;

import java.io.IOException;
import java.util.List;

@Component
public class Initializer {

    @Autowired
    private List<MappingCommand> mappingCommands;

    private final DataMapper dataMapper;
    private final Storage storage;

    @Autowired
    public Initializer(DataMapper dataMapper, Storage storage) {
        this.dataMapper = dataMapper;
        this.storage = storage;
    }

    @Value("${initializationData.path}")
    private String filePath;

    public void initializeData() {
        try {
            List<Object> dataList = dataMapper.mapJsonToObjects(filePath, getCommands());
            for (Object o : dataList) {
                storage.save(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MappingCommand> getCommands() {
        return List.of(
                new MapTraineeCommand(),
                new MapTrainerCommand(),
                new MapTrainingCommand(),
                new MapTrainingTypeCommand()
        );
    }
}
