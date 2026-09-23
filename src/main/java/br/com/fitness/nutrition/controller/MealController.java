package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.CreateMealRequest;
import br.com.fitness.nutrition.dto.MealResponse;
import br.com.fitness.nutrition.dto.UpdateMealRequest;
import br.com.fitness.nutrition.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MealResponse create(
            Authentication authentication,
            @Valid @RequestBody CreateMealRequest request
    ) {
        String email = authentication.getName();

        return mealService.create(
                email,
                request
        );
    }

    @GetMapping
    public List<MealResponse> findAll(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return mealService.findAll(email);
    }

    @GetMapping("/date/{date}")
    public List<MealResponse> findByDate(
            Authentication authentication,
            @PathVariable LocalDate date
    ) {
        String email = authentication.getName();

        return mealService.findByDate(
                email,
                date
        );
    }

    @GetMapping("/{id}")
    public MealResponse findById(
            Authentication authentication,
            @PathVariable String id
    ) {
        String email = authentication.getName();

        return mealService.findById(
                email,
                id
        );
    }

    @PutMapping("/{id}")
    public MealResponse update(
            Authentication authentication,
            @PathVariable String id,
            @Valid @RequestBody UpdateMealRequest request
    ) {
        String email = authentication.getName();

        return mealService.update(
                email,
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication,
            @PathVariable String id
    ) {
        String email = authentication.getName();

        mealService.delete(
                email,
                id
        );
    }
}