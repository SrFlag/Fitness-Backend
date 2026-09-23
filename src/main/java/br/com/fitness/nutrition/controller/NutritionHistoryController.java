package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.NutritionHistoryResponse;
import br.com.fitness.nutrition.service.NutritionHistoryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionHistoryController {

    private final NutritionHistoryService nutritionHistoryService;

    public NutritionHistoryController(
            NutritionHistoryService nutritionHistoryService
    ) {
        this.nutritionHistoryService = nutritionHistoryService;
    }

    @GetMapping("/history")
    public NutritionHistoryResponse getHistory(
            Authentication authentication,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {

        String email = authentication.getName();

        return nutritionHistoryService.getHistory(
                email,
                startDate,
                endDate
        );
    }
}