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
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.ModelMapper;
import ro.bluedreamshisha.backend.model.auth.Account;
import ro.bluedreamshisha.backend.model.auth.Role;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.model.auth.UserDetails;
import ro.bluedreamshisha.backend.model.check.FieldError;
import ro.bluedreamshisha.backend.model.request.auth.LoginRequest;
import ro.bluedreamshisha.backend.model.request.auth.RegisterRequest;
import ro.bluedreamshisha.backend.model.response.AuthResponse;
import ro.bluedreamshisha.backend.security.JwtUtils;
import ro.bluedreamshisha.backend.service.AccountService;
import ro.bluedreamshisha.backend.service.UserService;
import ro.bluedreamshisha.backend.validator.LoginRequestValidator;
import ro.bluedreamshisha.backend.validator.RegisterRequestValidator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final AccountService accountService;
    private final LoginRequestValidator loginRequestValidator;
    private final RegisterRequestValidator registerRequestValidator;
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) throws BlueDreamShishaException {
        loginRequestValidator.checkAndThrow(request);
        return generateToken(
                request.getUsername(),
                request.getPassword()
        );
    }

    public AuthResponse register(RegisterRequest request) throws BlueDreamShishaException {
        registerRequestValidator.checkAndThrow(request);

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
        return loginRequestValidator.check(request);
    }

    public List<FieldError> check(RegisterRequest request) {
        return registerRequestValidator.check(request);
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
