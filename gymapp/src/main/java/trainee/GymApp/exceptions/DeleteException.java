package trainee.GymApp.exceptions;

public class DeleteException extends RuntimeException {

    public DeleteException(String username) {
        super("Failed to delete " + username);
    }
}
