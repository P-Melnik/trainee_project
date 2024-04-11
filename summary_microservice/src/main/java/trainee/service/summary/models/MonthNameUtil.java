package trainee.service.summary.models;

import java.util.Collections;
import java.util.Map;

public class MonthNameUtil {

    private static final Map<Integer, String> monthNames;

    static {
        Map<Integer, String> names = Map.ofEntries(
                Map.entry(1, "January"),
                Map.entry(2, "February"),
                Map.entry(3, "March"),
                Map.entry(4, "April"),
                Map.entry(5, "May"),
                Map.entry(6, "June"),
                Map.entry(7, "July"),
                Map.entry(8, "August"),
                Map.entry(9, "September"),
                Map.entry(10, "October"),
                Map.entry(11, "November"),
                Map.entry(12, "December")
        );
        monthNames = Collections.unmodifiableMap(names);
    }

    public static String getMonthName(int month) {
        String name = monthNames.get(month);
        if (name == null) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
        return name;
    }


}
