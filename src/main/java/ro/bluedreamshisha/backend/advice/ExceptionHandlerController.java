package ro.bluedreamshisha.backend.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaErrorResponse;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BlueDreamShishaException.class)
    private ResponseEntity<BlueDreamShishaErrorResponse> handleException(BlueDreamShishaException e) {
        return new ResponseEntity<>(
                e.getResponse(),
                e.getResponse().getStatus()
        );
    }
}
