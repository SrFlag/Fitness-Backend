package br.com.fitness.nutrition.dto;

public class NutritionProfileResponse {

    private String id;

    private String nutritionGoal;

    private Double dailyCalories;

    private Double dailyProtein;

    private Double dailyCarbohydrates;

    private Double dailyFats;

    private Double dailyFiber;

    private Integer mealsPerDay;

    private String dietType;

    private String dietaryPreferences;

    private String restrictions;

    public NutritionProfileResponse(
            String id,
            String nutritionGoal,
            Double dailyCalories,
            Double dailyProtein,
            Double dailyCarbohydrates,
            Double dailyFats,
            Double dailyFiber,
            Integer mealsPerDay,
            String dietType,
            String dietaryPreferences,
            String restrictions
    ) {
        this.id = id;
        this.nutritionGoal = nutritionGoal;
        this.dailyCalories = dailyCalories;
        this.dailyProtein = dailyProtein;
        this.dailyCarbohydrates = dailyCarbohydrates;
        this.dailyFats = dailyFats;
        this.dailyFiber = dailyFiber;
        this.mealsPerDay = mealsPerDay;
        this.dietType = dietType;
        this.dietaryPreferences = dietaryPreferences;
        this.restrictions = restrictions;
    }

    public String getId() {
        return id;
    }

    public String getNutritionGoal() {
        return nutritionGoal;
    }

    public Double getDailyCalories() {
        return dailyCalories;
    }

    public Double getDailyProtein() {
        return dailyProtein;
    }

    public Double getDailyCarbohydrates() {
        return dailyCarbohydrates;
    }

    public Double getDailyFats() {
        return dailyFats;
    }

    public Double getDailyFiber() {
        return dailyFiber;
    }

    public Integer getMealsPerDay() {
        return mealsPerDay;
    }

    public String getDietType() {
        return dietType;
    }

    public String getDietaryPreferences() {
        return dietaryPreferences;
    }

    public String getRestrictions() {
        return restrictions;
    }
}