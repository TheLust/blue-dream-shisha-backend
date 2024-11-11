package ro.bluedreamshisha.backend.validator;

import org.springframework.http.HttpStatus;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.check.FieldError;

import java.util.List;

public interface Validator<T> {

    List<FieldError> check(T target);

    default void checkAndThrow(T target) throws BlueDreamShishaException {
        List<FieldError> fieldErrors = check(target);
        if (!fieldErrors.isEmpty()) {
            throw new BlueDreamShishaException(
                    "The request has check errors. Check api should be called first to resolve field errors",
                    ErrorCode.CHECK_ERROR,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
