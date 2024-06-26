package trainee.GymApp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class UserUtil {

    private static final Set<String> logins = new HashSet<>();

    private static final AtomicLong loginPostfixId = new AtomicLong(0);

    public static boolean isLoginUnique(String userName) {
        return !logins.contains(userName);
    }

    public static String generateLogin(String firstName, String lastName) {
        if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)) {
            String login = String.format("%s.%s", firstName, lastName);
            if (!isLoginUnique(login)) {
                login = login + loginPostfixId.incrementAndGet();
            }
            logins.add(login);
            return login;
        } else {
            throw new IllegalArgumentException("First name and last name must not be null or empty");
        }
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
