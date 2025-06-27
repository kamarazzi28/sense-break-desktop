package sensebreak.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sensebreak.notificationservice.model.NotificationMessage;
import sensebreak.notificationservice.service.NotificationCacheService;
import sensebreak.notificationservice.service.NotificationLogService;

/**
 * Kafka listener component that processes incoming notification messages.
 * On receiving a message, it logs the notification and caches it in Redis.
 */
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationLogService logService;
    private final NotificationCacheService cacheService;

    /**
     * Listens for messages on the "notifications" Kafka topic.
     * Processes each {@link NotificationMessage} by logging and caching it.
     *
     * @param message the notification message received from Kafka
     */
    @KafkaListener(
            topics = "notifications",
            groupId = "notification-group",
            containerFactory = "notificationKafkaListenerFactory"
    )
    public void handleNotification(NotificationMessage message) {
        System.out.println("GOT MESSAGE from Kafka!");
        System.out.println("Received notification: " + message);
        logService.save(message);
        cacheService.cacheNotification(message);
    }
}
