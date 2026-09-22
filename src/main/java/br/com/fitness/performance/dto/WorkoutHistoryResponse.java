package br.com.fitness.performance.dto;

import java.time.LocalDateTime;

public class WorkoutHistoryResponse {

    private String sessionId;

    private String workoutId;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer durationSeconds;

    private String status;

    public WorkoutHistoryResponse(
            String sessionId,
            String workoutId,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            Integer durationSeconds,
            String status
    ) {
        this.sessionId = sessionId;
        this.workoutId = workoutId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.durationSeconds = durationSeconds;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
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
}