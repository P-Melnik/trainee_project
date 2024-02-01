package trainee.GymApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainingType implements Entity {

    private long id;
    private String trainingTypeName;

}
