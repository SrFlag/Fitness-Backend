package br.com.fitness.nutrition.repository;

import br.com.fitness.nutrition.model.MealItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MealItemRepository
        extends MongoRepository<MealItem, String> {

    List<MealItem> findByMealIdOrderByCreatedAtAsc(
            String mealId
    );

    Optional<MealItem> findByIdAndMealId(
            String id,
            String mealId
    );

    boolean existsByIdAndMealId(
            String id,
            String mealId
    );

    void deleteByMealId(
            String mealId
    );
}