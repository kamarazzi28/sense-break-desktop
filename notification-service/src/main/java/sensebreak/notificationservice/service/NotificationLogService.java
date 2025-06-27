package sensebreak.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sensebreak.notificationservice.entity.NotificationLog;
import sensebreak.notificationservice.model.NotificationMessage;
import sensebreak.notificationservice.repository.NotificationLogRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository repository;

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
