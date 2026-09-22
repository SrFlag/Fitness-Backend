package br.com.fitness.workout.controller;

import br.com.fitness.workout.dto.CreateWorkoutSessionRequest;
import br.com.fitness.workout.dto.FinishWorkoutSessionRequest;
import br.com.fitness.workout.dto.WorkoutSessionResponse;
import br.com.fitness.workout.service.WorkoutSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    public WorkoutSessionController(
            WorkoutSessionService workoutSessionService
    ) {
        this.workoutSessionService = workoutSessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutSessionResponse startSession(
            @Valid @RequestBody CreateWorkoutSessionRequest request,
            Authentication authentication
    ) {

        return workoutSessionService.startSession(
                authentication.getName(),
                request
        );
    }

    @PutMapping("/{sessionId}/finish")
    public WorkoutSessionResponse finishSession(
            @PathVariable String sessionId,
            @Valid @RequestBody FinishWorkoutSessionRequest request,
            Authentication authentication
    ) {

        return workoutSessionService.finishSession(
                authentication.getName(),
                sessionId,
                request
        );
    }

    @GetMapping
    public List<WorkoutSessionResponse> getMySessions(
            Authentication authentication
    ) {

        return workoutSessionService.getMySessions(
                authentication.getName()
        );
    }

    @GetMapping("/{sessionId}")
    public WorkoutSessionResponse getSession(
            @PathVariable String sessionId,
            Authentication authentication
    ) {

        return workoutSessionService.getSession(
                authentication.getName(),
                sessionId
        );
    }
}