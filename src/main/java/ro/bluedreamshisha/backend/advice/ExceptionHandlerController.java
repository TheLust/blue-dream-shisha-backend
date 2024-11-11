package ro.bluedreamshisha.backend.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaErrorResponse;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BlueDreamShishaException.class)
    private ResponseEntity<BlueDreamShishaErrorResponse> handleException(BlueDreamShishaException e) {
        return new ResponseEntity<>(
                e.getResponse(),
                HttpStatusCode.valueOf(e.getResponse().getStatus())
        );
    }
}
