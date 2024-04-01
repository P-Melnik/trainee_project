package trainee.GymApp.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountRegisterMetric {

    private final Counter counter;

    @Autowired
    public CountRegisterMetric(MeterRegistry meterRegistry) {
        this.counter = Counter.builder("registrations_total")
                .description("total number of registrations").register(meterRegistry);
    }

    public void incrementCounter() {
        counter.increment();
    }
}
