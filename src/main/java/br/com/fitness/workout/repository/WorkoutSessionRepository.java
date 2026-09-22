package br.com.fitness.workout.repository;

import br.com.fitness.workout.model.WorkoutSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository
        extends MongoRepository<WorkoutSession, String> {

    List<WorkoutSession> findByUserIdOrderByStartedAtDesc(
            String userId
    );

    List<WorkoutSession> findByUserIdAndWorkoutIdOrderByStartedAtDesc(
            String userId,
            String workoutId
    );

    Optional<WorkoutSession> findByIdAndUserId(
            String id,
            String userId
    );
}