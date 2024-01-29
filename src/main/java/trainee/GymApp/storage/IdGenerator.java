package trainee.GymApp.storage;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private final AtomicLong generatedId = new AtomicLong(0);

    public long getGeneratedId() {
        return generatedId.incrementAndGet();
    }

}
