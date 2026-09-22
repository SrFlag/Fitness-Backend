package br.com.fitness.performance.service;

import br.com.fitness.exercise.model.Exercise;
import br.com.fitness.exercise.repository.ExerciseRepository;
import br.com.fitness.performance.dto.ExercisePerformanceResponse;
import br.com.fitness.performance.dto.PerformanceSessionResponse;
import br.com.fitness.performance.dto.PerformanceSummaryResponse;
import br.com.fitness.performance.dto.WorkoutHistoryResponse;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.model.SetLog;
import br.com.fitness.workout.model.WorkoutExercise;
import br.com.fitness.workout.model.WorkoutSession;
import br.com.fitness.workout.repository.SetLogRepository;
import br.com.fitness.workout.repository.WorkoutExerciseRepository;
import br.com.fitness.workout.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PerformanceService {

    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final SetLogRepository setLogRepository;

    public PerformanceService(
            UserRepository userRepository,
            ExerciseRepository exerciseRepository,
            WorkoutExerciseRepository workoutExerciseRepository,
            WorkoutSessionRepository workoutSessionRepository,
            SetLogRepository setLogRepository
    ) {
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutSessionRepository = workoutSessionRepository;
        this.setLogRepository = setLogRepository;
    }

    public ExercisePerformanceResponse getExercisePerformance(
            String email,
            String exerciseId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() ->
                        new RuntimeException("Exercício não encontrado")
                );

        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUserIdOrderByStartedAtDesc(user.getId());

        List<PerformanceSessionResponse> performanceSessions =
                sessions.stream()
                        .map(session ->
                                calculateSessionPerformance(
                                        session,
                                        exerciseId
                                )
                        )
                        .filter(response -> response != null)
                        .toList();

        Double personalRecord = performanceSessions.stream()
                .map(PerformanceSessionResponse::getMaxWeight)
                .max(Double::compareTo)
                .orElse(0.0);

        return new ExercisePerformanceResponse(
                exercise.getId(),
                exercise.getName(),
                personalRecord,
                performanceSessions
        );
    }

    public PerformanceSummaryResponse getPerformanceSummary(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUserIdOrderByStartedAtDesc(user.getId());

        Integer totalWorkouts = sessions.size();

        List<WorkoutSession> completedSessions = sessions.stream()
                .filter(session ->
                        "COMPLETED".equals(session.getStatus())
                )
                .toList();

        Integer completedWorkouts = completedSessions.size();

        int totalSets = 0;
        double totalVolume = 0.0;

        for (WorkoutSession session : completedSessions) {

            List<SetLog> setLogs =
                    setLogRepository
                            .findByWorkoutSessionIdOrderBySetNumberAsc(
                                    session.getId()
                            );

            for (SetLog setLog : setLogs) {

                if (!Boolean.TRUE.equals(setLog.getCompleted())) {
                    continue;
                }

                totalSets++;

                totalVolume +=
                        setLog.getWeight() *
                                setLog.getRepetitions();
            }
        }

        Integer currentStreak =
                calculateCurrentStreak(sessions);

        return new PerformanceSummaryResponse(
                totalWorkouts,
                completedWorkouts,
                totalSets,
                totalVolume,
                currentStreak
        );
    }

    private PerformanceSessionResponse calculateSessionPerformance(
            WorkoutSession session,
            String exerciseId
    ) {

        List<SetLog> setLogs =
                setLogRepository
                        .findByWorkoutSessionIdOrderBySetNumberAsc(
                                session.getId()
                        );

        List<SetLog> exerciseSetLogs = setLogs.stream()
                .filter(setLog ->
                        isSetFromExercise(
                                setLog,
                                exerciseId
                        )
                )
                .filter(setLog ->
                        Boolean.TRUE.equals(setLog.getCompleted())
                )
                .toList();

        if (exerciseSetLogs.isEmpty()) {
            return null;
        }

        Double maxWeight = exerciseSetLogs.stream()
                .map(SetLog::getWeight)
                .max(Double::compareTo)
                .orElse(0.0);

        Integer totalRepetitions = exerciseSetLogs.stream()
                .mapToInt(SetLog::getRepetitions)
                .sum();

        Double volume = exerciseSetLogs.stream()
                .mapToDouble(setLog ->
                        setLog.getWeight() *
                                setLog.getRepetitions()
                )
                .sum();

        LocalDate date =
                session.getStartedAt().toLocalDate();

        return new PerformanceSessionResponse(
                date,
                maxWeight,
                totalRepetitions,
                volume
        );
    }

    private boolean isSetFromExercise(
            SetLog setLog,
            String exerciseId
    ) {

        WorkoutExercise workoutExercise =
                workoutExerciseRepository
                        .findById(setLog.getWorkoutExerciseId())
                        .orElse(null);

        if (workoutExercise == null) {
            return false;
        }

        return exerciseId.equals(
                workoutExercise.getExerciseId()
        );
    }

    private Integer calculateCurrentStreak(
            List<WorkoutSession> sessions
    ) {

        List<LocalDate> trainingDates = sessions.stream()
                .filter(session ->
                        "COMPLETED".equals(session.getStatus())
                )
                .filter(session ->
                        session.getStartedAt() != null
                )
                .map(session ->
                        session.getStartedAt().toLocalDate()
                )
                .distinct()
                .sorted((date1, date2) ->
                        date2.compareTo(date1)
                )
                .toList();

        if (trainingDates.isEmpty()) {
            return 0;
        }

        int streak = 1;

        for (int i = 1; i < trainingDates.size(); i++) {

            LocalDate previousDate =
                    trainingDates.get(i - 1);

            LocalDate currentDate =
                    trainingDates.get(i);

            if (previousDate.minusDays(1)
                    .equals(currentDate)) {

                streak++;

            } else {
                break;
            }
        }

        return streak;
    }

    public List<WorkoutHistoryResponse> getWorkoutHistory(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUserIdOrderByStartedAtDesc(user.getId());

        return sessions.stream()
                .map(session ->
                        new WorkoutHistoryResponse(
                                session.getId(),
                                session.getWorkoutId(),
                                session.getStartedAt(),
                                session.getFinishedAt(),
                                session.getDurationSeconds(),
                                session.getStatus()
                        )
                )
                .toList();
    }
}