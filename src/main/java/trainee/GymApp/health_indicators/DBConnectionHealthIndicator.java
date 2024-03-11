package trainee.GymApp.health_indicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component("db-connection")
public class DBConnectionHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Autowired
    public DBConnectionHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            return Health.up().withDetail("message", "Database connection is fine").build();
        } catch (SQLException e) {
            return Health.down().withDetail("message", "Failed to connect to DB").build();
        }
    }

}
