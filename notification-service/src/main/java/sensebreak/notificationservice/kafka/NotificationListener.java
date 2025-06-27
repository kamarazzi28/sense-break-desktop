package sensebreak.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sensebreak.notificationservice.model.NotificationMessage;
import sensebreak.notificationservice.service.NotificationCacheService;
import sensebreak.notificationservice.service.NotificationLogService;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationLogService logService;
    private final NotificationCacheService cacheService;

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
