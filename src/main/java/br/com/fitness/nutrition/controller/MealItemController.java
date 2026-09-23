package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.CreateMealItemRequest;
import br.com.fitness.nutrition.dto.MealItemResponse;
import br.com.fitness.nutrition.dto.MealNutritionSummaryResponse;
import br.com.fitness.nutrition.dto.UpdateMealItemRequest;
import br.com.fitness.nutrition.service.MealItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals/{mealId}/items")
public class MealItemController {

    private final MealItemService mealItemService;

    public MealItemController(
            MealItemService mealItemService
    ) {
        this.mealItemService = mealItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MealItemResponse create(
            Authentication authentication,
            @PathVariable String mealId,
            @Valid @RequestBody CreateMealItemRequest request
    ) {
        String email = authentication.getName();

        return mealItemService.create(
                email,
                mealId,
                request
        );
    }

    @GetMapping
    public List<MealItemResponse> findByMeal(
            Authentication authentication,
            @PathVariable String mealId
    ) {
        String email = authentication.getName();

        return mealItemService.findByMeal(
                email,
                mealId
        );
    }

    @GetMapping("/summary")
    public MealNutritionSummaryResponse getNutritionSummary(
            Authentication authentication,
            @PathVariable String mealId
    ) {
        String email = authentication.getName();

        return mealItemService.getNutritionSummary(
                email,
                mealId
        );
    }

    @GetMapping("/{itemId}")
    public MealItemResponse findById(
            Authentication authentication,
            @PathVariable String mealId,
            @PathVariable String itemId
    ) {
        String email = authentication.getName();

        return mealItemService.findById(
                email,
                mealId,
                itemId
        );
    }

    @PutMapping("/{itemId}")
    public MealItemResponse update(
            Authentication authentication,
            @PathVariable String mealId,
            @PathVariable String itemId,
            @Valid @RequestBody UpdateMealItemRequest request
    ) {
        String email = authentication.getName();

        return mealItemService.update(
                email,
                mealId,
                itemId,
                request
        );
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication,
            @PathVariable String mealId,
            @PathVariable String itemId
    ) {
        String email = authentication.getName();

        mealItemService.delete(
                email,
                mealId,
                itemId
        );
    }
}