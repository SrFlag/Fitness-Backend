package br.com.fitness.nutrition.dto;

public class MealItemResponse {

    private String id;

    private String mealId;

    private String foodId;

    private Double quantity;

    private String unit;

    private Double calories;

    private Double protein;

    private Double carbohydrates;

    private Double fats;

    private Double fiber;

    private Double sugar;

    private Double sodium;

    public MealItemResponse(
            String id,
            String mealId,
            String foodId,
            Double quantity,
            String unit,
            Double calories,
            Double protein,
            Double carbohydrates,
            Double fats,
            Double fiber,
            Double sugar,
            Double sodium
    ) {
        this.id = id;
        this.mealId = mealId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    public String getId() {
        return id;
    }

    public String getMealId() {
        return mealId;
    }

    public String getFoodId() {
        return foodId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
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
}