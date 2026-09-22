package br.com.fitness.workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateWorkoutRequest {

    @NotBlank(message = "O nome do treino é obrigatório")
    private String name;

    private String description;

    @NotBlank(message = "O objetivo do treino é obrigatório")
    private String goal;

    @NotNull(message = "A duração estimada é obrigatória")
    @Positive(message = "A duração estimada deve ser maior que zero")
    private Integer estimatedDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}