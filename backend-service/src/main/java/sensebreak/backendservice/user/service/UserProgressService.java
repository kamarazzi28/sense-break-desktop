package sensebreak.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final UserProgressRepository progressRepository;

    private void updateStreakIfNeeded(UserProgress progress) {
        LocalDate today = LocalDate.now();
        LocalDate lastActive = progress.getLastActive();

        if (lastActive == null || lastActive.isBefore(today)) {
            if (lastActive != null && lastActive.equals(today.minusDays(1))) {
                progress.setStreakCurrent(progress.getStreakCurrent() + 1);
            } else {
                progress.setStreakCurrent(1);
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

    }


    public void addRelaxationMinutes(User user, int minutes) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

        progress.setRelaxationMinutes(progress.getRelaxationMinutes() + minutes);
        progressRepository.save(progress);
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
}
