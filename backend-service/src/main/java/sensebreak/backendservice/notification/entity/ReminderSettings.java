package sensebreak.backendservice.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Entity representing reminder settings for a specific user.
 * Defines whether reminders are enabled, their interval, and active time window.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderSettings {

    /**
     * The unique identifier of the user associated with these reminder settings.
     */
    @Id
    private UUID userId;

    /**
     * Flag indicating whether reminders are enabled for the user.
     */
    private boolean enabled;

    /**
     * Interval in hours between reminders.
     */
    private int intervalHours;

    /**
     * The start time of the reminder window (inclusive).
     */
    private LocalTime startHour;

    /**
     * The end time of the reminder window (exclusive).
     */
    private LocalTime endHour;
}
