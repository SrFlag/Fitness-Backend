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
@Document(collection = "workout_sessions")
public class WorkoutSession {

    @Id
    private String id;

    private String userId;

    private String workoutId;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer durationSeconds;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}