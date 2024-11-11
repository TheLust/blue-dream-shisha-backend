package ro.bluedreamshisha.backend.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.constant.FieldErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.model.check.FieldError;
import ro.bluedreamshisha.backend.model.request.LoginRequest;
import ro.bluedreamshisha.backend.service.UserService;
import ro.bluedreamshisha.backend.util.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginRequestValidator implements Validator<LoginRequest> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<FieldError> check(LoginRequest target) {
        List<FieldError> fieldErrors = new ArrayList<>();

        boolean usernameNotBlank = CheckUtils.notBlank(
                fieldErrors,
                target.getUsername(),
                "username",
                FieldErrorCode.LOGIN_USERNAME_BLANK
        );
        boolean passwordNotBlank = CheckUtils.notBlank(
                fieldErrors,
                target.getPassword(),
                "password",
                FieldErrorCode.LOGIN_PASSWORD_BLANK
        );

        if (usernameNotBlank) {
            Optional<User> user = userService.findByUsername(target.getUsername());
            if (user.isEmpty()) {
                fieldErrors.add(new FieldError("username", FieldErrorCode.LOGIN_USERNAME_NOT_EXISTS));
            } else {
                if (passwordNotBlank && !passwordEncoder.matches(target.getPassword(), user.get().getPassword())) {
                    fieldErrors.add(new FieldError("password", FieldErrorCode.LOGIN_PASSWORD_INCORRECT));
                }
            }

        }

        return fieldErrors;
    }

    @Override
    public void checkAndThrow(LoginRequest target) throws BlueDreamShishaException {
        Validator.super.checkAndThrow(target);
    }
}
