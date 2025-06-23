package sensebreak.backendservice.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.training.entity.TrainingSession;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.training.repository.TrainingSessionRepository;
import sensebreak.backendservice.training.service.TrainingService;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.repository.UserRepository;
import sensebreak.backendservice.user.service.UserProgressService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final UserRepository userRepository;
    private final TrainingSessionRepository sessionRepository;
    private final UserProgressService progressService;


    @PostMapping("/start")
    public ResponseEntity<TrainingSession> start(
            @RequestParam("type") TrainingType type,
            @AuthenticationPrincipal User user
    ) {
        TrainingSession session = trainingService.startSession(user, type);
        return ResponseEntity.ok(session);
    }

//TODO: не сохранять тренировки в бд если они меньше чем таймер

    @PostMapping("/end/{id}")
    public ResponseEntity<Void> end(@PathVariable("id") UUID id) {
        TrainingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training not found"));

        session.setEndTime(Instant.now());
        sessionRepository.save(session);

        progressService.onTrainingFinished(session.getUser(), session.getType());

        return ResponseEntity.ok().build();
    }
    //TODO: relaxation minutes

    @PostMapping("/relax")
    public ResponseEntity<Void> addRelaxationMinutes(
            @RequestParam int minutes,
            @AuthenticationPrincipal User user
    ) {
        progressService.addRelaxationMinutes(user, minutes);
        return ResponseEntity.ok().build();
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // email from JWT token
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @GetMapping("/history")
    public ResponseEntity<List<TrainingSession>> history() {
        User user = getCurrentUser();
        List<TrainingSession> sessions = trainingService.getUserHistory(user);
        return ResponseEntity.ok(sessions);
    }

}
