package br.com.fitness.nutrition.dto;

public class FoodResponse {

    private String id;
    private String name;
    private String category;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fats;
    private Double fiber;
    private Double sugar;
    private Double sodium;
    private String baseUnit;
    private Double baseQuantity;

    public FoodResponse(
            String id,
            String name,
            String category,
            Double calories,
            Double protein,
            Double carbohydrates,
            Double fats,
            Double fiber,
            Double sugar,
            Double sodium,
            String baseUnit,
            Double baseQuantity
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.sugar = sugar;
        this.sodium = sodium;
        this.baseUnit = baseUnit;
        this.baseQuantity = baseQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
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

    public String getBaseUnit() {
        return baseUnit;
    }

    public Double getBaseQuantity() {
        return baseQuantity;
    }
}