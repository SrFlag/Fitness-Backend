package br.com.fitness.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "workouts")
public class Workout {

    @Id
    private String id;

    private String userId;

    private String name;

    private String description;

    private String goal;

    private Integer estimatedDuration;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}