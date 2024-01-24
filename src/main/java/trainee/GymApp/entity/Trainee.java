package trainee.GymApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trainee extends User {

    private long id;
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;
    private String address;

}
