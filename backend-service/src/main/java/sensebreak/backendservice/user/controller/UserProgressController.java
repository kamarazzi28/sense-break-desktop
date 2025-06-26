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
}
