package trainee.service.summary.dao;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.YearData;
import trainee.service.summary.service.SummaryServiceImpl.UpdateParamsDto;

@Repository
@RequiredArgsConstructor
public class CustomRepoImpl implements CustomRepo {

    private final MongoTemplate mongoTemplate;

    public static final String USERNAME = "username";
    public static final String YEARS = "yearsList";
    public static final String YEARS_YEAR = "yearsList.year";
    public static final String YEARS_AND_MONTHS = "yearsList.$.monthsList";
    public static final String MONTH_VALUE = "yearsList.monthList.monthValue";

    @Override
    public void createYear(String username, YearData yearData) {
        var query = Query.query(Criteria.where(USERNAME).is(username));
        var update = new Update().addToSet(YEARS, yearData);
        mongoTemplate.updateFirst(query, update, Workload.class);
    }

    @Override
    public void createNewMonth(String username, YearData yearData) {
        var query = Query.query(Criteria.where(USERNAME).is(username).and(YEARS_YEAR).is(yearData.getYear()));
        var update = new Update().addToSet(YEARS_AND_MONTHS, new Document("$each", yearData.getMonths()));
        mongoTemplate.updateFirst(query, update, Workload.class);
    }

    @Override
    public void deleteMonth(String trainingId, int year, int month) {
        var query = new Query(Criteria.where("_id").is(trainingId).and(YEARS_YEAR).is(year)
                .and(MONTH_VALUE).is(month));
        var update = new Update().pull(YEARS_AND_MONTHS, new BasicDBObject("monthValue", month));
        mongoTemplate.updateFirst(query, update, Workload.class);
    }

    @Override
    public void deleteYear(String trainingId, int year) {
        var query = new Query(Criteria.where("_id").is(trainingId).and(YEARS_YEAR).is(year));
        var update = new Update().pull(YEARS, new BasicDBObject("year", year));
        mongoTemplate.updateFirst(query, update, Workload.class);
    }

    @Override
    public void updateDuration(UpdateParamsDto dto) {
        var query = new Query(Criteria.where(USERNAME).is(dto.training().getUsername())
                .and(YEARS_YEAR).is(dto.yearData())
                .and(MONTH_VALUE).is(dto.monthData()));
        var update = new Update().set("yearsList.$.monthsList.$[elem].trainingsSummaryDuration", dto.duration())
                .filterArray(Criteria.where("elem.monthValue").is(dto.monthData()));
        mongoTemplate.updateFirst(query, update, Workload.class);
    }
}
