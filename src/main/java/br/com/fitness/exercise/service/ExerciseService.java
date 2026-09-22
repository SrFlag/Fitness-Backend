package br.com.fitness.exercise.service;

import br.com.fitness.exercise.dto.CreateExerciseRequest;
import br.com.fitness.exercise.dto.ExerciseResponse;
import br.com.fitness.exercise.dto.UpdateExerciseRequest;
import br.com.fitness.exercise.model.Exercise;
import br.com.fitness.exercise.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseResponse createExercise(
            CreateExerciseRequest request
    ) {

        LocalDateTime now = LocalDateTime.now();

        Exercise exercise = Exercise.builder()
                .name(request.getName().trim())
                .description(request.getDescription().trim())
                .primaryMuscleGroup(
                        normalize(request.getPrimaryMuscleGroup())
                )
                .secondaryMuscleGroups(
                        normalizeList(request.getSecondaryMuscleGroups())
                )
                .equipment(
                        normalize(request.getEquipment())
                )
                .difficulty(
                        normalize(request.getDifficulty())
                )
                .instructions(
                        trimList(request.getInstructions())
                )
                .tips(
                        trimList(request.getTips())
                )
                .commonMistakes(
                        trimList(request.getCommonMistakes())
                )
                .alternatives(
                        trimList(request.getAlternatives())
                )
                .createdAt(now)
                .updatedAt(now)
                .build();

        Exercise savedExercise =
                exerciseRepository.save(exercise);

        return ExerciseResponse.from(savedExercise);
    }

    public List<ExerciseResponse> getAllExercises() {

        return exerciseRepository
                .findAllByOrderByNameAsc()
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public ExerciseResponse getExerciseById(String id) {

        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exercício não encontrado"
                        )
                );

        return ExerciseResponse.from(exercise);
    }

    public List<ExerciseResponse> getByPrimaryMuscleGroup(
            String primaryMuscleGroup
    ) {

        String normalizedMuscleGroup =
                normalize(primaryMuscleGroup);

        return exerciseRepository
                .findByPrimaryMuscleGroup(normalizedMuscleGroup)
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public List<ExerciseResponse> getByEquipment(
            String equipment
    ) {

        String normalizedEquipment =
                normalize(equipment);

        return exerciseRepository
                .findByEquipment(normalizedEquipment)
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public List<ExerciseResponse> getByDifficulty(
            String difficulty
    ) {

        String normalizedDifficulty =
                normalize(difficulty);

        return exerciseRepository
                .findByDifficulty(normalizedDifficulty)
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public List<ExerciseResponse> getByMuscleGroupAndEquipment(
            String primaryMuscleGroup,
            String equipment
    ) {

        String normalizedMuscleGroup =
                normalize(primaryMuscleGroup);

        String normalizedEquipment =
                normalize(equipment);

        return exerciseRepository
                .findByPrimaryMuscleGroupAndEquipment(
                        normalizedMuscleGroup,
                        normalizedEquipment
                )
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public ExerciseResponse updateExercise(
            String id,
            UpdateExerciseRequest request
    ) {

        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exercício não encontrado"
                        )
                );

        exercise.setName(
                request.getName().trim()
        );

        exercise.setDescription(
                request.getDescription().trim()
        );

        exercise.setPrimaryMuscleGroup(
                normalize(request.getPrimaryMuscleGroup())
        );

        exercise.setSecondaryMuscleGroups(
                normalizeList(
                        request.getSecondaryMuscleGroups()
                )
        );

        exercise.setEquipment(
                normalize(request.getEquipment())
        );

        exercise.setDifficulty(
                normalize(request.getDifficulty())
        );

        exercise.setInstructions(
                trimList(request.getInstructions())
        );

        exercise.setTips(
                trimList(request.getTips())
        );

        exercise.setCommonMistakes(
                trimList(request.getCommonMistakes())
        );

        exercise.setAlternatives(
                trimList(request.getAlternatives())
        );

        exercise.setUpdatedAt(
                LocalDateTime.now()
        );

        Exercise updatedExercise =
                exerciseRepository.save(exercise);

        return ExerciseResponse.from(updatedExercise);
    }

    public void deleteExercise(String id) {

        if (!exerciseRepository.existsById(id)) {
            throw new RuntimeException(
                    "Exercício não encontrado"
            );
        }

        exerciseRepository.deleteById(id);
    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private List<String> normalizeList(List<String> values) {

        if (values == null) {
            return null;
        }

        return values.stream()
                .map(this::normalize)
                .toList();
    }

    private List<String> trimList(List<String> values) {

        if (values == null) {
            return null;
        }

        return values.stream()
                .map(value -> value == null
                        ? null
                        : value.trim())
                .toList();
    }
}