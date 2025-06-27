package sensebreak.backendservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sensebreak.backendservice.model.NotificationMessage;

/**
 * Service responsible for producing and sending {@link NotificationMessage} objects to the Kafka topic.
 */
@Service
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    /**
     * Constructs a NotificationProducer with the provided KafkaTemplate.
     *
     * @param kafkaTemplate Kafka template used to send messages
     */
    @Autowired
    public NotificationProducer(KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a notification message to the "notifications" Kafka topic.
     *
     * @param message the {@link NotificationMessage} to send
     */
    public void sendNotification(NotificationMessage message) {
        kafkaTemplate.send("notifications", message);
        System.out.println("Sent notification to Kafka: " + message.getContent());
    }
}
