package br.com.fitness.workout.repository;

import br.com.fitness.workout.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository
        extends MongoRepository<Workout, String> {

    List<Workout> findByUserIdOrderByNameAsc(String userId);

    List<Workout> findByUserIdAndActive(
            String userId,
            Boolean active
    );

    Optional<Workout> findByIdAndUserId(
            String id,
            String userId
    );

    boolean existsByIdAndUserId(
            String id,
            String userId
    );
}