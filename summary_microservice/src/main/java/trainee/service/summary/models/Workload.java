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

    public MonthlyWorkload getMonthlyWorkload(int year, String month) {
        double duration = 0.0;
        for (YearData yearData : workload) {
            if (yearData.getYear() == year) {
                YearData filteredYear = new YearData(yearData.getYear(), new ArrayList<>());
                for (MonthData monthData : yearData.getMonths()) {
                    if (getMonthName(monthData.getMonth()).equalsIgnoreCase(month)) {
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

    private String getMonthName(int month) {
        return switch (month) {
            case 1 -> "january";
            case 2 -> "february";
            case 3 -> "march";
            case 4 -> "april";
            case 5 -> "may";
            case 6 -> "june";
            case 7 -> "july";
            case 8 -> "august";
            case 9 -> "september";
            case 10 -> "october";
            case 11 -> "november";
            case 12 -> "december";
            default -> throw new IllegalArgumentException("Invalid month: " + month);
        };
    }

}
