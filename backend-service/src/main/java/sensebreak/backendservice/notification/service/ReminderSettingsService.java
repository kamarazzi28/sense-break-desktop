package sensebreak.backendservice.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.notification.entity.ReminderSettings;
import sensebreak.backendservice.notification.repository.ReminderSettingsRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderSettingsService {

    private final ReminderSettingsRepository repository;

    public Optional<ReminderSettings> get(UUID userId) {
        return repository.findById(userId);
    }

    public void save(ReminderSettings settings) {
        repository.save(settings);
    }
}
