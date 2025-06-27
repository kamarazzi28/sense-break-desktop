package sensebreak.backendservice.user.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.kafka.NotificationProducer;
import sensebreak.backendservice.model.NotificationMessage;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for managing user progress, training streaks,
 * and reminders. Handles logic for tracking activity and sending notifications.
 */
@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final UserProgressRepository progressRepository;
    private final NotificationProducer notificationProducer;

    /**
     * Updates the user's streak based on last active date.
     */
    private void updateStreakIfNeeded(UserProgress progress) {
        LocalDate today = LocalDate.now();
        LocalDate lastActive = progress.getLastActive();

        if (lastActive == null || lastActive.isBefore(today)) {
            if (lastActive != null && lastActive.equals(today.minusDays(1))) {
                progress.setStreakCurrent(progress.getStreakCurrent() + 1);
            } else {
                int previousStreak = progress.getStreakCurrent();
                progress.setStreakCurrent(1);

                if (previousStreak > 1) {
                    notificationProducer.sendNotification(new NotificationMessage(
                            progress.getUser().getId().toString(),
                            "STREAK_LOST",
                            "You missed your training yesterday. Streak has been reset."
                    ));
                }
            }

            if (progress.getStreakCurrent() > progress.getStreakLongest()) {
                progress.setStreakLongest(progress.getStreakCurrent());
            }

            progress.setLastActive(today);
        }
    }

    /**
     * Called when training starts. Initializes progress if not present and updates streak.
     */
    public void onTrainingStarted(User user) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

        updateStreakIfNeeded(progress);
        progressRepository.save(progress);
    }

    /**
     * Called when training is completed. Updates training counts and sends a notification.
     */
    public void onTrainingFinished(User user, TrainingType type) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));
        updateStreakIfNeeded(progress);

        if (type == TrainingType.VISION) {
            progress.setVisionTrainings(progress.getVisionTrainings() + 1);
        } else if (type == TrainingType.HEARING) {
            progress.setHearingTrainings(progress.getHearingTrainings() + 1);
        }
        progress.setTotalTrainings(progress.getTotalTrainings() + 1);

        progressRepository.save(progress);

        notificationProducer.sendNotification(new NotificationMessage(
                user.getId().toString(),
                "TRAINING_COMPLETED",
                "You successfully completed your training!"
        ));
    }

    /**
     * Returns the user's current streak.
     */
    public int getCurrentStreak(UUID userId) {
        return progressRepository.findById(userId)
                .map(p -> {
                    updateStreakIfNeeded(p);
                    return p.getStreakCurrent();
                })
                .orElse(0);
    }

    /**
     * Returns the user's longest streak.
     */
    public int getLongestStreak(UUID userId) {
        return progressRepository.findById(userId)
                .map(p -> {
                    updateStreakIfNeeded(p);
                    return p.getStreakLongest();
                })
                .orElse(0);
    }

    /**
     * Returns number of hearing trainings completed by the user.
     */
    public int getHearingTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getHearingTrainings)
                .orElse(0);
    }

    /**
     * Returns number of vision trainings completed by the user.
     */
    public int getVisionTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getVisionTrainings)
                .orElse(0);
    }

    /**
     * Returns total number of trainings completed by the user.
     */
    public int getTotalTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getTotalTrainings)
                .orElse(0);
    }

    /**
     * Adds given relaxation minutes to the user's progress.
     */
    public void addRelaxationMinutes(User user, int minutes) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

        progress.setRelaxationMinutes(progress.getRelaxationMinutes() + minutes);
        progressRepository.save(progress);
    }

    /**
     * Returns total relaxation minutes logged by the user.
     */
    public int getRelaxationMinutes(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getRelaxationMinutes)
                .orElse(0);
    }

    /**
     * Creates a new UserProgress entity for a new user.
     */
    private UserProgress createNewProgress(User user) {
        return UserProgress.builder()
                .user(user)
                .lastActive(null)
                .streakCurrent(0)
                .streakLongest(0)
                .relaxationMinutes(0)
                .build();
    }

    /**
     * Updates reminder settings for the user.
     */
    public void updateReminderSettings(UUID userId, boolean enabled, int intervalMinutes) {
        UserProgress progress = progressRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        progress.setRemindersEnabled(enabled);
        progress.setReminderIntervalMinutes(intervalMinutes);
        progressRepository.save(progress);
    }

    /**
     * Determines if a reminder should be sent to the user based on time interval.
     */
    public boolean shouldSendReminder(UUID userId) {
        return progressRepository.findById(userId)
                .map(progress -> {
                    if (!progress.isRemindersEnabled()) return false;

                    LocalDateTime lastSent = progress.getLastReminderSent();
                    if (lastSent == null) return true;

                    int interval = progress.getReminderIntervalMinutes();
                    return lastSent.plusMinutes(interval).isBefore(LocalDateTime.now());
                })
                .orElse(false);
    }

    /**
     * Updates the timestamp of the last reminder sent to the user.
     */
    public void updateLastReminderSent(UUID userId) {
        progressRepository.findById(userId).ifPresent(progress -> {
            progress.setLastReminderSent(LocalDateTime.now());
            progressRepository.save(progress);
        });
    }
}
