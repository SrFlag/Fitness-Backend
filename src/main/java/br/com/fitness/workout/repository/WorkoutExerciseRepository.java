package br.com.fitness.workout.repository;

import br.com.fitness.workout.model.WorkoutExercise;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkoutExerciseRepository
        extends MongoRepository<WorkoutExercise, String> {

    List<WorkoutExercise> findByWorkoutIdOrderByExerciseOrderAsc(
            String workoutId
    );

    boolean existsByWorkoutIdAndExerciseId(
            String workoutId,
            String exerciseId
    );

    boolean existsByIdAndWorkoutId(
            String id,
            String workoutId
    );

    void deleteByWorkoutId(String workoutId);
}