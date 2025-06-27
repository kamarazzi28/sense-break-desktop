package sensebreak.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.kafka.NotificationProducer;
import sensebreak.backendservice.model.NotificationMessage;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final UserProgressRepository progressRepository;
    private final NotificationProducer notificationProducer;

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

    public void onTrainingStarted(User user) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

        updateStreakIfNeeded(progress);
        progressRepository.save(progress);
    }

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

    public int getCurrentStreak(UUID userId) {
        return progressRepository.findById(userId)
                .map(p -> {
                    updateStreakIfNeeded(p);
                    return p.getStreakCurrent();
                })
                .orElse(0);
    }

    public int getLongestStreak(UUID userId) {
        return progressRepository.findById(userId)
                .map(p -> {
                    updateStreakIfNeeded(p);
                    return p.getStreakLongest();
                })
                .orElse(0);
    }

    public int getHearingTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getHearingTrainings)
                .orElse(0);
    }

    public int getVisionTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getVisionTrainings)
                .orElse(0);
    }

    public int getTotalTrainings(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getTotalTrainings)
                .orElse(0);
    }

    public void addRelaxationMinutes(User user, int minutes) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

        progress.setRelaxationMinutes(progress.getRelaxationMinutes() + minutes);
        progressRepository.save(progress);
    }

    public int getRelaxationMinutes(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getRelaxationMinutes)
                .orElse(0);
    }

    private UserProgress createNewProgress(User user) {
        return UserProgress.builder()
                .user(user)
                .lastActive(null)
                .streakCurrent(0)
                .streakLongest(0)
                .relaxationMinutes(0)
                .build();
    }

    public void updateReminderSettings(UUID userId, boolean enabled, int intervalMinutes) {
        UserProgress progress = progressRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        progress.setRemindersEnabled(enabled);
        progress.setReminderIntervalMinutes(intervalMinutes);
        progressRepository.save(progress);
    }

    public boolean isReminderEnabled(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::isRemindersEnabled)
                .orElse(false);
    }

    public int getReminderInterval(UUID userId) {
        return progressRepository.findById(userId)
                .map(UserProgress::getReminderIntervalMinutes)
                .orElse(60);
    }

}
