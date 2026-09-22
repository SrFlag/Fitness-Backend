package br.com.fitness.exercise.dto;

import br.com.fitness.exercise.model.Exercise;

import java.time.LocalDateTime;
import java.util.List;

public class ExerciseResponse {

    private String id;

    private String name;

    private String description;

    private String primaryMuscleGroup;

    private List<String> secondaryMuscleGroups;

    private String equipment;

    private String difficulty;

    private List<String> instructions;

    private List<String> tips;

    private List<String> commonMistakes;

    private List<String> alternatives;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ExerciseResponse(
            String id,
            String name,
            String description,
            String primaryMuscleGroup,
            List<String> secondaryMuscleGroups,
            String equipment,
            String difficulty,
            List<String> instructions,
            List<String> tips,
            List<String> commonMistakes,
            List<String> alternatives,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.primaryMuscleGroup = primaryMuscleGroup;
        this.secondaryMuscleGroups = secondaryMuscleGroups;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.tips = tips;
        this.commonMistakes = commonMistakes;
        this.alternatives = alternatives;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ExerciseResponse from(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getPrimaryMuscleGroup(),
                exercise.getSecondaryMuscleGroups(),
                exercise.getEquipment(),
                exercise.getDifficulty(),
                exercise.getInstructions(),
                exercise.getTips(),
                exercise.getCommonMistakes(),
                exercise.getAlternatives(),
                exercise.getCreatedAt(),
                exercise.getUpdatedAt()
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

    public String getPrimaryMuscleGroup() {
        return primaryMuscleGroup;
    }

    public List<String> getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<String> getTips() {
        return tips;
    }

    public List<String> getCommonMistakes() {
        return commonMistakes;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}