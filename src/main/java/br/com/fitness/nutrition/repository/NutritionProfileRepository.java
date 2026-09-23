package br.com.fitness.nutrition.repository;

import br.com.fitness.nutrition.model.NutritionProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NutritionProfileRepository
        extends MongoRepository<NutritionProfile, String> {

    Optional<NutritionProfile> findByUserId(String userId);

    boolean existsByUserId(String userId);
}