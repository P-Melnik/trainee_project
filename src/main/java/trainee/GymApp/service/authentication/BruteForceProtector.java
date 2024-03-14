package trainee.GymApp.service.authentication;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
// todo -> is this bean ot just class
public class BruteForceProtector {

    private final Map<String, Integer> failedLoginAttempts = new HashMap<>();
    private final Map<String, Instant> lockoutExpiryTimes = new HashMap<>();

    private static final int MAX_FAILED_ATTEMPTS = 3;
    public static final Duration LOCKOUT_DURATION = Duration.ofMinutes(5);

    public boolean isUserLocked(String username) {
        Instant lockoutExpiryTime = lockoutExpiryTimes.get(username);
        return lockoutExpiryTime != null && lockoutExpiryTime.isAfter(Instant.now());
    }

    public void recordFailedLoginAttempt(String username) {
        failedLoginAttempts.put(username, failedLoginAttempts.getOrDefault(username, 0) + 1);
        if (failedLoginAttempts.get(username) >= MAX_FAILED_ATTEMPTS) {
            lockoutExpiryTimes.put(username, Instant.now().plus(LOCKOUT_DURATION));
        }
    }

    public void resetFailedLoginAttempts(String username) {
        failedLoginAttempts.remove(username);
        lockoutExpiryTimes.remove(username);
    }
}
