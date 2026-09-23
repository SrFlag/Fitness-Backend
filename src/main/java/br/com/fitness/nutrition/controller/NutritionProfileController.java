package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.CreateNutritionProfileRequest;
import br.com.fitness.nutrition.dto.NutritionProfileResponse;
import br.com.fitness.nutrition.dto.UpdateNutritionProfileRequest;
import br.com.fitness.nutrition.model.NutritionProfile;
import br.com.fitness.nutrition.service.NutritionProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition-profile")
public class NutritionProfileController {

    private final NutritionProfileService nutritionProfileService;

    public NutritionProfileController(
            NutritionProfileService nutritionProfileService
    ) {
        this.nutritionProfileService = nutritionProfileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NutritionProfileResponse create(
            Authentication authentication,
            @Valid @RequestBody CreateNutritionProfileRequest request
    ) {

        String email = authentication.getName();

        NutritionProfile profile =
                nutritionProfileService.create(
                        email,
                        request
                );

        return toResponse(profile);
    }

    @GetMapping
    public NutritionProfileResponse findAuthenticatedUserProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        NutritionProfile profile =
                nutritionProfileService
                        .findByAuthenticatedUser(email);

        return toResponse(profile);
    }

    @PutMapping
    public NutritionProfileResponse update(
            Authentication authentication,
            @Valid @RequestBody UpdateNutritionProfileRequest request
    ) {

        String email = authentication.getName();

        NutritionProfile profile =
                nutritionProfileService.update(
                        email,
                        request
                );

        return toResponse(profile);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication
    ) {

        String email = authentication.getName();

        nutritionProfileService.delete(email);
    }

    private NutritionProfileResponse toResponse(
            NutritionProfile profile
    ) {

        return new NutritionProfileResponse(
                profile.getId(),
                profile.getNutritionGoal(),
                profile.getDailyCalories(),
                profile.getDailyProtein(),
                profile.getDailyCarbohydrates(),
                profile.getDailyFats(),
                profile.getDailyFiber(),
                profile.getMealsPerDay(),
                profile.getDietType(),
                profile.getDietaryPreferences(),
                profile.getRestrictions()
        );
    }
}