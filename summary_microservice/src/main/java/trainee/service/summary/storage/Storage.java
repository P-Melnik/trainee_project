package trainee.service.summary.storage;

import org.springframework.stereotype.Component;
import trainee.service.summary.exceptions.WorkloadCalculationException;
import trainee.service.summary.models.MonthData;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.YearData;

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
        storage.put(username, null);
        return Optional.of(storage.get(username));
    }

    public void add(WorkloadDTO workloadDTO) {
        int year = workloadDTO.getTrainingDate().getYear();
        int month = workloadDTO.getTrainingDate().getMonthValue();
        Workload workload = storage.computeIfAbsent(workloadDTO.getUserName(), key -> {
            Workload newWorkload = new Workload();
            newWorkload.setUsername(workloadDTO.getUserName());
            newWorkload.setFirstname(workloadDTO.getFirstName());
            newWorkload.setLastname(workloadDTO.getLastName());
            newWorkload.setActive(workloadDTO.isActive());
            return newWorkload;
        });
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
        monthData.setSummaryDuration(monthData.getSummaryDuration() + workloadDTO.getTrainingDuration());
    }

    public void delete(WorkloadDTO workloadDTO) {
        int year = workloadDTO.getTrainingDate().getYear();
        int month = workloadDTO.getTrainingDate().getMonthValue();
        Workload workload = storage.get(workloadDTO.getUserName());
        if (workload == null) {
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
        Optional<YearData> yearDataOptional = workload.getWorkload().stream()
                .filter(yd -> yd.getYear() == year)
                .findFirst();
        if (yearDataOptional.isEmpty()) {
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
        YearData yearData = yearDataOptional.get();
        Optional<MonthData> monthDataOptional = yearData.getMonths().stream()
                .filter(md -> md.getMonth() == month)
                .findFirst();
        if (monthDataOptional.isEmpty()) {
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
        MonthData monthData = monthDataOptional.get();
        if (workloadDTO.getTrainingDuration() > monthData.getSummaryDuration()) {
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
        monthData.setSummaryDuration(monthData.getSummaryDuration() - workloadDTO.getTrainingDuration());
        if (monthData.getSummaryDuration() == 0) {
            yearData.getMonths().remove(monthData);
        }
    }
}
