package trainee.GymApp.health_indicators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("memory")
public class MemoryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        if (freeMemory > totalMemory * 0.2) {
            return Health.up().withDetail("message", "Free memory: " + freeMemory + "is in acceptable range."
            + " Total memory: " + totalMemory + ". Max memory: " + maxMemory + ".").build();
        } else {
            return Health.down().withDetail("message", "Critical memory usage. " + "Total memory: " + totalMemory
            + ". Free memory: " + freeMemory + ". Max memory: " + maxMemory + ".").build();
        }
    }
}
