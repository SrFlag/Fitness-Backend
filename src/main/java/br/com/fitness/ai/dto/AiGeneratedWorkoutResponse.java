package br.com.fitness.ai.dto;

import java.util.List;

public class AiGeneratedWorkoutResponse {

    private String workoutId;
    private String name;
    private String goal;
    private Integer estimatedDuration;
    private List<AiWorkoutExercise> exercises;

    public AiGeneratedWorkoutResponse(
            String workoutId,
            String name,
            String goal,
            Integer estimatedDuration,
            List<AiWorkoutExercise> exercises
    ) {
        this.workoutId = workoutId;
        this.name = name;
        this.goal = goal;
        this.estimatedDuration = estimatedDuration;
        this.exercises = exercises;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public List<AiWorkoutExercise> getExercises() {
        return exercises;
    }
}