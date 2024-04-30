package trainee.service.summary.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "trainer_workload")
@CompoundIndexes({@CompoundIndex(name = "firstName_lastName_index", def = "{'firstName' : 1, 'lastName' : 1}")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Workload {

    @MongoId
    private String id;
    @Indexed(unique = true)
    @NotBlank
    private String username;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private boolean isActive;
    private List<YearData> workload;

    public Workload(String username, String firstname, String lastname, boolean isActive, List<YearData> workload) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isActive = isActive;
        this.workload = workload;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Workload{");
        sb.append("username='").append(username).append('\'');
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", isActive=").append(isActive);
        sb.append(", workload=[");
        for (YearData yearData : workload) {
            sb.append("{ year=").append(yearData.getYear()).append(", months=[");
            for (MonthData monthData : yearData.getMonths()) {
                sb.append("{ month=").append(monthData.getMonth())
                        .append(", summaryDuration=").append(monthData.getSummaryDuration())
                        .append("}, ");
            }
            sb.append("]}, ");
        }
        sb.append("]}");
        return sb.toString();
    }

    public MonthlyWorkload getMonthlyWorkload(int year, String month) {
        double duration = 0.0;
        for (YearData yearData : workload) {
            if (yearData.getYear() == year) {
                YearData filteredYear = new YearData(yearData.getYear(), new ArrayList<>());
                for (MonthData monthData : yearData.getMonths()) {
                    if (MonthNameUtil.getMonthName(monthData.getMonth()).equalsIgnoreCase(month)) {
                        duration = monthData.getSummaryDuration();
                        break;
                    }
                }
                break;
            }
        }
        return new MonthlyWorkload(this.username, this.firstname, this.lastname,
                year, month, duration);
    }

}
