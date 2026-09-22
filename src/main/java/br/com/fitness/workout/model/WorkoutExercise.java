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
@Document(collection = "workout_exercises")
public class WorkoutExercise {

    @Id
    private String id;

    private String workoutId;

    private String exerciseId;

    private Integer exerciseOrder;

    private Integer sets;

    private String repetitions;

    private Integer restSeconds;

    private Double targetWeight;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}