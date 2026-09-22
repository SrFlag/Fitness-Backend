package br.com.fitness.workout.repository;

import br.com.fitness.workout.model.SetLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SetLogRepository
        extends MongoRepository<SetLog, String> {

    List<SetLog> findByWorkoutSessionIdOrderBySetNumberAsc(
            String workoutSessionId
    );

    List<SetLog> findByWorkoutExerciseIdOrderBySetNumberAsc(
            String workoutExerciseId
    );
}