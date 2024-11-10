package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.facade.AuthFacade;
import ro.bluedreamshisha.backend.model.check.FieldError;
import ro.bluedreamshisha.backend.model.request.LoginRequest;
import ro.bluedreamshisha.backend.model.request.RegisterRequest;
import ro.bluedreamshisha.backend.model.response.AuthResponse;

import java.util.List;

@RestController
@RequestMapping("${api.url.base}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    @Operation(operationId = "login")
    public AuthResponse login(@RequestBody LoginRequest request) throws BlueDreamShishaException {
        return authFacade.login(request);
    }

    @PostMapping("/login/check")
    @Operation(operationId = "check-login-request")
    public List<FieldError> checkLoginRequest(@RequestBody LoginRequest request) {
        return authFacade.check(request);
    }

    @PostMapping("/register")
    @Operation(operationId = "register")
    public AuthResponse register(@RequestBody RegisterRequest request) throws BlueDreamShishaException {
        return authFacade.register(request);
    }

    @PostMapping("/register/check")
    @Operation(operationId = "check-register-request")
    public List<FieldError> checkRegisterRequest(@RequestBody RegisterRequest request) {
        return authFacade.check(request);
    }
}
