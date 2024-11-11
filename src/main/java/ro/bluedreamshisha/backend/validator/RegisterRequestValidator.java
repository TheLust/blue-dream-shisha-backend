package ro.bluedreamshisha.backend.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.constant.FieldErrorCode;
import ro.bluedreamshisha.backend.constant.Rule;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.model.check.FieldError;
import ro.bluedreamshisha.backend.model.request.RegisterRequest;
import ro.bluedreamshisha.backend.service.UserService;
import ro.bluedreamshisha.backend.util.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterRequestValidator implements Validator<RegisterRequest> {

    private final UserService userService;

    @Override
    public List<FieldError> check(RegisterRequest target) {
        List<FieldError> fieldErrors = new ArrayList<>();

        boolean usernameNotBlank = CheckUtils.notBlank(
                fieldErrors,
                target.getUsername(),
                "username",
                FieldErrorCode.REGISTER_USERNAME_BLANK
        );
        boolean minUsernameLength = CheckUtils.minLength(
                fieldErrors,
                target.getUsername(),
                Rule.usernameLength.getLeft(),
                "username",
                FieldErrorCode.REGISTER_USERNAME_MIN_LENGTH
        );
        boolean maxUsernameLength = CheckUtils.maxLength(
                fieldErrors,
                target.getUsername(),
                Rule.usernameLength.getRight(),
                "username",
                FieldErrorCode.REGISTER_USERNAME_MAX_LENGTH
        );
        boolean passwordNotBlank = CheckUtils.notBlank(
                fieldErrors,
                target.getPassword(),
                "password",
                FieldErrorCode.REGISTER_PASSWORD_BLANK
        );

        if (usernameNotBlank && minUsernameLength && maxUsernameLength) {
            Optional<User> user = userService.findByUsername(target.getUsername());
            if (user.isPresent()) {
                fieldErrors.add(new FieldError("username", FieldErrorCode.REGISTER_USERNAME_EXISTS));
            }
        }

        if (passwordNotBlank && !checkPassword(target.getPassword())) {
            fieldErrors.add(new FieldError("password", FieldErrorCode.REGISTER_PASSWORD_WEEK));
        }

        return fieldErrors;
    }


    @Override
    public void checkAndThrow(RegisterRequest target) throws BlueDreamShishaException {
        Validator.super.checkAndThrow(target);
    }

    private boolean checkPassword(String password) {
        Integer min = Rule.passwordLength.getLeft();
        Integer max = Rule.passwordLength.getRight();
        int length = password.length();

        if (min > length || max < length) {
            return false;
        }

        return password.matches(Rule.passwordPattern);
    }
}
