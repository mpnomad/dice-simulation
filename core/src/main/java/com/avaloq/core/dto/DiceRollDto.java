package com.avaloq.core.dto;

import com.avaloq.backend.model.Roll;
import com.avaloq.core.dto.RollReportDto;

import java.util.LinkedList;
import java.util.List;

public class DiceRollDto {
    private Integer diceCount;
    private Integer sidesCount;
    private Integer rollCount;

    private LinkedList<Roll> RollList;
    private SimulationReportDto simulationReport;
    private List<RollReportDto> rollReport;

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public Integer getSidesCount() {
        return sidesCount;
    }

    public void setSidesCount(Integer sidesCount) {
        this.sidesCount = sidesCount;
    }

    public Integer getRollCount() {
        return rollCount;
    }

    public void setRollCount(Integer rollCount) {
        this.rollCount = rollCount;
    }

    public LinkedList<Roll> getRollList() {
        return RollList;
    }

    public void setRollList(LinkedList<Roll> rollList) {
        RollList = rollList;
    }

    public SimulationReportDto getSimulationReport() {
        return simulationReport;
    }

    public void setSimulationReport(SimulationReportDto simulationReport) {
        this.simulationReport = simulationReport;
    }

    public List<RollReportDto> getRollReport() {
        return rollReport;
    }

    public void setRollReport(List<RollReportDto> rollReport) {
        this.rollReport = rollReport;
    }
}
