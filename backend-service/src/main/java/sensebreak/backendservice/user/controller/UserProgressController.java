package sensebreak.backendservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.user.service.UserProgressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class UserProgressController {

    private final UserProgressService progressService;

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
}
