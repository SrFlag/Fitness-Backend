package br.com.fitness.ai.service;

import br.com.fitness.ai.dto.AiGeneratedWorkoutRequest;
import br.com.fitness.ai.dto.AiGeneratedWorkoutResponse;
import br.com.fitness.ai.dto.AiPerformanceContext;
import br.com.fitness.ai.dto.AiWorkoutExercise;
import br.com.fitness.ai.dto.AiWorkoutPlan;
import br.com.fitness.ai.integration.gemini.GeminiService;
import br.com.fitness.exercise.model.Exercise;
import br.com.fitness.exercise.repository.ExerciseRepository;
import br.com.fitness.profile.model.UserProfile;
import br.com.fitness.profile.repository.UserProfileRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import br.com.fitness.workout.model.SetLog;
import br.com.fitness.workout.model.Workout;
import br.com.fitness.workout.model.WorkoutExercise;
import br.com.fitness.workout.model.WorkoutSession;
import br.com.fitness.workout.repository.SetLogRepository;
import br.com.fitness.workout.repository.WorkoutExerciseRepository;
import br.com.fitness.workout.repository.WorkoutRepository;
import br.com.fitness.workout.repository.WorkoutSessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final GeminiService geminiService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final SetLogRepository setLogRepository;
    private final ObjectMapper objectMapper;

    public AiService(
            GeminiService geminiService,
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            ExerciseRepository exerciseRepository,
            WorkoutRepository workoutRepository,
            WorkoutExerciseRepository workoutExerciseRepository,
            WorkoutSessionRepository workoutSessionRepository,
            SetLogRepository setLogRepository,
            ObjectMapper objectMapper
    ) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutSessionRepository = workoutSessionRepository;
        this.setLogRepository = setLogRepository;
        this.objectMapper = objectMapper;
    }

    public String analyzeProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        String prompt = """
                Analise o seguinte perfil de treino.

                Objetivo:
                %s

                Nível de experiência:
                %s

                Dias disponíveis:
                %s

                Duração do treino:
                %s minutos

                Local:
                %s

                Equipamentos:
                %s

                Exercícios preferidos:
                %s

                Restrições:
                %s

                Gere uma análise objetiva contendo:

                1. Características principais do perfil
                2. Pontos importantes para montagem dos treinos
                3. Cuidados que devem ser considerados
                4. Estratégia geral recomendada

                Não invente informações que não estejam presentes no perfil.
                """.formatted(
                profile.getGoal(),
                profile.getExperienceLevel(),
                profile.getTrainingDays(),
                profile.getTrainingDuration(),
                profile.getTrainingLocation(),
                profile.getAvailableEquipment(),
                profile.getPreferredExercises(),
                profile.getRestrictions()
        );

        return geminiService.generateText(prompt);
    }

    public AiGeneratedWorkoutResponse generateWorkout(
            String email,
            AiGeneratedWorkoutRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        List<Exercise> exercises = getRelevantExercises(profile);

        if (exercises.isEmpty()) {
            throw new RuntimeException(
                    "Nenhum exercício compatível foi encontrado para o perfil"
            );
        }

        List<AiPerformanceContext> performanceContext =
                getPerformanceContext(
                        user.getId(),
                        exercises
                );

        String prompt = buildWorkoutPrompt(
                profile,
                exercises,
                performanceContext,
                request
        );

        String aiResponse = geminiService.generateText(prompt);

        String cleanedJson = cleanJsonResponse(aiResponse);

        AiWorkoutPlan plan;

        try {
            plan = objectMapper.readValue(
                    cleanedJson,
                    AiWorkoutPlan.class
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "A IA retornou um formato de treino inválido: "
                            + e.getMessage()
            );
        }

        validateWorkoutPlan(
                plan,
                profile,
                exercises
        );

        validateTargetWeights(
                plan,
                performanceContext
        );

        Workout workout = saveWorkout(
                user.getId(),
                plan
        );

        List<WorkoutExercise> workoutExercises =
                saveWorkoutExercises(
                        workout.getId(),
                        plan.getExercises()
                );

        List<AiWorkoutExercise> responseExercises =
                plan.getExercises();

        return new AiGeneratedWorkoutResponse(
                workout.getId(),
                workout.getName(),
                workout.getGoal(),
                workout.getEstimatedDuration(),
                responseExercises
        );
    }

    private List<Exercise> getRelevantExercises(
            UserProfile profile
    ) {

        List<Exercise> allExercises =
                exerciseRepository.findAllByOrderByNameAsc();

        if (
                profile.getAvailableEquipment() == null
                        || profile.getAvailableEquipment().isEmpty()
        ) {
            return allExercises;
        }

        Set<String> availableEquipment =
                profile.getAvailableEquipment()
                        .stream()
                        .filter(e -> e != null)
                        .map(e -> e.trim().toUpperCase(Locale.ROOT))
                        .collect(Collectors.toSet());

        List<Exercise> compatibleExercises =
                allExercises.stream()
                        .filter(exercise ->
                                exercise.getEquipment() != null
                                        && availableEquipment.contains(
                                        exercise.getEquipment()
                                                .trim()
                                                .toUpperCase(Locale.ROOT)
                                )
                        )
                        .toList();

        if (compatibleExercises.isEmpty()) {
            return allExercises;
        }

        return compatibleExercises;
    }

    private List<AiPerformanceContext> getPerformanceContext(
            String userId,
            List<Exercise> exercises
    ) {

        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUserIdOrderByStartedAtDesc(userId);

        List<WorkoutSession> completedSessions =
                sessions.stream()
                        .filter(session ->
                                "COMPLETED".equalsIgnoreCase(
                                        session.getStatus()
                                )
                        )
                        .toList();

        List<AiPerformanceContext> contexts =
                new ArrayList<>();

        for (Exercise exercise : exercises) {

            List<AiPerformanceContext.AiPerformanceSession>
                    performanceSessions =
                    new ArrayList<>();

            Double personalRecord = null;

            for (WorkoutSession session : completedSessions) {

                List<SetLog> logs =
                        setLogRepository
                                .findByWorkoutSessionIdOrderBySetNumberAsc(
                                        session.getId()
                                );

                List<SetLog> exerciseLogs =
                        logs.stream()
                                .filter(log ->
                                        isSetFromExercise(
                                                log,
                                                exercise.getId()
                                        )
                                )
                                .filter(log ->
                                        Boolean.TRUE.equals(
                                                log.getCompleted()
                                        )
                                )
                                .toList();

                if (exerciseLogs.isEmpty()) {
                    continue;
                }

                Double maxWeight =
                        exerciseLogs.stream()
                                .map(SetLog::getWeight)
                                .filter(weight -> weight != null)
                                .max(Double::compareTo)
                                .orElse(null);

                Integer totalRepetitions =
                        exerciseLogs.stream()
                                .map(SetLog::getRepetitions)
                                .filter(repetitions -> repetitions != null)
                                .mapToInt(Integer::intValue)
                                .sum();

                Double volume =
                        exerciseLogs.stream()
                                .filter(log ->
                                        log.getWeight() != null
                                                && log.getRepetitions() != null
                                )
                                .mapToDouble(log ->
                                        log.getWeight()
                                                * log.getRepetitions()
                                )
                                .sum();

                if (maxWeight != null) {

                    if (
                            personalRecord == null
                                    || maxWeight > personalRecord
                    ) {
                        personalRecord = maxWeight;
                    }
                }

                String date =
                        session.getStartedAt() != null
                                ? session.getStartedAt().toString()
                                : null;

                performanceSessions.add(
                        new AiPerformanceContext.AiPerformanceSession(
                                date,
                                maxWeight,
                                totalRepetitions,
                                volume
                        )
                );
            }

            if (performanceSessions.size() > 5) {
                performanceSessions =
                        performanceSessions.subList(
                                0,
                                5
                        );
            }

            contexts.add(
                    new AiPerformanceContext(
                            exercise.getId(),
                            exercise.getName(),
                            personalRecord,
                            performanceSessions
                    )
            );
        }

        return contexts;
    }

    private boolean isSetFromExercise(
            SetLog log,
            String exerciseId
    ) {

        if (log.getWorkoutExerciseId() == null) {
            return false;
        }

        return workoutExerciseRepository
                .findById(log.getWorkoutExerciseId())
                .map(workoutExercise ->
                        exerciseId.equals(
                                workoutExercise.getExerciseId()
                        )
                )
                .orElse(false);
    }

    private String buildWorkoutPrompt(
            UserProfile profile,
            List<Exercise> exercises,
            List<AiPerformanceContext> performanceContext,
            AiGeneratedWorkoutRequest request
    ) {

        String exerciseData =
                exercises.stream()
                        .map(exercise -> """
                                ID: %s
                                Nome: %s
                                Grupo muscular principal: %s
                                Grupos musculares secundários: %s
                                Equipamento: %s
                                Dificuldade: %s
                                Instruções: %s
                                Dicas: %s
                                Erros comuns: %s
                                """.formatted(
                                exercise.getId(),
                                exercise.getName(),
                                exercise.getPrimaryMuscleGroup(),
                                exercise.getSecondaryMuscleGroups(),
                                exercise.getEquipment(),
                                exercise.getDifficulty(),
                                exercise.getInstructions(),
                                exercise.getTips(),
                                exercise.getCommonMistakes()
                        ))
                        .collect(Collectors.joining("\n"));

        String performanceData =
                formatPerformanceContext(
                        performanceContext
                );

        return """
                Você é um especialista em prescrição de treinos.

                Gere um treino personalizado utilizando EXCLUSIVAMENTE
                os exercícios fornecidos pelo backend.

                Nome solicitado:
                %s

                PERFIL DO USUÁRIO

                Objetivo:
                %s

                Nível:
                %s

                Dias de treino:
                %s

                Duração desejada:
                %s minutos

                Local:
                %s

                Equipamentos disponíveis:
                %s

                Exercícios preferidos:
                %s

                Restrições:
                %s


                EXERCÍCIOS DISPONÍVEIS

                %s


                HISTÓRICO REAL DE DESEMPENHO

                %s


                REGRAS IMPORTANTES

                1. Utilize somente os exercícios fornecidos.
                2. Nunca invente exerciseId.
                3. O exerciseId deve ser exatamente o ID fornecido.
                4. Respeite os equipamentos disponíveis.
                5. Respeite o nível de experiência.
                6. Considere o objetivo do usuário.
                7. Considere as restrições informadas.
                8. Gere entre 4 e 8 exercícios.
                9. Não repita o mesmo exerciseId.
                10. O treino deve respeitar aproximadamente a duração desejada.
                11. Utilize o histórico de desempenho quando disponível.
                12. Não invente histórico de treinamento.
                13. O personalRecord informado é real e vem do backend.
                14. Utilize o histórico para definir uma progressão coerente.
                15. targetWeight representa a carga planejada para o exercício.
                16. Não aumente agressivamente a carga em relação ao histórico.
                17. Se não houver histórico suficiente para definir uma carga,
                    targetWeight deve ser null.
                18. targetWeight nunca deve ser negativo.
                19. A carga planejada não significa que o usuário
                    necessariamente conseguirá executar todas as séries.
                20. Retorne SOMENTE JSON válido.
                21. Não utilize markdown.
                22. Não escreva explicações antes ou depois do JSON.


                FORMATO OBRIGATÓRIO

                {
                  "name": "Nome do treino",
                  "goal": "Objetivo",
                  "estimatedDuration": 60,
                  "exercises": [
                    {
                      "exerciseId": "ID_REAL",
                      "sets": 4,
                      "repetitions": 10,
                      "restSeconds": 90,
                      "targetWeight": 27.5
                    }
                  ]
                }
                """.formatted(
                request != null
                        && request.getWorkoutName() != null
                        && !request.getWorkoutName().isBlank()
                        ? request.getWorkoutName()
                        : "Treino IA",

                profile.getGoal(),
                profile.getExperienceLevel(),
                profile.getTrainingDays(),
                profile.getTrainingDuration(),
                profile.getTrainingLocation(),
                profile.getAvailableEquipment(),
                profile.getPreferredExercises(),
                profile.getRestrictions(),
                exerciseData,
                performanceData
        );
    }

    private String formatPerformanceContext(
            List<AiPerformanceContext> contexts
    ) {

        if (contexts == null || contexts.isEmpty()) {
            return "Nenhum histórico disponível.";
        }

        StringBuilder builder = new StringBuilder();

        for (AiPerformanceContext context : contexts) {

            builder.append("\n");

            builder.append("Exercício: ")
                    .append(context.getExerciseName())
                    .append("\n");

            builder.append("exerciseId: ")
                    .append(context.getExerciseId())
                    .append("\n");

            builder.append("Personal Record: ")
                    .append(
                            context.getPersonalRecord() != null
                                    ? context.getPersonalRecord()
                                    : "Sem histórico"
                    )
                    .append("\n");

            if (
                    context.getRecentSessions() == null
                            || context.getRecentSessions().isEmpty()
            ) {

                builder.append(
                        "Sessões recentes: nenhuma\n"
                );

                continue;
            }

            builder.append("Sessões recentes:\n");

            for (
                    AiPerformanceContext.AiPerformanceSession session
                    : context.getRecentSessions()
            ) {

                builder.append(" - Data: ")
                        .append(session.getDate())
                        .append(", carga máxima: ")
                        .append(session.getMaxWeight())
                        .append(" kg, repetições: ")
                        .append(session.getTotalRepetitions())
                        .append(", volume: ")
                        .append(session.getVolume())
                        .append("\n");
            }
        }

        return builder.toString();
    }

    private void validateWorkoutPlan(
            AiWorkoutPlan plan,
            UserProfile profile,
            List<Exercise> availableExercises
    ) {

        if (plan == null) {
            throw new RuntimeException(
                    "A IA não retornou um plano de treino"
            );
        }

        if (
                plan.getName() == null
                        || plan.getName().isBlank()
        ) {
            throw new RuntimeException(
                    "O treino gerado não possui nome"
            );
        }

        if (
                plan.getGoal() == null
                        || plan.getGoal().isBlank()
        ) {
            throw new RuntimeException(
                    "O treino gerado não possui objetivo"
            );
        }

        if (
                plan.getEstimatedDuration() == null
                        || plan.getEstimatedDuration() <= 0
        ) {
            throw new RuntimeException(
                    "A duração do treino é inválida"
            );
        }

        Integer desiredDuration =
                profile.getTrainingDuration();

        if (desiredDuration != null) {

            double minimum =
                    desiredDuration * 0.75;

            double maximum =
                    desiredDuration * 1.25;

            if (
                    plan.getEstimatedDuration() < minimum
                            || plan.getEstimatedDuration() > maximum
            ) {

                throw new RuntimeException(
                        "A duração do treino gerado está fora "
                                + "do intervalo permitido"
                );
            }
        }

        if (
                plan.getExercises() == null
                        || plan.getExercises().size() < 4
                        || plan.getExercises().size() > 8
        ) {

            throw new RuntimeException(
                    "O treino deve possuir entre 4 e 8 exercícios"
            );
        }

        Set<String> availableExerciseIds =
                availableExercises.stream()
                        .map(Exercise::getId)
                        .collect(Collectors.toSet());

        Set<String> usedExerciseIds =
                new HashSet<>();

        for (
                AiWorkoutExercise exercise
                : plan.getExercises()
        ) {

            if (
                    exercise.getExerciseId() == null
                            || exercise.getExerciseId().isBlank()
            ) {

                throw new RuntimeException(
                        "Um exercício gerado não possui exerciseId"
                );
            }

            if (
                    !availableExerciseIds.contains(
                            exercise.getExerciseId()
                    )
            ) {

                throw new RuntimeException(
                        "A IA tentou utilizar um exercício "
                                + "que não está disponível: "
                                + exercise.getExerciseId()
                );
            }

            if (
                    !usedExerciseIds.add(
                            exercise.getExerciseId()
                    )
            ) {

                throw new RuntimeException(
                        "O treino possui exercícios duplicados: "
                                + exercise.getExerciseId()
                );
            }

            if (
                    exercise.getSets() == null
                            || exercise.getSets() <= 0
            ) {

                throw new RuntimeException(
                        "Quantidade de séries inválida"
                );
            }

            if (
                    exercise.getRepetitions() == null
                            || exercise.getRepetitions() <= 0
            ) {

                throw new RuntimeException(
                        "Quantidade de repetições inválida"
                );
            }

            if (
                    exercise.getRestSeconds() == null
                            || exercise.getRestSeconds() < 0
            ) {

                throw new RuntimeException(
                        "Tempo de descanso inválido"
                );
            }

            if (
                    exercise.getTargetWeight() != null
                            && exercise.getTargetWeight() < 0
            ) {

                throw new RuntimeException(
                        "targetWeight não pode ser negativo"
                );
            }
        }
    }

    private void validateTargetWeights(
            AiWorkoutPlan plan,
            List<AiPerformanceContext> performanceContext
    ) {

        if (plan.getExercises() == null) {
            return;
        }

        for (
                AiWorkoutExercise aiExercise
                : plan.getExercises()
        ) {

            Double targetWeight =
                    aiExercise.getTargetWeight();

            /*
             * Se a IA não informou carga,
             * não temos nada para validar.
             */
            if (targetWeight == null) {
                continue;
            }

            AiPerformanceContext context =
                    performanceContext.stream()
                            .filter(item ->
                                    aiExercise.getExerciseId()
                                            .equals(
                                                    item.getExerciseId()
                                            )
                            )
                            .findFirst()
                            .orElse(null);

            /*
             * Se não existe contexto para o exercício,
             * a carga não pode ser validada.
             */
            if (context == null) {

                throw new RuntimeException(
                        "A IA informou targetWeight para o exercício "
                                + aiExercise.getExerciseId()
                                + ", mas o backend não encontrou "
                                + "o contexto de desempenho."
                );
            }

            /*
             * Se não existe histórico real,
             * a IA não pode inventar uma carga.
             */
            if (
                    context.getRecentSessions() == null
                            || context.getRecentSessions().isEmpty()
            ) {

                throw new RuntimeException(
                        "A IA informou targetWeight para o exercício "
                                + aiExercise.getExerciseId()
                                + ", mas não existe histórico "
                                + "suficiente para validar essa carga."
                );
            }

            /*
             * As sessões são obtidas em ordem decrescente,
             * portanto a primeira é a mais recente.
             */
            AiPerformanceContext.AiPerformanceSession latestSession =
                    context.getRecentSessions().get(0);

            Double latestWeight =
                    latestSession.getMaxWeight();

            if (
                    latestWeight == null
                            || latestWeight <= 0
            ) {

                throw new RuntimeException(
                        "Não foi possível validar a carga do exercício "
                                + aiExercise.getExerciseId()
                );
            }

            /*
             * Permite no máximo 10% de aumento
             * sobre a carga máxima da sessão mais recente.
             */
            double maximumAllowedWeight =
                    latestWeight * 1.10;

            /*
             * Arredonda para duas casas decimais.
             */
            maximumAllowedWeight =
                    Math.round(
                            maximumAllowedWeight * 100.0
                    ) / 100.0;

            if (targetWeight > maximumAllowedWeight) {

                throw new RuntimeException(
                        "A carga sugerida pela IA para o exercício "
                                + aiExercise.getExerciseId()
                                + " excede o limite de progressão. "
                                + "Carga atual: "
                                + latestWeight
                                + " kg. "
                                + "Carga máxima permitida: "
                                + maximumAllowedWeight
                                + " kg. "
                                + "Carga sugerida: "
                                + targetWeight
                                + " kg."
                );
            }
        }
    }

    private Workout saveWorkout(
            String userId,
            AiWorkoutPlan plan
    ) {

        LocalDateTime now =
                LocalDateTime.now();

        Workout workout =
                Workout.builder()
                        .userId(userId)
                        .name(plan.getName())
                        .goal(plan.getGoal())
                        .estimatedDuration(
                                plan.getEstimatedDuration()
                        )
                        .active(true)
                        .createdAt(now)
                        .updatedAt(now)
                        .build();

        return workoutRepository.save(workout);
    }

    private List<WorkoutExercise> saveWorkoutExercises(
            String workoutId,
            List<AiWorkoutExercise> aiExercises
    ) {

        List<WorkoutExercise> savedExercises =
                new ArrayList<>();

        int order = 1;

        for (
                AiWorkoutExercise aiExercise
                : aiExercises
        ) {

            WorkoutExercise workoutExercise =
                    WorkoutExercise.builder()
                            .workoutId(workoutId)
                            .exerciseId(
                                    aiExercise.getExerciseId()
                            )
                            .exerciseOrder(order)
                            .sets(aiExercise.getSets())
                            .repetitions(
                                    String.valueOf(
                                            aiExercise.getRepetitions()
                                    )
                            )
                            .restSeconds(
                                    aiExercise.getRestSeconds()
                            )
                            .targetWeight(
                                    aiExercise.getTargetWeight()
                            )
                            .notes(null)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

            savedExercises.add(
                    workoutExerciseRepository.save(
                            workoutExercise
                    )
            );

            order++;
        }

        return savedExercises;
    }

    private String cleanJsonResponse(
            String response
    ) {

        if (response == null) {
            throw new RuntimeException(
                    "A IA retornou uma resposta vazia"
            );
        }

        String cleaned =
                response.trim();

        if (cleaned.startsWith("```json")) {
            cleaned =
                    cleaned.substring(
                            7
                    );
        } else if (cleaned.startsWith("```")) {
            cleaned =
                    cleaned.substring(
                            3
                    );
        }

        if (cleaned.endsWith("```")) {
            cleaned =
                    cleaned.substring(
                            0,
                            cleaned.length() - 3
                    );
        }

        return cleaned.trim();
    }
}