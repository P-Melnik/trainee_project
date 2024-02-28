package trainee.GymApp.exceptions;

public class ChangeStatusException extends RuntimeException {

    public ChangeStatusException(String username) {
        super("Failed to change status for " + username);
    }
}
