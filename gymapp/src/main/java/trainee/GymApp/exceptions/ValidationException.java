package trainee.GymApp.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Fields should not be blank");
    }
}
