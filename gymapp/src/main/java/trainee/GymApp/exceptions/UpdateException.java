package trainee.GymApp.exceptions;

public class UpdateException extends RuntimeException {

    public UpdateException(String username) {
        super("Failed to update " + username);
    }
}
