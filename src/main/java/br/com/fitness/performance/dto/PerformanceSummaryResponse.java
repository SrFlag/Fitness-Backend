package br.com.fitness.performance.dto;

public class PerformanceSummaryResponse {

    private Integer totalWorkouts;

    private Integer completedWorkouts;

    private Integer totalSets;

    private Double totalVolume;

    private Integer currentStreak;

    public PerformanceSummaryResponse(
            Integer totalWorkouts,
            Integer completedWorkouts,
            Integer totalSets,
            Double totalVolume,
            Integer currentStreak
    ) {
        this.totalWorkouts = totalWorkouts;
        this.completedWorkouts = completedWorkouts;
        this.totalSets = totalSets;
        this.totalVolume = totalVolume;
        this.currentStreak = currentStreak;
    }

    public Integer getTotalWorkouts() {
        return totalWorkouts;
    }

    public Integer getCompletedWorkouts() {
        return completedWorkouts;
    }

    public Integer getTotalSets() {
        return totalSets;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }
}