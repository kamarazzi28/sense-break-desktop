package sensebreak.backendservice.training.entity;

import jakarta.persistence.*;
import lombok.*;
import sensebreak.backendservice.user.entity.User;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "training_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSession {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TrainingType type;

    private Instant startTime;
    private Instant endTime;
}
