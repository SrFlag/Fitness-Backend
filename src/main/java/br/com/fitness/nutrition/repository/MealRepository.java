package br.com.fitness.nutrition.repository;

import br.com.fitness.nutrition.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository
        extends MongoRepository<Meal, String> {

    List<Meal> findByUserIdAndDateOrderByCreatedAtAsc(
            String userId,
            LocalDate date
    );

    List<Meal> findByUserIdOrderByDateDescCreatedAtAsc(
            String userId
    );

    Optional<Meal> findByIdAndUserId(
            String id,
            String userId
    );

    boolean existsByIdAndUserId(
            String id,
            String userId
    );
}