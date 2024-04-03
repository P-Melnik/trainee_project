package trainee.service.summary.storage;

import org.springframework.stereotype.Component;
import trainee.service.summary.models.MonthData;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.YearData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {

    private final Map<String, Workload> storage = new ConcurrentHashMap<>();

    public Map<String, Workload> getStorage() {
        return this.storage;
    }

    public Optional<Workload> getWorkloadByUsername(String username) {
        return Optional.ofNullable(storage.get(username));
    }

    public Optional<Workload> create(String username) {
        storage.put(username, new Workload());
        return Optional.of(storage.get(username));
    }

    public void add(String username, LocalDate localDate, double duration) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        Workload workload = storage.computeIfAbsent(username, key -> new Workload());
        YearData yearData = workload.getWorkload().stream()
                .filter(yd -> yd.getYear() == year).findFirst()
                .orElseGet(() -> {YearData newYd = new YearData(year, new ArrayList<>());
                workload.getWorkload().add(newYd);
                return newYd;});
        MonthData monthData = yearData.getMonths().stream()
                .filter(md -> md.getMonth() == month).findFirst()
                .orElseGet(() -> {
                    MonthData newMd = new MonthData(month, 0.0);
                    yearData.getMonths().add(newMd);
                    return newMd;
                });
        monthData.setSummaryDuration(monthData.getSummaryDuration() + duration);
    }

    public void delete(String username, LocalDate localDate, double duration) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        Workload workload = storage.get(username);
        if (workload == null) {
            return;
        }
        Optional<YearData> yearDataOptional = workload.getWorkload().stream()
                .filter(yd -> yd.getYear() == year)
                .findFirst();
        if (yearDataOptional.isEmpty()) {
            return;
        }
        YearData yearData = yearDataOptional.get();
        Optional<MonthData> monthDataOptional = yearData.getMonths().stream()
                .filter(md -> md.getMonth() == month)
                .findFirst();
        if (monthDataOptional.isEmpty()) {
            return;
        }
        MonthData monthData = monthDataOptional.get();
        if (duration > monthData.getSummaryDuration()) {
            return;
        }
        monthData.setSummaryDuration(monthData.getSummaryDuration() - duration);
        if (monthData.getSummaryDuration() == 0) {
            yearData.getMonths().remove(monthData);
        }
    }
}
