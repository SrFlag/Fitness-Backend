package br.com.fitness.workout.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateWorkoutSessionRequest {

    @NotBlank(message = "O treino é obrigatório")
    private String workoutId;

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }
}