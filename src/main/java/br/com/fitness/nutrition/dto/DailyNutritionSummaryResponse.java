package br.com.fitness.nutrition.dto;

import java.time.LocalDate;

public class DailyNutritionSummaryResponse {

    private LocalDate date;

    private Double calories;

    private Double protein;

    private Double carbohydrates;

    private Double fats;

    private Double fiber;

    private Double sugar;

    private Double sodium;

    private Double targetCalories;

    private Double targetProtein;

    private Double targetCarbohydrates;

    private Double targetFats;

    private Double targetFiber;

    private Double caloriesRemaining;

    private Double proteinRemaining;

    private Double carbohydratesRemaining;

    private Double fatsRemaining;

    private Double fiberRemaining;

    private Integer totalMeals;

    private Integer totalItems;

    public DailyNutritionSummaryResponse(
            LocalDate date,
            Double calories,
            Double protein,
            Double carbohydrates,
            Double fats,
            Double fiber,
            Double sugar,
            Double sodium,
            Double targetCalories,
            Double targetProtein,
            Double targetCarbohydrates,
            Double targetFats,
            Double targetFiber,
            Double caloriesRemaining,
            Double proteinRemaining,
            Double carbohydratesRemaining,
            Double fatsRemaining,
            Double fiberRemaining,
            Integer totalMeals,
            Integer totalItems
    ) {
        this.date = date;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.sugar = sugar;
        this.sodium = sodium;
        this.targetCalories = targetCalories;
        this.targetProtein = targetProtein;
        this.targetCarbohydrates = targetCarbohydrates;
        this.targetFats = targetFats;
        this.targetFiber = targetFiber;
        this.caloriesRemaining = caloriesRemaining;
        this.proteinRemaining = proteinRemaining;
        this.carbohydratesRemaining = carbohydratesRemaining;
        this.fatsRemaining = fatsRemaining;
        this.fiberRemaining = fiberRemaining;
        this.totalMeals = totalMeals;
        this.totalItems = totalItems;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public Double getFats() {
        return fats;
    }

    public Double getFiber() {
        return fiber;
    }

    public Double getSugar() {
        return sugar;
    }

    public Double getSodium() {
        return sodium;
    }

    public Double getTargetCalories() {
        return targetCalories;
    }

    public Double getTargetProtein() {
        return targetProtein;
    }

    public Double getTargetCarbohydrates() {
        return targetCarbohydrates;
    }

    public Double getTargetFats() {
        return targetFats;
    }

    public Double getTargetFiber() {
        return targetFiber;
    }

    public Double getCaloriesRemaining() {
        return caloriesRemaining;
    }

    public Double getProteinRemaining() {
        return proteinRemaining;
    }

    public Double getCarbohydratesRemaining() {
        return carbohydratesRemaining;
    }

    public Double getFatsRemaining() {
        return fatsRemaining;
    }

    public Double getFiberRemaining() {
        return fiberRemaining;
    }

    public Integer getTotalMeals() {
        return totalMeals;
    }

    public Integer getTotalItems() {
        return totalItems;
    }
}