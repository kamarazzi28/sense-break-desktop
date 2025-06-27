package sensebreak.backendservice.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate lastActive;

    private int streakCurrent;

    private int streakLongest;

    private int relaxationMinutes;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int visionTrainings;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int hearingTrainings;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int totalTrainings;

    @Column(nullable = false)
    private boolean remindersEnabled = false;

    @Column(nullable = false)
    private int reminderIntervalMinutes = 60;

    private java.time.LocalDateTime lastReminderSent;

    
}