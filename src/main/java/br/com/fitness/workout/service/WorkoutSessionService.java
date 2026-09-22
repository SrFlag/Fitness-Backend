package br.com.fitness.workout.service;

import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.dto.CreateWorkoutSessionRequest;
import br.com.fitness.workout.dto.FinishWorkoutSessionRequest;
import br.com.fitness.workout.dto.WorkoutSessionResponse;
import br.com.fitness.workout.model.Workout;
import br.com.fitness.workout.model.WorkoutSession;
import br.com.fitness.workout.repository.WorkoutRepository;
import br.com.fitness.workout.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutSessionService(
            WorkoutSessionRepository workoutSessionRepository,
            WorkoutRepository workoutRepository,
            UserRepository userRepository
    ) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public WorkoutSessionResponse startSession(
            String email,
            CreateWorkoutSessionRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        Workout workout = workoutRepository
                .findById(request.getWorkoutId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Treino não encontrado"
                        )
                );

        if (!workout.getUserId().equals(user.getId())) {
            throw new RuntimeException(
                    "O treino não pertence ao usuário"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        WorkoutSession session = WorkoutSession.builder()
                .userId(user.getId())
                .workoutId(workout.getId())
                .startedAt(now)
                .status("IN_PROGRESS")
                .createdAt(now)
                .updatedAt(now)
                .build();

        WorkoutSession savedSession =
                workoutSessionRepository.save(session);

        return WorkoutSessionResponse.from(savedSession);
    }

    public WorkoutSessionResponse finishSession(
            String email,
            String sessionId,
            FinishWorkoutSessionRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        WorkoutSession session = workoutSessionRepository
                .findByIdAndUserId(
                        sessionId,
                        user.getId()
                )
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

        LocalDateTime now = LocalDateTime.now();

        session.setFinishedAt(now);

        session.setDurationSeconds(
                request.getDurationSeconds()
        );

        session.setStatus("COMPLETED");

        session.setUpdatedAt(now);

        WorkoutSession updatedSession =
                workoutSessionRepository.save(session);

        return WorkoutSessionResponse.from(updatedSession);
    }

    public List<WorkoutSessionResponse> getMySessions(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        return workoutSessionRepository
                .findByUserIdOrderByStartedAtDesc(
                        user.getId()
                )
                .stream()
                .map(WorkoutSessionResponse::from)
                .toList();
    }

    public WorkoutSessionResponse getSession(
            String email,
            String sessionId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        WorkoutSession session =
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

        return WorkoutSessionResponse.from(session);
    }
}