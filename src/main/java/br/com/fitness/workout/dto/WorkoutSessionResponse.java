package br.com.fitness.workout.dto;

import br.com.fitness.workout.model.WorkoutSession;

import java.time.LocalDateTime;

public class WorkoutSessionResponse {

    private String id;
    private String workoutId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Integer durationSeconds;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WorkoutSessionResponse(
            String id,
            String workoutId,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            Integer durationSeconds,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.workoutId = workoutId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.durationSeconds = durationSeconds;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static WorkoutSessionResponse from(
            WorkoutSession session
    ) {

        return new WorkoutSessionResponse(
                session.getId(),
                session.getWorkoutId(),
                session.getStartedAt(),
                session.getFinishedAt(),
                session.getDurationSeconds(),
                session.getStatus(),
                session.getCreatedAt(),
                session.getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}