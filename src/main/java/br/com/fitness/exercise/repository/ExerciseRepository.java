package br.com.fitness.exercise.repository;

import br.com.fitness.exercise.model.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {

    List<Exercise> findAllByOrderByNameAsc();

    List<Exercise> findByPrimaryMuscleGroup(String primaryMuscleGroup);

    List<Exercise> findByEquipment(String equipment);

    List<Exercise> findByDifficulty(String difficulty);

    List<Exercise> findByPrimaryMuscleGroupAndEquipment(
            String primaryMuscleGroup,
            String equipment
    );
}