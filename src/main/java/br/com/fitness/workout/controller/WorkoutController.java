package br.com.fitness.workout.controller;

import br.com.fitness.workout.dto.CreateWorkoutRequest;
import br.com.fitness.workout.dto.UpdateWorkoutRequest;
import br.com.fitness.workout.dto.WorkoutResponse;
import br.com.fitness.workout.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutResponse createWorkout(
            @Valid @RequestBody CreateWorkoutRequest request,
            Authentication authentication
    ) {

        return workoutService.createWorkout(
                authentication.getName(),
                request
        );
    }

    @PutMapping("/{workoutId}")
    public WorkoutResponse updateWorkout(
            @PathVariable String workoutId,
            @Valid @RequestBody UpdateWorkoutRequest request,
            Authentication authentication
    ) {

        return workoutService.updateWorkout(
                authentication.getName(),
                workoutId,
                request
        );
    }

    @PatchMapping("/{workoutId}/deactivate")
    public WorkoutResponse deactivateWorkout(
            @PathVariable String workoutId,
            Authentication authentication
    ) {

        return workoutService.deactivateWorkout(
                authentication.getName(),
                workoutId
        );
    }

    @GetMapping
    public List<WorkoutResponse> getMyWorkouts(
            Authentication authentication
    ) {

        return workoutService.getMyWorkouts(
                authentication.getName()
        );
    }

    @GetMapping("/active")
    public List<WorkoutResponse> getMyActiveWorkouts(
            Authentication authentication
    ) {

        return workoutService.getMyActiveWorkouts(
                authentication.getName()
        );
    }
}