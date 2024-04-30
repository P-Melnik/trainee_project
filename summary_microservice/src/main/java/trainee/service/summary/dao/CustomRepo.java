package trainee.service.summary.dao;

import trainee.service.summary.models.YearData;
import trainee.service.summary.service.SummaryServiceImpl.UpdateParamsDto;

public interface CustomRepo {

    void createYear(String username, YearData yearData);

    void createNewMonth(String username, YearData yearData);

    void deleteMonth(String trainingId, int year, int month);

    void deleteYear(String trainingId, int year);

    void updateDuration(UpdateParamsDto dto);
}
