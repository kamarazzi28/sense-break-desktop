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

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final UserProgressService progressService;

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


    @Transactional
    public void endSession(UUID id) {
        TrainingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found"));

        session.setEndTime(Instant.now());
        sessionRepository.save(session);
    }
    @Transactional(readOnly = true)
    public List<TrainingSession> getUserHistory(User user) {
        return sessionRepository.findAllByUserId(user.getId());
    }

}
