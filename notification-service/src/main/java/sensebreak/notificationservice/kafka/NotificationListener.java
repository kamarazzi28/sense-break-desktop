package sensebreak.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sensebreak.notificationservice.model.NotificationMessage;

@Service
public class NotificationListener {

    @KafkaListener(topics = "notifications", groupId = "notification-group", containerFactory = "notificationKafkaListenerFactory")
    public void listen(NotificationMessage message) {
        System.out.println("Notification received: " + message.getContent() + " for user " + message.getUserId());
    }
}
