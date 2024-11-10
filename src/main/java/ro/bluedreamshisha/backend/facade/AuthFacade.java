package ro.bluedreamshisha.backend.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.constant.FieldErrorCode;
import ro.bluedreamshisha.backend.constant.Rule;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.ModelMapper;
import ro.bluedreamshisha.backend.model.auth.Account;
import ro.bluedreamshisha.backend.model.auth.Role;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.model.auth.UserDetails;
import ro.bluedreamshisha.backend.model.check.FieldError;
import ro.bluedreamshisha.backend.model.request.LoginRequest;
import ro.bluedreamshisha.backend.model.request.RegisterRequest;
import ro.bluedreamshisha.backend.model.response.AuthResponse;
import ro.bluedreamshisha.backend.security.JwtUtils;
import ro.bluedreamshisha.backend.service.AccountService;
import ro.bluedreamshisha.backend.service.UserService;
import ro.bluedreamshisha.backend.util.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final AccountService accountService;
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) throws BlueDreamShishaException {
        List<FieldError> fieldErrors = check(request);
        if (!fieldErrors.isEmpty()) {
            throw new BlueDreamShishaException(
                    ErrorCode.CHECK_ERROR,
                    HttpStatus.BAD_REQUEST
            );
        }

        return generateToken(
                request.getUsername(),
                request.getPassword()
        );
    }

    public AuthResponse register(RegisterRequest request) throws BlueDreamShishaException {
        List<FieldError> fieldErrors = check(request);
        if (!fieldErrors.isEmpty()) {
            throw new BlueDreamShishaException(
                    ErrorCode.CHECK_ERROR,
                    HttpStatus.BAD_REQUEST
            );
        }

        Account account = mapper.toEntity(request);
        User user = account.getUser();
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userService.insert(user);
        account.setUser(user);

        accountService.insert(account);

        return generateToken(
                request.getUsername(),
                request.getPassword()
        );
    }

    public List<FieldError> check(LoginRequest request) {
        List<FieldError> fieldErrors = new ArrayList<>();

        boolean usernameNotBlank = CheckUtils.notBlank(
                fieldErrors,
                request.getUsername(),
                "username",
                FieldErrorCode.LOGIN_USERNAME_BLANK
        );
        boolean passwordNotBlank = CheckUtils.notBlank(
                fieldErrors,
                request.getPassword(),
                "password",
                FieldErrorCode.LOGIN_PASSWORD_BLANK
        );

        if (usernameNotBlank) {
            Optional<User> user = userService.findByUsername(request.getUsername());
            if (user.isEmpty()) {
                fieldErrors.add(new FieldError("username", FieldErrorCode.LOGIN_USERNAME_NOT_EXISTS));
            } else {
                if (passwordNotBlank && !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                    fieldErrors.add(new FieldError("password", FieldErrorCode.LOGIN_PASSWORD_INCORRECT));
                }
            }

        }

        return fieldErrors;
    }

    public List<FieldError> check(RegisterRequest request) {
        List<FieldError> fieldErrors = new ArrayList<>();

        boolean usernameNotBlank = CheckUtils.notBlank(
                fieldErrors,
                request.getUsername(),
                "username",
                FieldErrorCode.REGISTER_USERNAME_BLANK
        );
        boolean passwordNotBlank = CheckUtils.notBlank(
                fieldErrors,
                request.getPassword(),
                "password",
                FieldErrorCode.REGISTER_PASSWORD_BLANK
        );

        if (usernameNotBlank) {
            Optional<User> user = userService.findByUsername(request.getUsername());
            if (user.isPresent()) {
                fieldErrors.add(new FieldError("username", FieldErrorCode.REGISTER_USERNAME_EXISTS));
            }
        }

        if (passwordNotBlank && !checkPassword(request.getPassword())) {
            fieldErrors.add(new FieldError("password", FieldErrorCode.REGISTER_PASSWORD_WEEK));
        }

        return fieldErrors;
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

    private AuthResponse generateToken(String username, String password) throws BlueDreamShishaException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );

        try {
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return new AuthResponse(
                    jwtUtils.generateToken(userDetails.getUser())
            );

        } catch (BadCredentialsException e) {
            throw new BlueDreamShishaException(
                    "Incorrect username or/and password",
                    ErrorCode.BAD_CREDENTIALS,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
