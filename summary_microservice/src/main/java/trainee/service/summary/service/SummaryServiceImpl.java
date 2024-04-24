package trainee.service.summary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainee.service.summary.dao.CustomRepo;
import trainee.service.summary.dao.Repo;
import trainee.service.summary.exceptions.WorkloadCalculationException;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.MonthData;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.YearData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private Repo repo;

    @Autowired
    private CustomRepo customRepo;

    @Override
    public void manage(WorkloadDTO workloadDTO) {
        if (ActionType.ADD.equals(workloadDTO.getActionType())) {
            saveTraining(workloadDTO);
        } else {
            deleteTraining(workloadDTO);
        }
    }

    @Override
    public Optional<Workload> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    private void saveTraining(WorkloadDTO workloadDTO) {
        try {
            Workload workload = extractWorkload(workloadDTO);
            findByUsername(workloadDTO.getUserName()).ifPresentOrElse(training ->
                    updateExistingTraining(new UpdateParamsDto(training, workloadDTO.getTrainingDate().getYear(),
                            workloadDTO.getTrainingDate().getMonthValue(), workloadDTO.getTrainingDuration())), () -> save(workload));
        } catch (Exception e) {
            log.error("Error while saving training", e);
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
    }

    private void deleteTraining(WorkloadDTO workloadDTO) {
        try {
            Workload workload = extractWorkload(workloadDTO);
            findByUsername(workloadDTO.getUserName()).ifPresent(training -> {
                var yearData = getYearData(workloadDTO);
                var monthDataOptional = getMonthData(yearData, workloadDTO.getTrainingDate().getMonthValue());
                monthDataOptional.ifPresent(monthData -> {
                    double newDuration = monthData.getSummaryDuration() - workloadDTO.getTrainingDuration();
                    if (newDuration < 0) {
                        throw new WorkloadCalculationException(workloadDTO.getUserName());
                    }
                    if (newDuration == 0) {
                        deleteMonthAndYearIfNeeded(new DtoToDelete(workload, yearData, monthData));
                    } else {
                        updateDuration(new UpdateParamsDto(workload, yearData.getYear(), monthData.getMonth(), newDuration));
                    }
                });
            });
        } catch (Exception e) {
            log.error("Error while deleting training", e);
            throw new WorkloadCalculationException(workloadDTO.getUserName());
        }
    }

    private void save(Workload workload) {
        try {
            repo.save(workload);
        } catch (Error e) {
            log.error("Failed to save training", e);
            throw new WorkloadCalculationException(workload.getUsername());
        }
    }

    public void updateDuration(UpdateParamsDto updateParamsDto) {
        customRepo.updateDuration(updateParamsDto);
    }

    private void updateExistingTraining(UpdateParamsDto data) {
        var yearDataOptional = data.training.getWorkload().stream().filter(yearData ->
                yearData.getYear() == data.yearData).findFirst();
        yearDataOptional.ifPresentOrElse(yearData -> updateYear(new DtoToCreateMonth(data.training, yearData, data.monthData(), data.duration)),
                () -> createYear(new UpdateParamsDto(data.training, data.yearData, data.monthData(), data.duration)));
    }

    private void deleteMonth(DtoToDelete dtoToDelete) {
        try {
            customRepo.deleteMonth(dtoToDelete.training.getId(), dtoToDelete.yearData.getYear(), dtoToDelete.monthData.getMonth());
        } catch (Error e) {
            log.error("Failed to delete month", e);
            throw new WorkloadCalculationException(dtoToDelete.training.getUsername());
        }
    }

    private void deleteMonthAndYearIfNeeded(DtoToDelete dtoToDelete) {
        deleteMonth(dtoToDelete);
        if (dtoToDelete.training.getWorkload().size() == 1) {
            deleteYear(dtoToDelete.training, dtoToDelete.yearData);
        }
    }

    private void deleteYear(Workload workload, YearData yearData) {
        try {
            customRepo.deleteYear(workload.getId(), yearData.getYear());
        } catch (Exception e) {
            log.error("Failed to delete month", e);
            throw new WorkloadCalculationException(workload.getUsername());
        }
    }

    private static YearData getYearData(WorkloadDTO workloadDTO) {
        return new YearData(workloadDTO.getTrainingDate().getYear(), List.of(
                new MonthData(workloadDTO.getTrainingDate().getMonthValue(), workloadDTO.getTrainingDuration())));
    }

    private Optional<MonthData> getMonthData(YearData yearData, int month) {
        return yearData.getMonths().stream().filter(monthData -> monthData.getMonth() == month).findFirst();
    }

    private void createYear(UpdateParamsDto updateParamsDto) {
        try {
            var newYear = YearData.builder().year(updateParamsDto.yearData).build();
            var newMonth = MonthData.builder().month(updateParamsDto.monthData)
                    .summaryDuration(updateParamsDto.duration()).build();
            if (newYear.getMonths() == null) {
                newYear.setMonths(new ArrayList<>());
            }
            newYear.getMonths().add(newMonth);
            customRepo.createYear(updateParamsDto.training.getUsername(), newYear);
        } catch (Exception e) {
            log.error("Failed to create a year", e);
            throw new WorkloadCalculationException(updateParamsDto.training.getUsername());
        }
    }

    private void updateYear(DtoToCreateMonth dtoToCreateMonth) {
        try {
            var monthDataOptional = dtoToCreateMonth.yearData.getMonths().stream()
                    .filter(monthData -> monthData.getMonth() == dtoToCreateMonth.month)
                    .findFirst();
            monthDataOptional.ifPresentOrElse(monthData -> updateMonth(new UpdateDataDto(dtoToCreateMonth.workload, dtoToCreateMonth.yearData, monthData, dtoToCreateMonth.duration)),
                    () -> createMonth(new DtoToCreateMonth(dtoToCreateMonth.workload, dtoToCreateMonth.yearData, dtoToCreateMonth.month(), dtoToCreateMonth.duration)));
        } catch (Exception e) {
            log.error("Failed to update year", e);
            throw new WorkloadCalculationException(dtoToCreateMonth.workload.getUsername());
        }
    }

    private void createMonth(DtoToCreateMonth dtoToCreateMonth) {
        try {
            var newMonth = MonthData.builder().month(dtoToCreateMonth.month).summaryDuration(dtoToCreateMonth.duration).build();
            dtoToCreateMonth.yearData.getMonths().add(newMonth);
            customRepo.createYear(dtoToCreateMonth.workload.getUsername(), dtoToCreateMonth.yearData);
        } catch (Exception e) {
            log.error("Failed to create a month", e);
            throw new WorkloadCalculationException(dtoToCreateMonth.workload.getUsername());
        }
    }

    private void updateMonth(UpdateDataDto updateDataDto) {
        var newDuration = updateDataDto.monthData.getSummaryDuration() + updateDataDto.duration;
        updateDuration(new UpdateParamsDto(updateDataDto.workload, updateDataDto.yearData.getYear(),
                updateDataDto.monthData.getMonth(), newDuration));
    }


    private Workload extractWorkload(WorkloadDTO workloadDTO) {
        return new Workload(workloadDTO.getUserName(), workloadDTO.getFirstName(),
                workloadDTO.getLastName(), workloadDTO.isActive(), List.of(getYearData(workloadDTO)));
    }

    public record UpdateDataDto(Workload workload,
                                YearData yearData,
                                MonthData monthData,
                                double duration) {
    }

    public record UpdateParamsDto(Workload training,
                                  int yearData,
                                  int monthData,
                                  double duration) {
    }

    public record DtoToDelete(Workload training,
                              YearData yearData,
                              MonthData monthData) {
    }

    public record DtoToCreateMonth(Workload workload,
                                   YearData yearData,
                                   int month,
                                   double duration) {
    }

}
