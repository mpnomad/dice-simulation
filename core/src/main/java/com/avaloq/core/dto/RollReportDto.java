package com.avaloq.core.dto;

public class RollReportDto {
    private Integer rollValue;
    private Integer rollValueTotal;
    private Double rollValueRate;

    public Integer getRollValue() {
        return rollValue;
    }

    public void setRollValue(Integer rollValue) {
        this.rollValue = rollValue;
    }

    public Integer getRollValueTotal() {
        return rollValueTotal;
    }

    public void setRollValueTotal(Integer rollValueTotal) {
        this.rollValueTotal = rollValueTotal;
    }

    public Double getRollValueRate() {
        return rollValueRate;
    }

    public void setRollValueRate(Double rollValueRate) {
        this.rollValueRate = rollValueRate;
    }
}
