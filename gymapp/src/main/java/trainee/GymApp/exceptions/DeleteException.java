package trainee.GymApp.exceptions;

public class DeleteException extends RuntimeException {

    public DeleteException(String username) {
        super("Failed to delete user: " + username);
    }

    public DeleteException(long id) {
        super("Failed to delete training: " + id);
    }
}
