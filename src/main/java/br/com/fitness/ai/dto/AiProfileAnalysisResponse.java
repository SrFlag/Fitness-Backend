package br.com.fitness.ai.dto;

public class AiProfileAnalysisResponse {

    private String analysis;

    public AiProfileAnalysisResponse(String analysis) {
        this.analysis = analysis;
    }

    public String getAnalysis() {
        return analysis;
    }
}