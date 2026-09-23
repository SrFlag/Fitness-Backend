package br.com.fitness.nutrition.dto;

import java.time.LocalDate;

public class MealResponse {

    private String id;

    private LocalDate date;

    private String name;

    private String mealType;

    private String notes;

    public MealResponse(
            String id,
            LocalDate date,
            String name,
            String mealType,
            String notes
    ) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.mealType = mealType;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getMealType() {
        return mealType;
    }

    public String getNotes() {
        return notes;
    }
}