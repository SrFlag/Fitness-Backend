package br.com.fitness.nutrition.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateNutritionProfileRequest {

    @NotBlank
    private String nutritionGoal;

    @NotNull
    @Positive
    private Double dailyCalories;

    @NotNull
    @PositiveOrZero
    private Double dailyProtein;

    @NotNull
    @PositiveOrZero
    private Double dailyCarbohydrates;

    @NotNull
    @PositiveOrZero
    private Double dailyFats;

    @NotNull
    @PositiveOrZero
    private Double dailyFiber;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer mealsPerDay;

    @NotBlank
    private String dietType;

    private String dietaryPreferences;

    private String restrictions;

    public String getNutritionGoal() {
        return nutritionGoal;
    }

    public void setNutritionGoal(String nutritionGoal) {
        this.nutritionGoal = nutritionGoal;
    }

    public Double getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(Double dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public Double getDailyProtein() {
        return dailyProtein;
    }

    public void setDailyProtein(Double dailyProtein) {
        this.dailyProtein = dailyProtein;
    }

    public Double getDailyCarbohydrates() {
        return dailyCarbohydrates;
    }

    public void setDailyCarbohydrates(Double dailyCarbohydrates) {
        this.dailyCarbohydrates = dailyCarbohydrates;
    }

    public Double getDailyFats() {
        return dailyFats;
    }

    public void setDailyFats(Double dailyFats) {
        this.dailyFats = dailyFats;
    }

    public Double getDailyFiber() {
        return dailyFiber;
    }

    public void setDailyFiber(Double dailyFiber) {
        this.dailyFiber = dailyFiber;
    }

    public Integer getMealsPerDay() {
        return mealsPerDay;
    }

    public void setMealsPerDay(Integer mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(String dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }
}