package sensebreak.backendservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sensebreak.backendservice.user.entity.UserProgress;

import java.util.UUID;

public interface UserProgressRepository extends JpaRepository<UserProgress, UUID> {
}