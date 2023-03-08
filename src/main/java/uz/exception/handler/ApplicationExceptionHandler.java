package uz.exception.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.dto.response.ErrorMessageResponse;
import uz.exception.custom_ex.UserNotFoundException;

import java.util.Date;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResponse handleUserAuthenticationException (UserNotFoundException ex, WebRequest request){
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

}
