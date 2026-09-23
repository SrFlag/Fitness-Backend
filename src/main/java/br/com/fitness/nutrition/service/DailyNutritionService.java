package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.DailyNutritionSummaryResponse;
import br.com.fitness.nutrition.model.Meal;
import br.com.fitness.nutrition.model.MealItem;
import br.com.fitness.nutrition.model.NutritionProfile;
import br.com.fitness.nutrition.repository.MealItemRepository;
import br.com.fitness.nutrition.repository.MealRepository;
import br.com.fitness.nutrition.repository.NutritionProfileRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyNutritionService {

    private final MealRepository mealRepository;
    private final MealItemRepository mealItemRepository;
    private final NutritionProfileRepository nutritionProfileRepository;
    private final UserRepository userRepository;

    public DailyNutritionService(
            MealRepository mealRepository,
            MealItemRepository mealItemRepository,
            NutritionProfileRepository nutritionProfileRepository,
            UserRepository userRepository
    ) {
        this.mealRepository = mealRepository;
        this.mealItemRepository = mealItemRepository;
        this.nutritionProfileRepository =
                nutritionProfileRepository;
        this.userRepository = userRepository;
    }

    public DailyNutritionSummaryResponse getDailySummary(
            String email,
            LocalDate date
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

        List<Meal> meals =
                mealRepository
                        .findByUserIdAndDateOrderByCreatedAtAsc(
                                user.getId(),
                                date
                        );

        double calories = 0.0;
        double protein = 0.0;
        double carbohydrates = 0.0;
        double fats = 0.0;
        double fiber = 0.0;
        double sugar = 0.0;
        double sodium = 0.0;

        int totalItems = 0;

        for (Meal meal : meals) {

            List<MealItem> items =
                    mealItemRepository
                            .findByMealIdOrderByCreatedAtAsc(
                                    meal.getId()
                            );

            totalItems += items.size();

            for (MealItem item : items) {

                calories += safeValue(
                        item.getCalories()
                );

                protein += safeValue(
                        item.getProtein()
                );

                carbohydrates += safeValue(
                        item.getCarbohydrates()
                );

                fats += safeValue(
                        item.getFats()
                );

                fiber += safeValue(
                        item.getFiber()
                );

                sugar += safeValue(
                        item.getSugar()
                );

                sodium += safeValue(
                        item.getSodium()
                );
            }
        }

        double targetCalories =
                safeValue(profile.getDailyCalories());

        double targetProtein =
                safeValue(profile.getDailyProtein());

        double targetCarbohydrates =
                safeValue(profile.getDailyCarbohydrates());

        double targetFats =
                safeValue(profile.getDailyFats());

        double targetFiber =
                safeValue(profile.getDailyFiber());

        double caloriesRemaining =
                targetCalories - calories;

        double proteinRemaining =
                targetProtein - protein;

        double carbohydratesRemaining =
                targetCarbohydrates - carbohydrates;

        double fatsRemaining =
                targetFats - fats;

        double fiberRemaining =
                targetFiber - fiber;

        return new DailyNutritionSummaryResponse(
                date,
                calories,
                protein,
                carbohydrates,
                fats,
                fiber,
                sugar,
                sodium,
                targetCalories,
                targetProtein,
                targetCarbohydrates,
                targetFats,
                targetFiber,
                caloriesRemaining,
                proteinRemaining,
                carbohydratesRemaining,
                fatsRemaining,
                fiberRemaining,
                meals.size(),
                totalItems
        );
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

    private double safeValue(
            Double value
    ) {

        if (value == null) {
            return 0.0;
        }

        return value;
    }
}