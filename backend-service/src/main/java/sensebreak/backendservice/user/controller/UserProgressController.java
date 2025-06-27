package sensebreak.backendservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.repository.UserRepository;
import sensebreak.backendservice.user.service.UserProgressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class UserProgressController {

    private final UserProgressService progressService;
    private final UserRepository userRepository;

    @GetMapping("/streak")
    public ResponseEntity<Integer> getCurrentStreak(@RequestParam UUID userId) {
        int streak = progressService.getCurrentStreak(userId);
        return ResponseEntity.ok(streak);
    }

    @GetMapping("/longest-streak")
    public ResponseEntity<Integer> getLongestStreak(@RequestParam UUID userId) {
        int streak = progressService.getLongestStreak(userId);
        return ResponseEntity.ok(streak);
    }

    @GetMapping("/hearing-trainings")
    public ResponseEntity<Integer> getHearingTrainings(@RequestParam UUID userId) {
        int streak = progressService.getHearingTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    @GetMapping("/vision-trainings")
    public ResponseEntity<Integer> getVisionTrainings(@RequestParam UUID userId) {
        int streak = progressService.getVisionTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    @GetMapping("/total-trainings")
    public ResponseEntity<Integer> getTotalTrainings(@RequestParam UUID userId) {
        int streak = progressService.getTotalTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    @GetMapping("/relaxation-minutes")
    public ResponseEntity<Integer> getRelaxationMinutes(@RequestParam UUID userId) {
        int streak = progressService.getRelaxationMinutes(userId);
        return ResponseEntity.ok(streak);
    }

    @PostMapping("/relaxation-minutes")
    public ResponseEntity<Void> addRelaxationMinutes(
            @RequestParam UUID userId,
            @RequestParam int minutes
    ) {
        User user = userRepository.findById(userId).orElseThrow();
        progressService.addRelaxationMinutes(user, minutes);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finish-training")
    public ResponseEntity<Void> finishTraining(@RequestParam TrainingType type,
                                               @AuthenticationPrincipal User user) {
        progressService.onTrainingFinished(user, type);
        return ResponseEntity.ok().build();
    }
}
