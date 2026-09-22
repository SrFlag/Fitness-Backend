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
@Document(collection = "set_logs")
public class SetLog {

    @Id
    private String id;

    private String workoutSessionId;

    private String workoutExerciseId;

    private Integer setNumber;

    private Double weight;

    private Integer repetitions;

    private Integer restSeconds;

    private Boolean completed;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}