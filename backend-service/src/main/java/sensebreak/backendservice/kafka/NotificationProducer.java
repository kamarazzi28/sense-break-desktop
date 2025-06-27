package sensebreak.backendservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.model.NotificationMessage;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationMessage message) {
        kafkaTemplate.send("notifications", message);
        System.out.println("Sent notification to Kafka: " + message.getContent());
    }
}
