package br.com.fitness.nutrition.dto;

public class MealNutritionSummaryResponse {

    private String mealId;

    private String mealName;

    private Double calories;

    private Double protein;

    private Double carbohydrates;

    private Double fats;

    private Double fiber;

    private Double sugar;

    private Double sodium;

    private Integer totalItems;

    public MealNutritionSummaryResponse(
            String mealId,
            String mealName,
            Double calories,
            Double protein,
            Double carbohydrates,
            Double fats,
            Double fiber,
            Double sugar,
            Double sodium,
            Integer totalItems
    ) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.sugar = sugar;
        this.sodium = sodium;
        this.totalItems = totalItems;
    }

    public String getMealId() {
        return mealId;
    }

    public String getMealName() {
        return mealName;
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

    public Integer getTotalItems() {
        return totalItems;
    }
}