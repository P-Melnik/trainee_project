package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import trainee.GymApp.service.Generators;

public class GeneratorsTest {

    @Test
    void testGenerateUserId() {
        long userId1 = Generators.generateUserId();
        long userId2 = Generators.generateUserId();

        Assertions.assertNotEquals(userId1, userId2);
    }

    @Test
    void testGenerateUserName() {
        String firstName = "Will";
        String lastName = "Smith";

        String userName = Generators.generateUserName(firstName, lastName);

        Assertions.assertNotNull(userName);
        Assertions.assertEquals("Will.Smith", userName);
    }

    @Test
    void testGenerateUserNameWithDuplicate() {
        Generators.addUserName("Ivan.Ivanov");

        String firstName = "Ivan";
        String lastName = "Ivanov";

        String userName = Generators.generateUserName(firstName, lastName);

        Assertions.assertNotNull(userName);
        Assertions.assertEquals("Ivan.Ivanov1", userName);
    }

    @Test
    void testGeneratePassword() {
        String password1 = Generators.generatePassword();
        String password2 = Generators.generatePassword();

        Assertions.assertNotNull(password1);
        Assertions.assertNotNull(password2);
        Assertions.assertNotEquals(password1, password2);
    }
}
