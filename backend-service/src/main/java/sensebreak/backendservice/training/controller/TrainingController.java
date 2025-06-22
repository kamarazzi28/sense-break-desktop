package sensebreak.backendservice.training.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.training.entity.TrainingSession;
import sensebreak.backendservice.training.entity.TrainingType;
import sensebreak.backendservice.training.service.TrainingService;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.repository.UserRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final UserRepository userRepository;

    // ⏱️ Старт новой тренировки
    @PostMapping("/start")
    public ResponseEntity<TrainingSession> start(
            @RequestParam("type") TrainingType type
    ) {
        // 🔥 Вместо new User() — загружаем из базы
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111"); // ← временный id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TrainingSession session = trainingService.startSession(user, type);
        return ResponseEntity.ok(session);
    }

    // ✅ Завершить тренировку
    @PostMapping("/end/{id}")
    public ResponseEntity<Void> end(@PathVariable UUID id) {
        TrainingSession session = new TrainingSession();
        session.setId(id);
        trainingService.endSession(session);
        return ResponseEntity.ok().build();
    }
}
