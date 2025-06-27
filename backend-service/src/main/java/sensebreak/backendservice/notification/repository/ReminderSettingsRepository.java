package sensebreak.backendservice.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensebreak.backendservice.notification.entity.ReminderSettings;

import java.util.UUID;

public interface ReminderSettingsRepository extends JpaRepository<ReminderSettings, UUID> {
}
