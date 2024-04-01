package trainee.GymApp.service.authentication;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message){
        super(message);
    }
}
