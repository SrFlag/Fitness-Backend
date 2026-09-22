package br.com.fitness.performance.controller;

import br.com.fitness.performance.dto.ExercisePerformanceResponse;
import br.com.fitness.performance.dto.PerformanceSummaryResponse;
import br.com.fitness.performance.dto.WorkoutHistoryResponse;
import br.com.fitness.performance.service.PerformanceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping("/exercises/{exerciseId}")
    public ExercisePerformanceResponse getExercisePerformance(
            @PathVariable String exerciseId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return performanceService.getExercisePerformance(
                email,
                exerciseId
        );
    }

    @GetMapping("/summary")
    public PerformanceSummaryResponse getPerformanceSummary(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return performanceService.getPerformanceSummary(email);
    }

    @GetMapping("/history")
    public List<WorkoutHistoryResponse> getWorkoutHistory(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return performanceService.getWorkoutHistory(email);
    }
}