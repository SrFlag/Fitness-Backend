package br.com.fitness.profile.service;

import br.com.fitness.profile.dto.CreateUserProfileRequest;
import br.com.fitness.profile.dto.UpdateUserProfileRequest;
import br.com.fitness.profile.dto.UserProfileResponse;
import br.com.fitness.profile.model.UserProfile;
import br.com.fitness.profile.repository.UserProfileRepository;
import br.com.fitness.user.model.User;
import br.com.fitness.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(
            UserProfileRepository userProfileRepository,
            UserRepository userRepository
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public UserProfileResponse createProfile(
            String email,
            CreateUserProfileRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (userProfileRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("O usuário já possui um perfil");
        }

        LocalDateTime now = LocalDateTime.now();

        UserProfile profile = UserProfile.builder()
                .userId(user.getId())
                .birthDate(request.getBirthDate())
                .height(request.getHeight())
                .weight(request.getWeight())
                .gender(request.getGender())
                .goal(request.getGoal())
                .experienceLevel(request.getExperienceLevel())
                .trainingDays(request.getTrainingDays())
                .trainingDuration(request.getTrainingDuration())
                .trainingLocation(request.getTrainingLocation())
                .availableEquipment(request.getAvailableEquipment())
                .preferredExercises(request.getPreferredExercises())
                .restrictions(request.getRestrictions())
                .createdAt(now)
                .updatedAt(now)
                .build();

        UserProfile savedProfile = userProfileRepository.save(profile);

        return UserProfileResponse.from(savedProfile);
    }

    public UserProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        return UserProfileResponse.from(profile);
    }

    public UserProfileResponse updateProfile(
            String email,
            UpdateUserProfileRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        profile.setBirthDate(request.getBirthDate());
        profile.setHeight(request.getHeight());
        profile.setWeight(request.getWeight());
        profile.setGender(request.getGender());
        profile.setGoal(request.getGoal());
        profile.setExperienceLevel(request.getExperienceLevel());
        profile.setTrainingDays(request.getTrainingDays());
        profile.setTrainingDuration(request.getTrainingDuration());
        profile.setTrainingLocation(request.getTrainingLocation());
        profile.setAvailableEquipment(request.getAvailableEquipment());
        profile.setPreferredExercises(request.getPreferredExercises());
        profile.setRestrictions(request.getRestrictions());
        profile.setUpdatedAt(LocalDateTime.now());

        UserProfile updatedProfile = userProfileRepository.save(profile);

        return UserProfileResponse.from(updatedProfile);
    }
}