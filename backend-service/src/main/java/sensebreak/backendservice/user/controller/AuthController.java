package sensebreak.backendservice.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.auth.JwtService;
import sensebreak.backendservice.user.dto.UserLoginRequest;
import sensebreak.backendservice.user.dto.UserRegisterRequest;
import sensebreak.backendservice.user.dto.UserResponse;
import sensebreak.backendservice.user.entity.User;
import sensebreak.backendservice.user.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginRequest request) {
        User user = userService.validateLogin(request);

        String token = jwtService.generateToken(user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build());

        return ResponseEntity.ok(response);
    }


}
