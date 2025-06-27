package sensebreak.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.notificationservice.entity.NotificationLog;
import sensebreak.notificationservice.model.NotificationMessage;
import sensebreak.notificationservice.repository.NotificationLogRepository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service responsible for logging received notification messages to the database.
 */
@Service
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository repository;

    /**
     * Saves a notification message to the database by converting it into a {@link NotificationLog} entity.
     *
     * @param message the notification message to be logged
     */
    public void save(NotificationMessage message) {
        NotificationLog log = NotificationLog.builder()
                .userId(UUID.fromString(message.getUserId()))
                .type(message.getType())
                .message(message.getContent())
                .receivedAt(LocalDateTime.now())
                .build();

        repository.save(log);
    }
}
