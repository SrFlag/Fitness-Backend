package br.com.fitness.nutrition.service;

import br.com.fitness.nutrition.dto.CreateMealRequest;
import br.com.fitness.nutrition.dto.MealResponse;
import br.com.fitness.nutrition.dto.UpdateMealRequest;
import br.com.fitness.nutrition.model.Meal;
import br.com.fitness.nutrition.repository.MealRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public MealService(
            MealRepository mealRepository,
            UserRepository userRepository
    ) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public MealResponse create(
            String email,
            CreateMealRequest request
    ) {

        User user = findUserByEmail(email);

        LocalDateTime now = LocalDateTime.now();

        Meal meal = new Meal();

        meal.setUserId(user.getId());

        meal.setDate(
                request.getDate()
        );

        meal.setName(
                normalizeName(request.getName())
        );

        meal.setMealType(
                normalizeMealType(request.getMealType())
        );

        meal.setNotes(
                request.getNotes()
        );

        meal.setCreatedAt(now);
        meal.setUpdatedAt(now);

        Meal savedMeal =
                mealRepository.save(meal);

        return toResponse(savedMeal);
    }

    public List<MealResponse> findByDate(
            String email,
            LocalDate date
    ) {

        User user = findUserByEmail(email);

        return mealRepository
                .findByUserIdAndDateOrderByCreatedAtAsc(
                        user.getId(),
                        date
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MealResponse> findAll(
            String email
    ) {

        User user = findUserByEmail(email);

        return mealRepository
                .findByUserIdOrderByDateDescCreatedAtAsc(
                        user.getId()
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MealResponse findById(
            String email,
            String mealId
    ) {

        User user = findUserByEmail(email);

        Meal meal =
                mealRepository
                        .findByIdAndUserId(
                                mealId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Refeição não encontrada"
                                )
                        );

        return toResponse(meal);
    }

    public MealResponse update(
            String email,
            String mealId,
            UpdateMealRequest request
    ) {

        User user = findUserByEmail(email);

        Meal meal =
                mealRepository
                        .findByIdAndUserId(
                                mealId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Refeição não encontrada"
                                )
                        );

        meal.setDate(
                request.getDate()
        );

        meal.setName(
                normalizeName(request.getName())
        );

        meal.setMealType(
                normalizeMealType(request.getMealType())
        );

        meal.setNotes(
                request.getNotes()
        );

        meal.setUpdatedAt(
                LocalDateTime.now()
        );

        Meal updatedMeal =
                mealRepository.save(meal);

        return toResponse(updatedMeal);
    }

    public void delete(
            String email,
            String mealId
    ) {

        User user = findUserByEmail(email);

        Meal meal =
                mealRepository
                        .findByIdAndUserId(
                                mealId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Refeição não encontrada"
                                )
                        );

        mealRepository.delete(meal);
    }

    private User findUserByEmail(
            String email
    ) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );
    }

    private MealResponse toResponse(
            Meal meal
    ) {

        return new MealResponse(
                meal.getId(),
                meal.getDate(),
                meal.getName(),
                meal.getMealType(),
                meal.getNotes()
        );
    }

    private String normalizeName(
            String value
    ) {

        if (value == null) {
            return null;
        }

        String normalized =
                value.trim();

        if (normalized.isEmpty()) {
            return normalized;
        }

        return normalized.substring(0, 1)
                .toUpperCase(Locale.ROOT)
                + normalized.substring(1);
    }

    private String normalizeMealType(
            String value
    ) {

        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toUpperCase(Locale.ROOT);
    }
}