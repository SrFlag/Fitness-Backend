package br.com.fitness.workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateWorkoutExerciseRequest {

    @NotNull(message = "A ordem do exercício é obrigatória")
    @Positive(message = "A ordem do exercício deve ser maior que zero")
    private Integer exerciseOrder;

    @NotNull(message = "A quantidade de séries é obrigatória")
    @Positive(message = "A quantidade de séries deve ser maior que zero")
    private Integer sets;

    @NotBlank(message = "As repetições são obrigatórias")
    private String repetitions;

    @NotNull(message = "O descanso é obrigatório")
    @Positive(message = "O descanso deve ser maior que zero")
    private Integer restSeconds;

    private Double targetWeight;

    private String notes;

    public Integer getExerciseOrder() {
        return exerciseOrder;
    }

    public void setExerciseOrder(Integer exerciseOrder) {
        this.exerciseOrder = exerciseOrder;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(Integer restSeconds) {
        this.restSeconds = restSeconds;
    }

    public Double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}