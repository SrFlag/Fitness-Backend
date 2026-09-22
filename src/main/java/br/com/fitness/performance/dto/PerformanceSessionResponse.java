package br.com.fitness.performance.dto;

import java.time.LocalDate;

public class PerformanceSessionResponse {

    private LocalDate date;

    private Double maxWeight;

    private Integer totalRepetitions;

    private Double volume;

    public PerformanceSessionResponse(
            LocalDate date,
            Double maxWeight,
            Integer totalRepetitions,
            Double volume
    ) {
        this.date = date;
        this.maxWeight = maxWeight;
        this.totalRepetitions = totalRepetitions;
        this.volume = volume;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public Integer getTotalRepetitions() {
        return totalRepetitions;
    }

    public Double getVolume() {
        return volume;
    }
}