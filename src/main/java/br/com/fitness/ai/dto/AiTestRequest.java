package br.com.fitness.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class AiTestRequest {

    @NotBlank
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}