package br.com.fitness.nutrition.dto;

import java.time.LocalDate;
import java.util.List;

public class NutritionHistoryResponse {

    private LocalDate startDate;

    private LocalDate endDate;

    private List<DailyNutritionSummaryResponse> days;

    public NutritionHistoryResponse(
            LocalDate startDate,
            LocalDate endDate,
            List<DailyNutritionSummaryResponse> days
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<DailyNutritionSummaryResponse> getDays() {
        return days;
    }
}