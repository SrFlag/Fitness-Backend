package br.com.fitness.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    private String userId;

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
}