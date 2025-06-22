package sensebreak.backendservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.entity.UserProgress;
import sensebreak.backendservice.user.repository.UserProgressRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private final UserProgressRepository progressRepository;

    @Transactional
    public void onTrainingStarted(User user) {
        UserProgress progress = progressRepository.findById(user.getId())
                .orElseGet(() -> createNewProgress(user));

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
