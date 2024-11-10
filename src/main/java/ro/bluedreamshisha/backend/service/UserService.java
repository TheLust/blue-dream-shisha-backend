package ro.bluedreamshisha.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User insert(User user) {
        return userRepository.save(user);
    }
}
