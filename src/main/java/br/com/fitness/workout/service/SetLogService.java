package br.com.fitness.workout.service;

import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.dto.SetLogResponse;
import br.com.fitness.workout.model.SetLog;
import br.com.fitness.workout.model.WorkoutExercise;
import br.com.fitness.workout.model.WorkoutSession;
import br.com.fitness.workout.repository.SetLogRepository;
import br.com.fitness.workout.repository.WorkoutExerciseRepository;
import br.com.fitness.workout.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetLogService {

    private final SetLogRepository setLogRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final UserRepository userRepository;

    public SetLogService(
            SetLogRepository setLogRepository,
            WorkoutSessionRepository workoutSessionRepository,
            WorkoutExerciseRepository workoutExerciseRepository,
            UserRepository userRepository
    ) {
        this.setLogRepository = setLogRepository;
        this.workoutSessionRepository = workoutSessionRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.userRepository = userRepository;
    }

    public SetLogResponse createSetLog(
            String email,
            String sessionId,
            String workoutExerciseId,
            Integer setNumber,
            Double weight,
            Integer repetitions,
            Integer restSeconds,
            Boolean completed,
            String notes
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        WorkoutSession session = workoutSessionRepository
                .findByIdAndUserId(sessionId, user.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sessão não encontrada"
                        )
                );

        if (!"IN_PROGRESS".equals(session.getStatus())) {
            throw new RuntimeException(
                    "A sessão não está em andamento"
            );
        }

        WorkoutExercise workoutExercise =
                workoutExerciseRepository.findById(
                                workoutExerciseId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exercício do treino não encontrado"
                                )
                        );

        if (!workoutExercise
                .getWorkoutId()
                .equals(session.getWorkoutId())) {

            throw new RuntimeException(
                    "O exercício não pertence ao treino da sessão"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        SetLog setLog = SetLog.builder()
                .workoutSessionId(sessionId)
                .workoutExerciseId(workoutExerciseId)
                .setNumber(setNumber)
                .weight(weight)
                .repetitions(repetitions)
                .restSeconds(restSeconds)
                .completed(completed)
                .notes(
                        notes == null
                                ? null
                                : notes.trim()
                )
                .createdAt(now)
                .updatedAt(now)
                .build();

        SetLog savedSetLog =
                setLogRepository.save(setLog);

        return SetLogResponse.from(savedSetLog);
    }

    public List<SetLogResponse> getSessionSetLogs(
            String email,
            String sessionId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        workoutSessionRepository
                .findByIdAndUserId(
                        sessionId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sessão não encontrada"
                        )
                );

        return setLogRepository
                .findByWorkoutSessionIdOrderBySetNumberAsc(
                        sessionId
                )
                .stream()
                .map(SetLogResponse::from)
                .toList();
    }

    public List<SetLogResponse> getExerciseSetLogs(
            String email,
            String workoutExerciseId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        List<SetLog> setLogs = setLogRepository
                .findByWorkoutExerciseIdOrderBySetNumberAsc(
                        workoutExerciseId
                );

        if (setLogs.isEmpty()) {
            return List.of();
        }

        String sessionId =
                setLogs.get(0).getWorkoutSessionId();

        workoutSessionRepository
                .findByIdAndUserId(
                        sessionId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sessão não encontrada"
                        )
                );

        return setLogs
                .stream()
                .map(SetLogResponse::from)
                .toList();
    }
}