package br.com.fitness.exercise.controller;

import br.com.fitness.exercise.dto.CreateExerciseRequest;
import br.com.fitness.exercise.dto.ExerciseResponse;
import br.com.fitness.exercise.dto.UpdateExerciseRequest;
import br.com.fitness.exercise.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponse createExercise(
            @Valid @RequestBody CreateExerciseRequest request
    ) {
        return exerciseService.createExercise(request);
    }

    @GetMapping
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public ExerciseResponse getExerciseById(
            @PathVariable String id
    ) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("/muscle/{primaryMuscleGroup}")
    public List<ExerciseResponse> getByPrimaryMuscleGroup(
            @PathVariable String primaryMuscleGroup
    ) {
        return exerciseService.getByPrimaryMuscleGroup(
                primaryMuscleGroup
        );
    }

    @GetMapping("/equipment/{equipment}")
    public List<ExerciseResponse> getByEquipment(
            @PathVariable String equipment
    ) {
        return exerciseService.getByEquipment(equipment);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<ExerciseResponse> getByDifficulty(
            @PathVariable String difficulty
    ) {
        return exerciseService.getByDifficulty(difficulty);
    }

    @GetMapping("/filter")
    public List<ExerciseResponse> getByMuscleGroupAndEquipment(
            @RequestParam String muscle,
            @RequestParam String equipment
    ) {
        return exerciseService.getByMuscleGroupAndEquipment(
                muscle,
                equipment
        );
    }

    @PutMapping("/{id}")
    public ExerciseResponse updateExercise(
            @PathVariable String id,
            @Valid @RequestBody UpdateExerciseRequest request
    ) {
        return exerciseService.updateExercise(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(
            @PathVariable String id
    ) {
        exerciseService.deleteExercise(id);
    }
}