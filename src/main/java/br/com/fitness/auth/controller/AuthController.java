package br.com.fitness.auth.controller;

import br.com.fitness.auth.dto.LoginRequest;
import br.com.fitness.auth.dto.LoginResponse;
import br.com.fitness.auth.dto.RegisterRequest;
import br.com.fitness.auth.dto.UserResponse;
import br.com.fitness.auth.service.AuthService;
import br.com.fitness.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);

        return UserResponse.from(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}