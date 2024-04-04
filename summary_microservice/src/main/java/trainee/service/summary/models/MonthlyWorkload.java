package trainee.service.summary.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonthlyWorkload {

    private String username;
    private String firstname;
    private String lastname;
    private int year;
    private String month;
    private double workloadDuration;
}
