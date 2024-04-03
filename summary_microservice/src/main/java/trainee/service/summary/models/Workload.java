package trainee.service.summary.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Workload {

    private String username;
    private String firstname;
    private String lastname;
    private boolean isActive;
    private List<YearData> workload = new ArrayList<>();

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

}
