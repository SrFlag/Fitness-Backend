package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.DailyNutritionSummaryResponse;
import br.com.fitness.nutrition.dto.NutritionHistoryResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NutritionHistoryService {

    private final DailyNutritionService dailyNutritionService;

    public NutritionHistoryService(
            DailyNutritionService dailyNutritionService
    ) {
        this.dailyNutritionService = dailyNutritionService;
    }

    public NutritionHistoryResponse getHistory(
            String email,
            LocalDate startDate,
            LocalDate endDate
    ) {

        validateDateRange(startDate, endDate);

        List<DailyNutritionSummaryResponse> days = new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {

            DailyNutritionSummaryResponse dailySummary =
                    dailyNutritionService.getDailySummary(
                            email,
                            currentDate
                    );

            days.add(dailySummary);

            currentDate = currentDate.plusDays(1);
        }

        return new NutritionHistoryResponse(
                startDate,
                endDate,
                days
        );
    }

    private void validateDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null || endDate == null) {
            throw new RuntimeException(
                    "As datas inicial e final são obrigatórias"
            );
        }

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException(
                    "A data final não pode ser anterior à data inicial"
            );
        }
    }
}