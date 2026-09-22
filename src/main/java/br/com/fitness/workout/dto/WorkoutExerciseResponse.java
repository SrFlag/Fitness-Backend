package br.com.fitness.workout.dto;

import br.com.fitness.workout.model.WorkoutExercise;

import java.time.LocalDateTime;

public class WorkoutExerciseResponse {

    private String id;

    private String workoutId;

    private String exerciseId;

    private Integer exerciseOrder;

    private Integer sets;

    private String repetitions;

    private Integer restSeconds;

    private Double targetWeight;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public WorkoutExerciseResponse(
            String id,
            String workoutId,
            String exerciseId,
            Integer exerciseOrder,
            Integer sets,
            String repetitions,
            Integer restSeconds,
            Double targetWeight,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.exerciseOrder = exerciseOrder;
        this.sets = sets;
        this.repetitions = repetitions;
        this.restSeconds = restSeconds;
        this.targetWeight = targetWeight;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static WorkoutExerciseResponse from(
            WorkoutExercise workoutExercise
    ) {

        return new WorkoutExerciseResponse(
                workoutExercise.getId(),
                workoutExercise.getWorkoutId(),
                workoutExercise.getExerciseId(),
                workoutExercise.getExerciseOrder(),
                workoutExercise.getSets(),
                workoutExercise.getRepetitions(),
                workoutExercise.getRestSeconds(),
                workoutExercise.getTargetWeight(),
                workoutExercise.getNotes(),
                workoutExercise.getCreatedAt(),
                workoutExercise.getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public Integer getExerciseOrder() {
        return exerciseOrder;
    }

    public Integer getSets() {
        return sets;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public Double getTargetWeight() {
        return targetWeight;
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