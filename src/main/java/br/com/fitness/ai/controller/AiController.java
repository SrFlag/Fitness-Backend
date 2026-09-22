package br.com.fitness.ai.controller;

import br.com.fitness.ai.dto.AiGeneratedWorkoutRequest;
import br.com.fitness.ai.dto.AiGeneratedWorkoutResponse;
import br.com.fitness.ai.dto.AiProfileAnalysisResponse;
import br.com.fitness.ai.dto.AiTestRequest;
import br.com.fitness.ai.dto.AiTestResponse;
import br.com.fitness.ai.integration.gemini.GeminiService;
import br.com.fitness.ai.service.AiService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiService geminiService;
    private final AiService aiService;

    public AiController(
            GeminiService geminiService,
            AiService aiService
    ) {
        this.geminiService = geminiService;
        this.aiService = aiService;
    }

    @PostMapping("/test")
    public AiTestResponse test(
            @Valid @RequestBody AiTestRequest request
    ) {

        String response =
                geminiService.generateText(
                        request.getMessage()
                );

        return new AiTestResponse(response);
    }

    @GetMapping("/analyze-profile")
    public AiProfileAnalysisResponse analyzeProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        String analysis =
                aiService.analyzeProfile(email);

        return new AiProfileAnalysisResponse(
                analysis
        );
    }

    @PostMapping("/generate-workout")
    public AiGeneratedWorkoutResponse generateWorkout(
            Authentication authentication,
            @RequestBody AiGeneratedWorkoutRequest request
    ) {

        String email = authentication.getName();

        return aiService.generateWorkout(
                email,
                request
        );
    }
}