package sensebreak.backendservice.training.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sensebreak.backendservice.training.entity.TrainingSession;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.training.repository.TrainingSessionRepository;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.service.UserProgressService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingSessionRepository sessionRepository;
    private final UserProgressService progressService;

    @Transactional
    public TrainingSession startSession(User user, TrainingType type) {
        progressService.onTrainingStarted(user); // streak обновляется

        TrainingSession session = TrainingSession.builder()
                .user(user)
                .type(type)
                .startTime(Instant.now())
                .build();

        return sessionRepository.save(session);
    }

    @Transactional
    public void endSession(TrainingSession session) {
        session.setEndTime(Instant.now());
        sessionRepository.save(session);
    }
}
