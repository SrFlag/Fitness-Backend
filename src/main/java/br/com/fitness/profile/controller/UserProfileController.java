package br.com.fitness.profile.controller;

import br.com.fitness.profile.dto.CreateUserProfileRequest;
import br.com.fitness.profile.dto.UpdateUserProfileRequest;
import br.com.fitness.profile.dto.UserProfileResponse;
import br.com.fitness.profile.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse createProfile(
            @Valid @RequestBody CreateUserProfileRequest request,
            Authentication authentication
    ) {
        return userProfileService.createProfile(
                authentication.getName(),
                request
        );
    }

    @GetMapping
    public UserProfileResponse getProfile(
            Authentication authentication
    ) {
        return userProfileService.getProfile(
                authentication.getName()
        );
    }

    @PutMapping
    public UserProfileResponse updateProfile(
            @Valid @RequestBody UpdateUserProfileRequest request,
            Authentication authentication
    ) {
        return userProfileService.updateProfile(
                authentication.getName(),
                request
        );
    }
}