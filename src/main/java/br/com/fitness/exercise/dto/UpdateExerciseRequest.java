package br.com.fitness.exercise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateExerciseRequest {

    @NotBlank(message = "O nome do exercício é obrigatório")
    private String name;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotBlank(message = "O grupo muscular principal é obrigatório")
    private String primaryMuscleGroup;

    private List<String> secondaryMuscleGroups;

    @NotBlank(message = "O equipamento é obrigatório")
    private String equipment;

    @NotBlank(message = "A dificuldade é obrigatória")
    private String difficulty;

    @NotNull(message = "As instruções são obrigatórias")
    private List<String> instructions;

    private List<String> tips;

    private List<String> commonMistakes;

    private List<String> alternatives;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryMuscleGroup() {
        return primaryMuscleGroup;
    }

    public void setPrimaryMuscleGroup(String primaryMuscleGroup) {
        this.primaryMuscleGroup = primaryMuscleGroup;
    }

    public List<String> getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    public void setSecondaryMuscleGroups(List<String> secondaryMuscleGroups) {
        this.secondaryMuscleGroups = secondaryMuscleGroups;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    public List<String> getCommonMistakes() {
        return commonMistakes;
    }

    public void setCommonMistakes(List<String> commonMistakes) {
        this.commonMistakes = commonMistakes;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }
}