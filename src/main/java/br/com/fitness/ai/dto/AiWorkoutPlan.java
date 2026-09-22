package br.com.fitness.ai.dto;

import java.util.List;

public class AiWorkoutPlan {

    private String name;
    private String goal;
    private Integer estimatedDuration;
    private List<AiWorkoutExercise> exercises;

    public AiWorkoutPlan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public List<AiWorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<AiWorkoutExercise> exercises) {
        this.exercises = exercises;
    }
}