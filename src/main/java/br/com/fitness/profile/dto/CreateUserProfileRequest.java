package br.com.fitness.profile.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class CreateUserProfileRequest {

    @Past(message = "A data de nascimento deve estar no passado")
    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate birthDate;

    @NotNull(message = "A altura é obrigatória")
    @Positive(message = "A altura deve ser maior que zero")
    private Double height;

    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser maior que zero")
    private Double weight;

    private String gender;

    @NotNull(message = "O objetivo é obrigatório")
    private String goal;

    @NotNull(message = "O nível de experiência é obrigatório")
    private String experienceLevel;

    @NotNull(message = "Os dias de treino são obrigatórios")
    @Size(
            min = 1,
            max = 7,
            message = "Informe entre 1 e 7 dias de treino"
    )
    private List<
            @Min(value = 1, message = "O dia deve estar entre 1 e 7")
            @Max(value = 7, message = "O dia deve estar entre 1 e 7")
                    Integer
            > trainingDays;

    @NotNull(message = "A duração do treino é obrigatória")
    @Positive(message = "A duração do treino deve ser maior que zero")
    private Integer trainingDuration;

    @NotNull(message = "O local de treino é obrigatório")
    private String trainingLocation;

    private List<String> availableEquipment;

    private List<String> preferredExercises;

    private List<String> restrictions;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public List<Integer> getTrainingDays() {
        return trainingDays;
    }

    public void setTrainingDays(List<Integer> trainingDays) {
        this.trainingDays = trainingDays;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public List<String> getAvailableEquipment() {
        return availableEquipment;
    }

    public void setAvailableEquipment(List<String> availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    public List<String> getPreferredExercises() {
        return preferredExercises;
    }

    public void setPreferredExercises(List<String> preferredExercises) {
        this.preferredExercises = preferredExercises;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }
}