package ro.bluedreamshisha.backend.util;

import ro.bluedreamshisha.backend.model.check.FieldError;

import java.util.List;

public class CheckUtils {

    public static boolean notNull(List<FieldError> fieldErrors, Object target, String jsonPath, String errorCode) {
        if (target == null) {
            fieldErrors.add(new FieldError(jsonPath, errorCode));
            return false;
        }

        return true;
    }

    public static boolean notBlank(List<FieldError> fieldErrors, String target, String jsonPath, String errorCode) {
        if (target == null || target.isBlank()) {
            fieldErrors.add(new FieldError(jsonPath, errorCode));
            return false;
        }

        return true;
    }
}
