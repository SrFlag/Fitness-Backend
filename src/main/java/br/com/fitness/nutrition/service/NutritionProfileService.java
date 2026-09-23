package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.CreateNutritionProfileRequest;
import br.com.fitness.nutrition.dto.UpdateNutritionProfileRequest;
import br.com.fitness.nutrition.model.NutritionProfile;
import br.com.fitness.nutrition.repository.NutritionProfileRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NutritionProfileService {

    private final NutritionProfileRepository nutritionProfileRepository;
    private final UserRepository userRepository;

    public NutritionProfileService(
            NutritionProfileRepository nutritionProfileRepository,
            UserRepository userRepository
    ) {
        this.nutritionProfileRepository = nutritionProfileRepository;
        this.userRepository = userRepository;
    }

    public NutritionProfile create(
            String email,
            CreateNutritionProfileRequest request
    ) {

        User user = findUserByEmail(email);

        if (nutritionProfileRepository.existsByUserId(user.getId())) {
            throw new RuntimeException(
                    "O usuário já possui um perfil nutricional"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        NutritionProfile profile = new NutritionProfile();

        profile.setUserId(user.getId());

        profile.setNutritionGoal(
                normalize(request.getNutritionGoal())
        );

        profile.setDailyCalories(
                request.getDailyCalories()
        );

        profile.setDailyProtein(
                request.getDailyProtein()
        );

        profile.setDailyCarbohydrates(
                request.getDailyCarbohydrates()
        );

        profile.setDailyFats(
                request.getDailyFats()
        );

        profile.setDailyFiber(
                request.getDailyFiber()
        );

        profile.setMealsPerDay(
                request.getMealsPerDay()
        );

        profile.setDietType(
                normalize(request.getDietType())
        );

        profile.setDietaryPreferences(
                request.getDietaryPreferences()
        );

        profile.setRestrictions(
                request.getRestrictions()
        );

        profile.setCreatedAt(now);
        profile.setUpdatedAt(now);

        return nutritionProfileRepository.save(profile);
    }

    public NutritionProfile findByAuthenticatedUser(
            String email
    ) {

        User user = findUserByEmail(email);

        return nutritionProfileRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Perfil nutricional não encontrado"
                        )
                );
    }

    public NutritionProfile update(
            String email,
            UpdateNutritionProfileRequest request
    ) {

        User user = findUserByEmail(email);

        NutritionProfile profile =
                nutritionProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Perfil nutricional não encontrado"
                                )
                        );

        profile.setNutritionGoal(
                normalize(request.getNutritionGoal())
        );

        profile.setDailyCalories(
                request.getDailyCalories()
        );

        profile.setDailyProtein(
                request.getDailyProtein()
        );

        profile.setDailyCarbohydrates(
                request.getDailyCarbohydrates()
        );

        profile.setDailyFats(
                request.getDailyFats()
        );

        profile.setDailyFiber(
                request.getDailyFiber()
        );

        profile.setMealsPerDay(
                request.getMealsPerDay()
        );

        profile.setDietType(
                normalize(request.getDietType())
        );

        profile.setDietaryPreferences(
                request.getDietaryPreferences()
        );

        profile.setRestrictions(
                request.getRestrictions()
        );

        profile.setUpdatedAt(
                LocalDateTime.now()
        );

        return nutritionProfileRepository.save(profile);
    }

    public void delete(
            String email
    ) {

        User user = findUserByEmail(email);

        NutritionProfile profile =
                nutritionProfileRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Perfil nutricional não encontrado"
                                )
                        );

        nutritionProfileRepository.delete(profile);
    }

    private User findUserByEmail(
            String email
    ) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );
    }

    private String normalize(
            String value
    ) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase();
    }
}