package br.com.fitness.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "exercises")
public class Exercise {

    @Id
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
}