package br.com.fitness.workout.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class FinishWorkoutSessionRequest {

    @PositiveOrZero(message = "A duração não pode ser negativa")
    private Integer durationSeconds;

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}