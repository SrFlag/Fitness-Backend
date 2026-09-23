package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.CreateFoodRequest;
import br.com.fitness.nutrition.dto.FoodResponse;
import br.com.fitness.nutrition.dto.UpdateFoodRequest;
import br.com.fitness.nutrition.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodResponse create(
            @Valid @RequestBody CreateFoodRequest request
    ) {
        return foodService.create(request);
    }

    @GetMapping
    public List<FoodResponse> findAll() {
        return foodService.findAll();
    }

    @GetMapping("/{id}")
    public FoodResponse findById(
            @PathVariable String id
    ) {
        return foodService.findById(id);
    }

    @GetMapping("/category/{category}")
    public List<FoodResponse> findByCategory(
            @PathVariable String category
    ) {
        return foodService.findByCategory(category);
    }

    @GetMapping("/search")
    public List<FoodResponse> search(
            @RequestParam String name
    ) {
        return foodService.searchByName(name);
    }

    @PutMapping("/{id}")
    public FoodResponse update(
            @PathVariable String id,
            @Valid @RequestBody UpdateFoodRequest request
    ) {
        return foodService.update(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String id
    ) {
        foodService.delete(id);
    }
}