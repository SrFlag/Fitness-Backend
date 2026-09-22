package br.com.fitness.workout.dto;

import br.com.fitness.workout.model.Workout;

import java.time.LocalDateTime;

public class WorkoutResponse {

    private String id;

    private String name;

    private String description;

    private String goal;

    private Integer estimatedDuration;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public WorkoutResponse(
            String id,
            String name,
            String description,
            String goal,
            Integer estimatedDuration,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.goal = goal;
        this.estimatedDuration = estimatedDuration;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static WorkoutResponse from(Workout workout) {

        return new WorkoutResponse(
                workout.getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getGoal(),
                workout.getEstimatedDuration(),
                workout.getActive(),
                workout.getCreatedAt(),
                workout.getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getGoal() {
        return goal;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}