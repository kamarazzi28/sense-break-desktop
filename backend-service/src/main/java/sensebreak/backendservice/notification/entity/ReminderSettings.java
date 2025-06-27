package sensebreak.backendservice.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderSettings {

    @Id
    private UUID userId;

    private boolean enabled;

    private int intervalHours;

    private LocalTime startHour;
    private LocalTime endHour;
}
