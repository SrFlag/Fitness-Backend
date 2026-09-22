package br.com.fitness.workout.service;

import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.dto.CreateWorkoutRequest;
import br.com.fitness.workout.dto.UpdateWorkoutRequest;
import br.com.fitness.workout.dto.WorkoutResponse;
import br.com.fitness.workout.model.Workout;
import br.com.fitness.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            UserRepository userRepository
    ) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public WorkoutResponse createWorkout(
            String email,
            CreateWorkoutRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        LocalDateTime now = LocalDateTime.now();

        Workout workout = Workout.builder()
                .userId(user.getId())
                .name(request.getName().trim())
                .description(
                        request.getDescription() == null
                                ? null
                                : request.getDescription().trim()
                )
                .goal(request.getGoal().trim().toUpperCase())
                .estimatedDuration(
                        request.getEstimatedDuration()
                )
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Workout savedWorkout =
                workoutRepository.save(workout);

        return WorkoutResponse.from(savedWorkout);
    }

    public List<WorkoutResponse> getMyWorkouts(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        return workoutRepository
                .findByUserIdOrderByNameAsc(user.getId())
                .stream()
                .map(WorkoutResponse::from)
                .toList();
    }

    public List<WorkoutResponse> getMyActiveWorkouts(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        return workoutRepository
                .findByUserIdAndActive(
                        user.getId(),
                        true
                )
                .stream()
                .map(WorkoutResponse::from)
                .toList();
    }

    public WorkoutResponse updateWorkout(
            String email,
            String workoutId,
            UpdateWorkoutRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        Workout workout = workoutRepository.findById(workoutId)
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

        workout.setName(
                request.getName().trim()
        );

        workout.setDescription(
                request.getDescription() == null
                        ? null
                        : request.getDescription().trim()
        );

        workout.setGoal(
                request.getGoal()
                        .trim()
                        .toUpperCase()
        );

        workout.setEstimatedDuration(
                request.getEstimatedDuration()
        );

        workout.setUpdatedAt(
                LocalDateTime.now()
        );

        Workout updatedWorkout =
                workoutRepository.save(workout);

        return WorkoutResponse.from(updatedWorkout);
    }

    public WorkoutResponse deactivateWorkout(
            String email,
            String workoutId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        Workout workout = workoutRepository.findById(workoutId)
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

        workout.setActive(false);
        workout.setUpdatedAt(LocalDateTime.now());

        Workout deactivatedWorkout =
                workoutRepository.save(workout);

        return WorkoutResponse.from(deactivatedWorkout);
    }
}