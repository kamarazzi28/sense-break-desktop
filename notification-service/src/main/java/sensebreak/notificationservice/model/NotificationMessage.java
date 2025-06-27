package sensebreak.notificationservice.model;

import lombok.*;

/**
 * Represents a notification message sent through Kafka.
 * Contains the user ID, type of the message, and content.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationMessage {

    /**
     * ID of the user the notification is intended for.
     */
    private String userId;

    /**
     * Type of the notification (e.g., "TRAINING_COMPLETED", "STREAK_LOST").
     */
    private String type;

    /**
     * The main content or text of the notification.
     */
    private String content;
}
