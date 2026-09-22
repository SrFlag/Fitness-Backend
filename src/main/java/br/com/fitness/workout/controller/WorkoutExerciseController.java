package br.com.fitness.workout.controller;

import br.com.fitness.workout.dto.AddWorkoutExerciseRequest;
import br.com.fitness.workout.dto.UpdateWorkoutExerciseRequest;
import br.com.fitness.workout.dto.WorkoutExerciseResponse;
import br.com.fitness.workout.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts/{workoutId}/exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(
            WorkoutExerciseService workoutExerciseService
    ) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutExerciseResponse addExerciseToWorkout(
            @PathVariable String workoutId,
            @Valid @RequestBody AddWorkoutExerciseRequest request,
            Authentication authentication
    ) {

        return workoutExerciseService.addExerciseToWorkout(
                authentication.getName(),
                workoutId,
                request
        );
    }

    @GetMapping
    public List<WorkoutExerciseResponse> getWorkoutExercises(
            @PathVariable String workoutId,
            Authentication authentication
    ) {

        return workoutExerciseService.getWorkoutExercises(
                authentication.getName(),
                workoutId
        );
    }

    @PutMapping("/{workoutExerciseId}")
    public WorkoutExerciseResponse updateWorkoutExercise(
            @PathVariable String workoutId,
            @PathVariable String workoutExerciseId,
            @Valid @RequestBody UpdateWorkoutExerciseRequest request,
            Authentication authentication
    ) {

        return workoutExerciseService.updateWorkoutExercise(
                authentication.getName(),
                workoutId,
                workoutExerciseId,
                request
        );
    }
}