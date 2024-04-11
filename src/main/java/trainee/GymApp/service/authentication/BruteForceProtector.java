package trainee.GymApp.service.authentication;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class BruteForceProtector {

    private static final Map<String, Integer> failedLoginAttempts = new HashMap<>();
    private static final Map<String, Instant> lockoutExpiryTimes = new HashMap<>();

    private static final int MAX_FAILED_ATTEMPTS = 3;
    public static final Duration LOCKOUT_DURATION = Duration.ofMinutes(5);

    public boolean isUserLocked(String username) {
        Instant lockoutExpiryTime = lockoutExpiryTimes.get(username);
        return lockoutExpiryTime != null && lockoutExpiryTime.isAfter(Instant.now());
    }

    public int recordFailedLoginAttempt(String username) {
        failedLoginAttempts.put(username, failedLoginAttempts.getOrDefault(username, 0) + 1);
        if (failedLoginAttempts.get(username) >= MAX_FAILED_ATTEMPTS) {
            lockoutExpiryTimes.put(username, Instant.now().plus(LOCKOUT_DURATION));
        }
        return failedLoginAttempts.get(username);
    }

    public String getTime(String username) {
        Instant lockOut = lockoutExpiryTimes.get(username);
        Instant currentTime = Instant.now();
        if (lockOut != null) {
            Duration duration = Duration.between(currentTime, lockOut);
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
            return String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds);
        } else {
            return null;
        }
    }

    public void resetFailedLoginAttempts(String username) {
        failedLoginAttempts.remove(username);
        lockoutExpiryTimes.remove(username);
    }

    public int getMaxAttempts() {
        return MAX_FAILED_ATTEMPTS;
    }

}
