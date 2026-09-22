package br.com.fitness.profile.dto;

import br.com.fitness.profile.model.UserProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserProfileResponse {

    private String id;
    private LocalDate birthDate;
    private Double height;
    private Double weight;
    private String gender;
    private String goal;
    private String experienceLevel;
    private List<Integer> trainingDays;
    private Integer trainingDuration;
    private String trainingLocation;
    private List<String> availableEquipment;
    private List<String> preferredExercises;
    private List<String> restrictions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfileResponse(
            String id,
            LocalDate birthDate,
            Double height,
            Double weight,
            String gender,
            String goal,
            String experienceLevel,
            List<Integer> trainingDays,
            Integer trainingDuration,
            String trainingLocation,
            List<String> availableEquipment,
            List<String> preferredExercises,
            List<String> restrictions,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.goal = goal;
        this.experienceLevel = experienceLevel;
        this.trainingDays = trainingDays;
        this.trainingDuration = trainingDuration;
        this.trainingLocation = trainingLocation;
        this.availableEquipment = availableEquipment;
        this.preferredExercises = preferredExercises;
        this.restrictions = restrictions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserProfileResponse from(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getBirthDate(),
                profile.getHeight(),
                profile.getWeight(),
                profile.getGender(),
                profile.getGoal(),
                profile.getExperienceLevel(),
                profile.getTrainingDays(),
                profile.getTrainingDuration(),
                profile.getTrainingLocation(),
                profile.getAvailableEquipment(),
                profile.getPreferredExercises(),
                profile.getRestrictions(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getGoal() {
        return goal;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public List<Integer> getTrainingDays() {
        return trainingDays;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public List<String> getAvailableEquipment() {
        return availableEquipment;
    }

    public List<String> getPreferredExercises() {
        return preferredExercises;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}