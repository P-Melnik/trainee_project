package trainee.GymApp.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountProcessedTrainingsMetric {

    private final Counter counter;

    @Autowired
    public CountProcessedTrainingsMetric(MeterRegistry meterRegistry) {
        this.counter = Counter.builder("processed_trainings_total")
                .description("total number of trainings").register(meterRegistry);
    }

    public void incrementCounter() {
        counter.increment();
    }

}
