package ro.bluedreamshisha.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.auth.Role;
import ro.bluedreamshisha.backend.model.auth.User;

import java.util.Set;

@Service
public class AuthorizationService {

    private final Set<Role> ADMINISTRATIVE_ROLES = Set.of(Role.MANAGER, Role.MASTER);

    public void checkUserHasAdministrativeRole(User user) throws BlueDreamShishaException {
        if (!ADMINISTRATIVE_ROLES.contains(user.getRole())) {
            throw new BlueDreamShishaException(
                    "Requester user[%s] does not have access to this resource",
                    ErrorCode.ACCESS_DENIED,
                    HttpStatus.FORBIDDEN
            );
        }
    }
}
