package br.com.fitness.workout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateSetLogRequest {

    @NotNull(message = "O número da série é obrigatório")
    @Positive(message = "O número da série deve ser maior que zero")
    private Integer setNumber;

    @NotNull(message = "A carga é obrigatória")
    @PositiveOrZero(message = "A carga não pode ser negativa")
    private Double weight;

    @NotNull(message = "O número de repetições é obrigatório")
    @Positive(message = "O número de repetições deve ser maior que zero")
    private Integer repetitions;

    @NotNull(message = "O tempo de descanso é obrigatório")
    @PositiveOrZero(message = "O descanso não pode ser negativo")
    private Integer restSeconds;

    @NotNull(message = "O status de conclusão é obrigatório")
    private Boolean completed;

    private String notes;

    public Integer getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(Integer restSeconds) {
        this.restSeconds = restSeconds;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}