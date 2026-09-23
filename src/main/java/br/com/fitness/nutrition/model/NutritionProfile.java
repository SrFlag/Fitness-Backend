package br.com.fitness.nutrition.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "nutrition_profiles")
public class NutritionProfile {

    @Id
    private String id;

    private String userId;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public NutritionProfile() {
    }

    public NutritionProfile(
            String id,
            String userId,
            String nutritionGoal,
            Double dailyCalories,
            Double dailyProtein,
            Double dailyCarbohydrates,
            Double dailyFats,
            Double dailyFiber,
            Integer mealsPerDay,
            String dietType,
            String dietaryPreferences,
            String restrictions,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}