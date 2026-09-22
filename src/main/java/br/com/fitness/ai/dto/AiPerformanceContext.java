package br.com.fitness.ai.dto;

import java.util.List;

public class AiPerformanceContext {

    private String exerciseId;
    private String exerciseName;
    private Double personalRecord;
    private List<AiPerformanceSession> recentSessions;

    public AiPerformanceContext() {
    }

    public AiPerformanceContext(
            String exerciseId,
            String exerciseName,
            Double personalRecord,
            List<AiPerformanceSession> recentSessions
    ) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.personalRecord = personalRecord;
        this.recentSessions = recentSessions;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Double getPersonalRecord() {
        return personalRecord;
    }

    public void setPersonalRecord(Double personalRecord) {
        this.personalRecord = personalRecord;
    }

    public List<AiPerformanceSession> getRecentSessions() {
        return recentSessions;
    }

    public void setRecentSessions(
            List<AiPerformanceSession> recentSessions
    ) {
        this.recentSessions = recentSessions;
    }

    public static class AiPerformanceSession {

        private String date;
        private Double maxWeight;
        private Integer totalRepetitions;
        private Double volume;

        public AiPerformanceSession() {
        }

        public AiPerformanceSession(
                String date,
                Double maxWeight,
                Integer totalRepetitions,
                Double volume
        ) {
            this.date = date;
            this.maxWeight = maxWeight;
            this.totalRepetitions = totalRepetitions;
            this.volume = volume;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Double getMaxWeight() {
            return maxWeight;
        }

        public void setMaxWeight(Double maxWeight) {
            this.maxWeight = maxWeight;
        }

        public Integer getTotalRepetitions() {
            return totalRepetitions;
        }

        public void setTotalRepetitions(
                Integer totalRepetitions
        ) {
            this.totalRepetitions = totalRepetitions;
        }

        public Double getVolume() {
            return volume;
        }

        public void setVolume(Double volume) {
            this.volume = volume;
        }
    }
}