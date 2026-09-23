package br.com.fitness.nutrition.controller;

import br.com.fitness.nutrition.dto.DailyNutritionSummaryResponse;
import br.com.fitness.nutrition.service.DailyNutritionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/nutrition")
public class DailyNutritionController {

    private final DailyNutritionService dailyNutritionService;

    public DailyNutritionController(
            DailyNutritionService dailyNutritionService
    ) {
        this.dailyNutritionService =
                dailyNutritionService;
    }

    @GetMapping("/daily")
    public DailyNutritionSummaryResponse getDailySummary(
            Authentication authentication,
            @RequestParam LocalDate date
    ) {
        String email = authentication.getName();

        return dailyNutritionService.getDailySummary(
                email,
                date
        );
    }
}