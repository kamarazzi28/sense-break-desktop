package sensebreak.backendservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.user.dto.ReminderSettings;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;
import sensebreak.backendservice.user.repository.UserRepository;
import sensebreak.backendservice.user.service.UserProgressService;

import java.util.UUID;

/**
 * Controller that manages user training progress, streaks, relaxation time,
 * and reminder settings.
 */
@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class UserProgressController {

    private final UserProgressService progressService;
    private final UserRepository userRepository;
    private final UserProgressRepository progressRepository;

    /**
     * Returns the current streak of a user.
     */
    @GetMapping("/streak")
    public ResponseEntity<Integer> getCurrentStreak(@RequestParam UUID userId) {
        int streak = progressService.getCurrentStreak(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Returns the longest streak achieved by the user.
     */
    @GetMapping("/longest-streak")
    public ResponseEntity<Integer> getLongestStreak(@RequestParam UUID userId) {
        int streak = progressService.getLongestStreak(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Returns the total number of hearing trainings completed by the user.
     */
    @GetMapping("/hearing-trainings")
    public ResponseEntity<Integer> getHearingTrainings(@RequestParam UUID userId) {
        int streak = progressService.getHearingTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Returns the total number of vision trainings completed by the user.
     */
    @GetMapping("/vision-trainings")
    public ResponseEntity<Integer> getVisionTrainings(@RequestParam UUID userId) {
        int streak = progressService.getVisionTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Returns the total number of trainings (vision + hearing) completed by the user.
     */
    @GetMapping("/total-trainings")
    public ResponseEntity<Integer> getTotalTrainings(@RequestParam UUID userId) {
        int streak = progressService.getTotalTrainings(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Returns the number of relaxation minutes logged by the user.
     */
    @GetMapping("/relaxation-minutes")
    public ResponseEntity<Integer> getRelaxationMinutes(@RequestParam UUID userId) {
        int streak = progressService.getRelaxationMinutes(userId);
        return ResponseEntity.ok(streak);
    }

    /**
     * Adds a given number of relaxation minutes to the user's progress.
     */
    @PostMapping("/relaxation-minutes")
    public ResponseEntity<Void> addRelaxationMinutes(
            @RequestParam UUID userId,
            @RequestParam int minutes
    ) {
        User user = userRepository.findById(userId).orElseThrow();
        progressService.addRelaxationMinutes(user, minutes);
        return ResponseEntity.ok().build();
    }

    /**
     * Marks a training as finished and updates the user's progress accordingly.
     */
    @PostMapping("/finish-training")
    public ResponseEntity<Void> finishTraining(@RequestParam TrainingType type,
                                               @AuthenticationPrincipal User user) {
        progressService.onTrainingFinished(user, type);
        return ResponseEntity.ok().build();
    }

    /**
     * Returns the reminder settings of the user.
     */
    @GetMapping("/reminders")
    public ResponseEntity<ReminderSettings> getReminderSettings(@RequestParam UUID userId) {
        UserProgress progress = progressRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User progress not found"));

        ReminderSettings dto = new ReminderSettings();
        dto.setRemindersEnabled(progress.isRemindersEnabled());
        dto.setReminderIntervalMinutes(progress.getReminderIntervalMinutes());
        return ResponseEntity.ok(dto);
    }

    /**
     * Updates the user's reminder settings.
     */
    @PutMapping("/reminders")
    public ResponseEntity<Void> updateReminderSettings(
            @RequestParam UUID userId,
            @RequestBody ReminderSettings settings
    ) {
        progressService.updateReminderSettings(userId, settings.isRemindersEnabled(), settings.getReminderIntervalMinutes());
        return ResponseEntity.ok().build();
    }

    /**
     * Checks if a reminder should be sent to the user.
     */
    @GetMapping("/should-send-reminder")
    public ResponseEntity<Boolean> shouldSendReminder(@RequestParam UUID userId) {
        boolean shouldSend = progressService.shouldSendReminder(userId);
        return ResponseEntity.ok(shouldSend);
    }

    /**
     * Updates the timestamp of the last sent reminder.
     */
    @PostMapping("/reminder-sent")
    public ResponseEntity<Void> updateReminderSent(@RequestParam UUID userId) {
        progressService.updateLastReminderSent(userId);
        return ResponseEntity.ok().build();
    }
}
