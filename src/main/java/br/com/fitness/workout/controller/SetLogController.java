package br.com.fitness.workout.controller;

import br.com.fitness.workout.dto.CreateSetLogRequest;
import br.com.fitness.workout.dto.SetLogResponse;
import br.com.fitness.workout.service.SetLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions/{sessionId}/sets")
public class SetLogController {

    private final SetLogService setLogService;

    public SetLogController(SetLogService setLogService) {
        this.setLogService = setLogService;
    }

    @PostMapping("/{workoutExerciseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SetLogResponse createSetLog(
            @PathVariable String sessionId,
            @PathVariable String workoutExerciseId,
            @Valid @RequestBody CreateSetLogRequest request,
            Authentication authentication
    ) {

        return setLogService.createSetLog(
                authentication.getName(),
                sessionId,
                workoutExerciseId,
                request.getSetNumber(),
                request.getWeight(),
                request.getRepetitions(),
                request.getRestSeconds(),
                request.getCompleted(),
                request.getNotes()
        );
    }

    @GetMapping
    public List<SetLogResponse> getSessionSetLogs(
            @PathVariable String sessionId,
            Authentication authentication
    ) {

        return setLogService.getSessionSetLogs(
                authentication.getName(),
                sessionId
        );
    }
}