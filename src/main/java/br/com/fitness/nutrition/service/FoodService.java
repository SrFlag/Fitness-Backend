package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.CreateFoodRequest;
import br.com.fitness.nutrition.dto.FoodResponse;
import br.com.fitness.nutrition.dto.UpdateFoodRequest;
import br.com.fitness.nutrition.model.Food;
import br.com.fitness.nutrition.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public FoodResponse create(CreateFoodRequest request) {

        String normalizedName =
                normalizeName(request.getName());

        if (foodRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new RuntimeException(
                    "Já existe um alimento com esse nome"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        Food food = new Food();

        food.setName(normalizedName);
        food.setCategory(
                normalizeCategory(request.getCategory())
        );
        food.setCalories(request.getCalories());
        food.setProtein(request.getProtein());
        food.setCarbohydrates(request.getCarbohydrates());
        food.setFats(request.getFats());
        food.setFiber(request.getFiber());
        food.setSugar(request.getSugar());
        food.setSodium(request.getSodium());
        food.setBaseUnit(
                normalizeBaseUnit(request.getBaseUnit())
        );
        food.setBaseQuantity(request.getBaseQuantity());
        food.setCreatedAt(now);
        food.setUpdatedAt(now);

        Food savedFood = foodRepository.save(food);

        return toResponse(savedFood);
    }

    public List<FoodResponse> findAll() {

        return foodRepository
                .findAllByOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FoodResponse findById(String id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alimento não encontrado"
                        )
                );

        return toResponse(food);
    }

    public List<FoodResponse> findByCategory(
            String category
    ) {

        String normalizedCategory =
                normalizeCategory(category);

        return foodRepository
                .findByCategory(normalizedCategory)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FoodResponse> searchByName(
            String name
    ) {

        if (name == null || name.isBlank()) {
            return findAll();
        }

        return foodRepository
                .findByNameContainingIgnoreCase(name.trim())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FoodResponse update(
            String id,
            UpdateFoodRequest request
    ) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alimento não encontrado"
                        )
                );

        String normalizedName =
                normalizeName(request.getName());

        boolean nameChanged =
                !food.getName()
                        .equalsIgnoreCase(normalizedName);

        if (
                nameChanged
                        && foodRepository.existsByNameIgnoreCase(
                        normalizedName
                )
        ) {
            throw new RuntimeException(
                    "Já existe um alimento com esse nome"
            );
        }

        food.setName(normalizedName);

        food.setCategory(
                normalizeCategory(request.getCategory())
        );

        food.setCalories(request.getCalories());
        food.setProtein(request.getProtein());
        food.setCarbohydrates(request.getCarbohydrates());
        food.setFats(request.getFats());
        food.setFiber(request.getFiber());
        food.setSugar(request.getSugar());
        food.setSodium(request.getSodium());

        food.setBaseUnit(
                normalizeBaseUnit(request.getBaseUnit())
        );

        food.setBaseQuantity(
                request.getBaseQuantity()
        );

        food.setUpdatedAt(LocalDateTime.now());

        Food updatedFood =
                foodRepository.save(food);

        return toResponse(updatedFood);
    }

    public void delete(String id) {

        if (!foodRepository.existsById(id)) {
            throw new RuntimeException(
                    "Alimento não encontrado"
            );
        }

        foodRepository.deleteById(id);
    }

    private FoodResponse toResponse(
            Food food
    ) {

        return new FoodResponse(
                food.getId(),
                food.getName(),
                food.getCategory(),
                food.getCalories(),
                food.getProtein(),
                food.getCarbohydrates(),
                food.getFats(),
                food.getFiber(),
                food.getSugar(),
                food.getSodium(),
                food.getBaseUnit(),
                food.getBaseQuantity()
        );
    }

    private String normalizeName(
            String value
    ) {

        if (value == null) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.isEmpty()) {
            return normalized;
        }

        return normalized.substring(0, 1)
                .toUpperCase(Locale.ROOT)
                + normalized.substring(1);
    }

    private String normalizeCategory(
            String value
    ) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private String normalizeBaseUnit(
            String value
    ) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }
}