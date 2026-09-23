package br.com.fitness.nutrition.repository;

import br.com.fitness.nutrition.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodRepository
        extends MongoRepository<Food, String> {

    List<Food> findAllByOrderByNameAsc();

    List<Food> findByCategory(String category);

    List<Food> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}