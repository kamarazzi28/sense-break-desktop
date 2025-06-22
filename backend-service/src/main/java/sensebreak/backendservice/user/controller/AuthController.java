package sensebreak.backendservice.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sensebreak.backendservice.user.dto.UserLoginRequest;
import sensebreak.backendservice.user.dto.UserRegisterRequest;
import sensebreak.backendservice.user.dto.UserResponse;
import sensebreak.backendservice.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

}
