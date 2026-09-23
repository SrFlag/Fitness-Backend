package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.CreateMealItemRequest;
import br.com.fitness.nutrition.dto.MealItemResponse;
import br.com.fitness.nutrition.dto.MealNutritionSummaryResponse;
import br.com.fitness.nutrition.dto.UpdateMealItemRequest;
import br.com.fitness.nutrition.model.Food;
import br.com.fitness.nutrition.model.Meal;
import br.com.fitness.nutrition.model.MealItem;
import br.com.fitness.nutrition.repository.FoodRepository;
import br.com.fitness.nutrition.repository.MealItemRepository;
import br.com.fitness.nutrition.repository.MealRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class MealItemService {

    private final MealItemRepository mealItemRepository;
    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public MealItemService(
            MealItemRepository mealItemRepository,
            MealRepository mealRepository,
            FoodRepository foodRepository,
            UserRepository userRepository
    ) {
        this.mealItemRepository = mealItemRepository;
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    public MealItemResponse create(
            String email,
            String mealId,
            CreateMealItemRequest request
    ) {

        User user = findUserByEmail(email);

        Meal meal = findMealForUser(
                mealId,
                user.getId()
        );

        Food food = findFood(
                request.getFoodId()
        );

        validateUnit(
                request.getUnit(),
                food.getBaseUnit()
        );

        NutritionalValues values =
                calculateNutritionalValues(
                        food,
                        request.getQuantity()
                );

        LocalDateTime now =
                LocalDateTime.now();

        MealItem mealItem = new MealItem();

        mealItem.setMealId(
                meal.getId()
        );

        mealItem.setFoodId(
                food.getId()
        );

        mealItem.setQuantity(
                request.getQuantity()
        );

        mealItem.setUnit(
                normalizeUnit(request.getUnit())
        );

        mealItem.setCalories(
                values.calories()
        );

        mealItem.setProtein(
                values.protein()
        );

        mealItem.setCarbohydrates(
                values.carbohydrates()
        );

        mealItem.setFats(
                values.fats()
        );

        mealItem.setFiber(
                values.fiber()
        );

        mealItem.setSugar(
                values.sugar()
        );

        mealItem.setSodium(
                values.sodium()
        );

        mealItem.setCreatedAt(now);
        mealItem.setUpdatedAt(now);

        MealItem savedItem =
                mealItemRepository.save(mealItem);

        return toResponse(savedItem);
    }

    public List<MealItemResponse> findByMeal(
            String email,
            String mealId
    ) {

        User user = findUserByEmail(email);

        findMealForUser(
                mealId,
                user.getId()
        );

        return mealItemRepository
                .findByMealIdOrderByCreatedAtAsc(mealId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MealItemResponse findById(
            String email,
            String mealId,
            String mealItemId
    ) {

        User user = findUserByEmail(email);

        findMealForUser(
                mealId,
                user.getId()
        );

        MealItem mealItem =
                mealItemRepository
                        .findByIdAndMealId(
                                mealItemId,
                                mealId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Item da refeição não encontrado"
                                )
                        );

        return toResponse(mealItem);
    }

    public MealItemResponse update(
            String email,
            String mealId,
            String mealItemId,
            UpdateMealItemRequest request
    ) {

        User user = findUserByEmail(email);

        findMealForUser(
                mealId,
                user.getId()
        );

        MealItem mealItem =
                mealItemRepository
                        .findByIdAndMealId(
                                mealItemId,
                                mealId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Item da refeição não encontrado"
                                )
                        );

        Food food = findFood(
                request.getFoodId()
        );

        validateUnit(
                request.getUnit(),
                food.getBaseUnit()
        );

        NutritionalValues values =
                calculateNutritionalValues(
                        food,
                        request.getQuantity()
                );

        mealItem.setFoodId(
                food.getId()
        );

        mealItem.setQuantity(
                request.getQuantity()
        );

        mealItem.setUnit(
                normalizeUnit(request.getUnit())
        );

        mealItem.setCalories(
                values.calories()
        );

        mealItem.setProtein(
                values.protein()
        );

        mealItem.setCarbohydrates(
                values.carbohydrates()
        );

        mealItem.setFats(
                values.fats()
        );

        mealItem.setFiber(
                values.fiber()
        );

        mealItem.setSugar(
                values.sugar()
        );

        mealItem.setSodium(
                values.sodium()
        );

        mealItem.setUpdatedAt(
                LocalDateTime.now()
        );

        MealItem updatedItem =
                mealItemRepository.save(mealItem);

        return toResponse(updatedItem);
    }

    public void delete(
            String email,
            String mealId,
            String mealItemId
    ) {

        User user = findUserByEmail(email);

        findMealForUser(
                mealId,
                user.getId()
        );

        MealItem mealItem =
                mealItemRepository
                        .findByIdAndMealId(
                                mealItemId,
                                mealId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Item da refeição não encontrado"
                                )
                        );

        mealItemRepository.delete(mealItem);
    }

    public MealNutritionSummaryResponse getNutritionSummary(
            String email,
            String mealId
    ) {

        User user = findUserByEmail(email);

        Meal meal = findMealForUser(
                mealId,
                user.getId()
        );

        List<MealItem> items =
                mealItemRepository
                        .findByMealIdOrderByCreatedAtAsc(
                                mealId
                        );

        double calories = 0.0;
        double protein = 0.0;
        double carbohydrates = 0.0;
        double fats = 0.0;
        double fiber = 0.0;
        double sugar = 0.0;
        double sodium = 0.0;

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

        return new MealNutritionSummaryResponse(
                meal.getId(),
                meal.getName(),
                calories,
                protein,
                carbohydrates,
                fats,
                fiber,
                sugar,
                sodium,
                items.size()
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

    private Meal findMealForUser(
            String mealId,
            String userId
    ) {

        return mealRepository
                .findByIdAndUserId(
                        mealId,
                        userId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Refeição não encontrada"
                        )
                );
    }

    private Food findFood(
            String foodId
    ) {

        return foodRepository
                .findById(foodId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alimento não encontrado"
                        )
                );
    }

    private NutritionalValues calculateNutritionalValues(
            Food food,
            Double quantity
    ) {

        if (food.getBaseQuantity() == null
                || food.getBaseQuantity() <= 0) {

            throw new RuntimeException(
                    "O alimento possui uma quantidade base inválida"
            );
        }

        double factor =
                quantity / food.getBaseQuantity();

        return new NutritionalValues(
                calculate(
                        food.getCalories(),
                        factor
                ),
                calculate(
                        food.getProtein(),
                        factor
                ),
                calculate(
                        food.getCarbohydrates(),
                        factor
                ),
                calculate(
                        food.getFats(),
                        factor
                ),
                calculate(
                        food.getFiber(),
                        factor
                ),
                calculate(
                        food.getSugar(),
                        factor
                ),
                calculate(
                        food.getSodium(),
                        factor
                )
        );
    }

    private Double calculate(
            Double value,
            double factor
    ) {

        if (value == null) {
            return 0.0;
        }

        return value * factor;
    }

    private Double safeValue(
            Double value
    ) {

        if (value == null) {
            return 0.0;
        }

        return value;
    }

    private void validateUnit(
            String requestedUnit,
            String foodBaseUnit
    ) {

        if (requestedUnit == null
                || foodBaseUnit == null) {

            throw new RuntimeException(
                    "A unidade do alimento é inválida"
            );
        }

        String normalizedRequestedUnit =
                normalizeUnit(requestedUnit);

        String normalizedBaseUnit =
                normalizeUnit(foodBaseUnit);

        if (!normalizedRequestedUnit.equals(
                normalizedBaseUnit
        )) {

            throw new RuntimeException(
                    "A unidade informada não é compatível com a unidade base do alimento"
            );
        }
    }

    private String normalizeUnit(
            String value
    ) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private MealItemResponse toResponse(
            MealItem mealItem
    ) {

        return new MealItemResponse(
                mealItem.getId(),
                mealItem.getMealId(),
                mealItem.getFoodId(),
                mealItem.getQuantity(),
                mealItem.getUnit(),
                mealItem.getCalories(),
                mealItem.getProtein(),
                mealItem.getCarbohydrates(),
                mealItem.getFats(),
                mealItem.getFiber(),
                mealItem.getSugar(),
                mealItem.getSodium()
        );
    }

    private record NutritionalValues(
            Double calories,
            Double protein,
            Double carbohydrates,
            Double fats,
            Double fiber,
            Double sugar,
            Double sodium
    ) {
    }
}