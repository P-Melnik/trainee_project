package trainee.GymApp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import trainee.GymApp.exceptions.ChangePasswordException;
import trainee.GymApp.exceptions.ChangeStatusException;
import trainee.GymApp.exceptions.DeleteException;
import trainee.GymApp.exceptions.UpdateException;
import trainee.GymApp.exceptions.UserNotFoundException;
import trainee.GymApp.exceptions.ValidationException;
import trainee.GymApp.service.authentication.UnauthorizedAccessException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleUserNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlerValidationException(ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handlerUnauthorizedAccessException(UnauthorizedAccessException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlerChangePasswordException(ChangePasswordException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ChangeStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlerChangeStatusException(ChangeStatusException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlerDeleteException(DeleteException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handlerUpdateException(UpdateException e) {
        return e.getMessage();
    }

}
