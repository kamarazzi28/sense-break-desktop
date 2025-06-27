package sensebreak.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sensebreak.backendservice.user.dto.UserLoginRequest;
import sensebreak.backendservice.user.dto.UserRegisterRequest;
import sensebreak.backendservice.user.dto.UserResponse;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;
import sensebreak.backendservice.user.repository.UserRepository;

import java.time.LocalDate;

/**
 * Service class for handling user registration and login logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user with encoded password and initializes their progress.
     *
     * @param request the registration request containing username, email, and password
     * @return the registered user's basic information
     */
    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepository.save(user);

        UserProgress progress = UserProgress.builder()
                .user(saved)
                .lastActive(LocalDate.now().minusDays(1))
                .streakCurrent(0)
                .streakLongest(0)
                .relaxationMinutes(0)
                .build();

        userProgressRepository.save(progress);

        return UserResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .build();
    }

    /**
     * Validates login credentials and returns the user if valid.
     *
     * @param request the login request with email and password
     * @return the authenticated user entity
     * @throws IllegalArgumentException if email or password is incorrect
     */
    public User validateLogin(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }

}
