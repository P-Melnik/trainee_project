package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import trainee.GymApp.service.UserUtil;

public class UserUtilTest {

    @Test
    void testAddLogin() {
        UserUtil.generateLogin("john", "doe");
        Assertions.assertFalse(UserUtil.isLoginUnique("john.doe"));
    }

    @Test
    void testGenerateLogin() {
        String login = UserUtil.generateLogin("Ivan", "Ivanov");
        Assertions.assertTrue(UserUtil.isLoginUnique(login));
    }

    @Test
    void testGenerateLoginWithDuplicate() {
        UserUtil.generateLogin("Petr", "Petrov");

        String login = UserUtil.generateLogin("Petr", "Petrov");
        Assertions.assertFalse(UserUtil.isLoginUnique(login));
    }

    @Test
    void testGenerateLoginWithEmptyFirstName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> UserUtil.generateLogin("", "Doe"));
    }

    @Test
    void testGenerateLoginWithEmptyLastName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> UserUtil.generateLogin("John", ""));
    }

    @Test
    void testGeneratePassword() {
        String password = UserUtil.generatePassword();
        Assertions.assertNotNull(password);
        Assertions.assertEquals(10, password.length());
    }
}
