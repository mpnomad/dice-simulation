package com.avaloq.core.dto;

public class DiceFormInputDto {
    private Integer diceCount;
    private Integer rollCount;
    private Integer sideCount;

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public Integer getRollCount() {
        return rollCount;
    }

    public void setRollCount(Integer rollCount) {
        this.rollCount = rollCount;
    }

    public Integer getSideCount() {
        return sideCount;
    }

    public void setSideCount(Integer sideCount) {
        this.sideCount = sideCount;
    }
}
