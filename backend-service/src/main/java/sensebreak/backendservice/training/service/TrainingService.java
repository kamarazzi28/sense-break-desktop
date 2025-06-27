package sensebreak.backendservice.training.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sensebreak.backendservice.training.entity.TrainingSession;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.training.repository.TrainingSessionRepository;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.repository.UserRepository;
import sensebreak.backendservice.user.service.UserProgressService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Service for handling training session logic, including start, end, and history retrieval.
 */
@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final UserProgressService progressService;

    /**
     * Starts a new training session for the given user and training type.
     *
     * @param user the user who starts the training
     * @param type the type of training
     * @return the saved training session
     */
    @Transactional
    public TrainingSession startSession(User user, TrainingType type) {
        User attachedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found: " + user.getId()));

        progressService.onTrainingStarted(attachedUser);

        TrainingSession session = TrainingSession.builder()
                .user(attachedUser)
                .type(type)
                .startTime(Instant.now())
                .build();

        return sessionRepository.save(session);
    }

    /**
     * Ends a training session by setting the end time to now.
     *
     * @param id the ID of the training session
     */
    @Transactional
    public void endSession(UUID id) {
        TrainingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found"));

        session.setEndTime(Instant.now());
        sessionRepository.save(session);
    }

    /**
     * Retrieves the history of all training sessions for a given user.
     *
     * @param user the user whose training history is requested
     * @return list of training sessions
     */
    @Transactional(readOnly = true)
    public List<TrainingSession> getUserHistory(User user) {
        return sessionRepository.findAllByUserId(user.getId());
    }
}
