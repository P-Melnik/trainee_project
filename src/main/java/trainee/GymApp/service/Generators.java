package trainee.GymApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class Generators {

    private static List<String> userNames = new ArrayList<>();

    private static AtomicLong generatedTraineeId = new AtomicLong(0);
    private static AtomicLong generatedTrainerId = new AtomicLong(0);
    private static AtomicLong generatedTrainingId = new AtomicLong(0);
    private static AtomicLong generatedUserId = new AtomicLong(0);
    private static AtomicLong generatedTrainingTypeId = new AtomicLong(0);
    private static AtomicLong generatedUserNameIdForDuplication= new AtomicLong(0);


    public static long generateUserId() {
        return generatedUserId.incrementAndGet();
    }

    public static long generateTraineeId() {
        return generatedTraineeId.incrementAndGet();
    }

    public static long generateTrainerId() {
        return generatedTrainerId.incrementAndGet();
    }

    public static long generateTrainingId() {
        return generatedTrainingId.incrementAndGet();
    }

    public static long generateTrainingTypeId() {
        return generatedTrainingTypeId.incrementAndGet();
    }

    public static void addUserName(String userName) {
        if (uniqueUserName(userName)) {
            userNames.add(userName);
            log.debug("Added new unique userName");
        }
    }

    public static String generateUserName(String firstName, String lastName) {
        if (firstName != null && !firstName.isEmpty()
                && lastName != null && !lastName.isEmpty()) {
            String userName = String.format("%s.%s", firstName, lastName);
            if (!uniqueUserName(userName)) {
                userName = userName + generatedUserNameIdForDuplication.incrementAndGet();
                userNames.add(userName);
            }
            return userName;
        } else {
            throw new IllegalArgumentException("First name and last name must not be null or empty");
        }
    }

    private static boolean uniqueUserName(String userName) {
        return !userNames.contains(userName);
    }

    public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 10;
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(chars.length());
            char randomChar = chars.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

}
