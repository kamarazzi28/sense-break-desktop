package sensebreak.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a log entry for a received notification.
 * Stores the user ID, notification type, content, and timestamp of receipt.
 */
@Entity
@Table(name = "notification_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationLog {

    /**
     * Unique identifier for the log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * UUID of the user associated with the notification.
     */
    private UUID userId;

    /**
     * Type of the notification (e.g. TRAINING_COMPLETED, STREAK_LOST).
     */
    private String type;

    /**
     * Content of the notification message.
     */
    private String message;

    /**
     * Timestamp when the notification was received.
     */
    private LocalDateTime receivedAt;
}
