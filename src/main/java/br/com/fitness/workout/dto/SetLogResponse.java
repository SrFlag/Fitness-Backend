package br.com.fitness.workout.dto;

import br.com.fitness.workout.model.SetLog;

import java.time.LocalDateTime;

public class SetLogResponse {

    private String id;
    private String workoutSessionId;
    private String workoutExerciseId;
    private Integer setNumber;
    private Double weight;
    private Integer repetitions;
    private Integer restSeconds;
    private Boolean completed;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SetLogResponse(
            String id,
            String workoutSessionId,
            String workoutExerciseId,
            Integer setNumber,
            Double weight,
            Integer repetitions,
            Integer restSeconds,
            Boolean completed,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.workoutSessionId = workoutSessionId;
        this.workoutExerciseId = workoutExerciseId;
        this.setNumber = setNumber;
        this.weight = weight;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.completed = completed;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SetLogResponse from(SetLog setLog) {

        return new SetLogResponse(
                setLog.getId(),
                setLog.getWorkoutSessionId(),
                setLog.getWorkoutExerciseId(),
                setLog.getSetNumber(),
                setLog.getWeight(),
                setLog.getRepetitions(),
                setLog.getRestSeconds(),
                setLog.getCompleted(),
                setLog.getNotes(),
                setLog.getCreatedAt(),
                setLog.getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public String getWorkoutSessionId() {
        return workoutSessionId;
    }

    public String getWorkoutExerciseId() {
        return workoutExerciseId;
    }

    public Integer getSetNumber() {
        return setNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}