package br.com.fitness.performance.dto;

import java.util.List;

public class ExercisePerformanceResponse {

    private String exerciseId;

    private String exerciseName;

    private Double personalRecord;

    private List<PerformanceSessionResponse> sessions;

    public ExercisePerformanceResponse(
            String exerciseId,
            String exerciseName,
            Double personalRecord,
            List<PerformanceSessionResponse> sessions
    ) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.personalRecord = personalRecord;
        this.sessions = sessions;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public Double getPersonalRecord() {
        return personalRecord;
    }

    public List<PerformanceSessionResponse> getSessions() {
        return sessions;
    }
}