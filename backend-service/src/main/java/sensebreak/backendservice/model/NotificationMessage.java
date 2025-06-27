package sensebreak.backendservice.model;

import lombok.Getter;
import lombok.Setter;

/**
 * A model representing a notification message to be sent via Kafka.
 * Contains the user ID, type of the notification, and its content.
 */
@Setter
@Getter
public class NotificationMessage {

    /**
     * ID of the user who will receive the notification.
     */
    private String userId;

    /**
     * Type of the notification (e.g., "TRAINING_COMPLETED", "REMINDER").
     */
    private String type;

    /**
     * Content of the notification message.
     */
    private String content;

    /**
     * Constructs a new {@link NotificationMessage} with the given user ID, type, and content.
     *
     * @param userId  the ID of the user
     * @param type    the type of the notification
     * @param content the content of the notification
     */
    public NotificationMessage(String userId, String type, String content) {
        this.userId = userId;
        this.type = type;
        this.content = content;
    }
}
