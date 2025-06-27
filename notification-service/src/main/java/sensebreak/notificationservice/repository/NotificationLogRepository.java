package sensebreak.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensebreak.notificationservice.entity.NotificationLog;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}
