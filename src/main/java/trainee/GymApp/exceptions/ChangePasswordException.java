package trainee.GymApp.exceptions;

public class ChangePasswordException extends RuntimeException {

    public ChangePasswordException(String username) {
        super("Failed to change password " + username);
    }
}
