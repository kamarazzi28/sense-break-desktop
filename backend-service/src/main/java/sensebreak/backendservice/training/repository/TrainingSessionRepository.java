package sensebreak.backendservice.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensebreak.backendservice.training.entity.TrainingSession;

import java.util.List;
import java.util.UUID;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, UUID> {

    List<TrainingSession> findAllByUserId(UUID userId);
}
