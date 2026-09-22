package br.com.fitness.workout.service;

import br.com.fitness.exercise.repository.ExerciseRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.dto.AddWorkoutExerciseRequest;
import br.com.fitness.workout.dto.UpdateWorkoutExerciseRequest;
import br.com.fitness.workout.dto.WorkoutExerciseResponse;
import br.com.fitness.workout.model.Workout;
import br.com.fitness.workout.model.WorkoutExercise;
import br.com.fitness.workout.repository.WorkoutExerciseRepository;
import br.com.fitness.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutExerciseService(
            WorkoutExerciseRepository workoutExerciseRepository,
            WorkoutRepository workoutRepository,
            ExerciseRepository exerciseRepository,
            UserRepository userRepository
    ) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public WorkoutExerciseResponse addExerciseToWorkout(
            String email,
            String workoutId,
            AddWorkoutExerciseRequest request
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

        exerciseRepository
                .findById(request.getExerciseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exercício não encontrado"
                        )
                );

        if (workoutExerciseRepository
                .existsByWorkoutIdAndExerciseId(
                        workoutId,
                        request.getExerciseId()
                )) {

            throw new RuntimeException(
                    "O exercício já está adicionado ao treino"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        WorkoutExercise workoutExercise =
                WorkoutExercise.builder()
                        .workoutId(workoutId)
                        .exerciseId(request.getExerciseId())
                        .exerciseOrder(
                                request.getExerciseOrder()
                        )
                        .sets(request.getSets())
                        .repetitions(
                                request.getRepetitions().trim()
                        )
                        .restSeconds(
                                request.getRestSeconds()
                        )
                        .targetWeight(
                                request.getTargetWeight()
                        )
                        .notes(
                                request.getNotes() == null
                                        ? null
                                        : request.getNotes().trim()
                        )
                        .createdAt(now)
                        .updatedAt(now)
                        .build();

        WorkoutExercise savedWorkoutExercise =
                workoutExerciseRepository.save(
                        workoutExercise
                );

        return WorkoutExerciseResponse.from(
                savedWorkoutExercise
        );
    }

    public List<WorkoutExerciseResponse> getWorkoutExercises(
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

        return workoutExerciseRepository
                .findByWorkoutIdOrderByExerciseOrderAsc(
                        workoutId
                )
                .stream()
                .map(WorkoutExerciseResponse::from)
                .toList();
    }

    public WorkoutExerciseResponse updateWorkoutExercise(
            String email,
            String workoutId,
            String workoutExerciseId,
            UpdateWorkoutExerciseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        List<Workout> userWorkouts =
                workoutRepository.findByUserIdOrderByNameAsc(
                        user.getId()
                );

        Workout workout = userWorkouts.stream()
                .filter(item ->
                        item.getId() != null &&
                                item.getId().equals(workoutId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Treino não encontrado"
                        )
                );

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
                .equals(workout.getId())) {

            throw new RuntimeException(
                    "O exercício não pertence ao treino"
            );
        }

        workoutExercise.setExerciseOrder(
                request.getExerciseOrder()
        );

        workoutExercise.setSets(
                request.getSets()
        );

        workoutExercise.setRepetitions(
                request.getRepetitions().trim()
        );

        workoutExercise.setRestSeconds(
                request.getRestSeconds()
        );

        workoutExercise.setTargetWeight(
                request.getTargetWeight()
        );

        workoutExercise.setNotes(
                request.getNotes() == null
                        ? null
                        : request.getNotes().trim()
        );

        workoutExercise.setUpdatedAt(
                LocalDateTime.now()
        );

        WorkoutExercise updatedWorkoutExercise =
                workoutExerciseRepository.save(
                        workoutExercise
                );

        return WorkoutExerciseResponse.from(
                updatedWorkoutExercise
        );
    }
}