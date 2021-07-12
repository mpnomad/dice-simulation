package com.avaloq.core.service;

import com.avaloq.backend.dto.SimulationReportInterface;
import com.avaloq.backend.dto.SimulationRollReportInterface;
import com.avaloq.backend.model.Dice;
import com.avaloq.backend.model.Roll;
import com.avaloq.backend.model.Simulation;
import com.avaloq.backend.repository.RollRepository;
import com.avaloq.backend.repository.SimulationRepository;
import com.avaloq.core.dto.DiceFormInputDto;
import com.avaloq.core.dto.DiceRollDto;
import com.avaloq.core.dto.RollReportDto;
import com.avaloq.core.dto.SimulationReportDto;
import com.avaloq.core.exception.DiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DiceServiceImpl implements DiceService {
    Logger LOGGER = LogManager.getLogger(DiceService.class);

/*
    @Autowired
    DiceRepository diceRepository;*/

    @Autowired
    RollRepository rollRepository;

    @Autowired
    SimulationRepository simulationRepository;

    @Override
    public DiceRollDto rollDice(Integer diceCount, Integer sideCount, Integer rollCount) throws DiceException {
        LOGGER.info("Roll dice with diceCount: {}, sideCount: {}, rollCount: {}", diceCount, sideCount, rollCount);
        try {
            DiceRollDto diceRollDto = new DiceRollDto();
            diceRollDto.setDiceCount(diceCount);
            diceRollDto.setSidesCount(sideCount);
            diceRollDto.setRollCount(rollCount);

            LinkedList<Roll> rolls = new LinkedList<>();
            for (int rollCtr = 0; rollCtr < rollCount; rollCtr++) {
                List<Dice> diceList = new ArrayList<>();
                for (int diceCtr = 0; diceCtr < diceCount; diceCtr++) {
                    Dice dice = new Dice();
                    dice.setDiceCount(diceCtr + 1);
                    dice.setSideCount(sideCount);
                    dice.setRollValue(rollDie(sideCount));
                    diceList.add(dice);
                }
                Roll roll = new Roll();
                roll.setRollCount(rollCtr + 1);
                roll.setRollTotal(diceList.stream().collect(Collectors.summingLong(Dice::getRollValue)));
                roll.setDiceSet(diceList.stream().collect(Collectors.toSet()));
                rolls.add(roll);
            }
            diceRollDto.setRollList(rolls);
            return diceRollDto;
        } catch (NullPointerException npe) {
            throw new DiceException("One of the inputs is null.");
        }
    }

    @Override
    public Integer rollDie(Integer sideCount) {
        Random random = new Random();
        return random.nextInt(sideCount) + 1;
    }

    @Transactional
    @Override
    public DiceRollDto rollDice(DiceFormInputDto diceFormInputDto) throws DiceException {
        LOGGER.info("Roll dice with diceCount: {}, sideCount: {}, rollCount: {}",
                diceFormInputDto.getDiceCount(),
                diceFormInputDto.getSideCount(),
                diceFormInputDto.getRollCount());

        try {
            Integer diceCount = diceFormInputDto.getDiceCount();
            Integer sideCount = diceFormInputDto.getSideCount();
            Integer rollCount = diceFormInputDto.getRollCount();

            DiceRollDto diceRollDto = rollDice(diceCount, sideCount, rollCount);
            saveRolledDice(diceRollDto);

            //summary dto
            SimulationReportInterface totalSimulationReport = simulationRepository.getTotalSimulationReport();
            SimulationReportDto simulationReportDto = new SimulationReportDto();
            simulationReportDto.setSimulationTotal(totalSimulationReport.getSimulationTotal());
            simulationReportDto.setRollTotal(totalSimulationReport.getRollTotal());
            diceRollDto.setSimulationReport(simulationReportDto);

            List<SimulationRollReportInterface> rollReport = rollRepository.getRollReport();
            List<RollReportDto> rollReportDtoList = new ArrayList<>();
            for (SimulationRollReportInterface simulationRollReportInterface : rollReport) {
                RollReportDto rollReportDto = new RollReportDto();
                rollReportDto.setRollValue(simulationRollReportInterface.getRollTotal());
                rollReportDto.setRollValueTotal(simulationRollReportInterface.getRollTotalCount());
                Double rate = (new Double(rollReportDto.getRollValueTotal().intValue()) / new Double(simulationReportDto.getRollTotal().intValue()) * 100);
                LOGGER.info("RollValue: {}; RollTotal: {}; Rate: {}",
                        rollReportDto.getRollValueTotal(),
                        simulationReportDto.getRollTotal(),
                        rate);
                rollReportDto.setRollValueRate(rate);
                rollReportDtoList.add(rollReportDto);
            }
            diceRollDto.setRollReport(rollReportDtoList);

            return diceRollDto;
        } catch (DiceException de) {
            throw de;
        } catch (Exception e) {
            throw new DiceException("Unable to roll dice.", e);
        }
    }

    @Override
    public void saveRolledDice(DiceRollDto diceRollDto) throws DiceException {
        LOGGER.info("Save rolled dice.");
        try {
            Simulation simulation = new Simulation();
            simulation.setDiceCount(diceRollDto.getDiceCount());
            simulation.setRollCount(diceRollDto.getRollCount());
            simulation.setSideCount(diceRollDto.getSidesCount());

            LinkedList<Roll> rollList = diceRollDto.getRollList();
            for (Roll roll : rollList) {
                roll.setSimulation(simulation);
                for (Dice dice : roll.getDiceSet()) {
                    dice.setRoll(roll);
                }
            }

            simulation.setRollSet(new HashSet<>(rollList));
            simulationRepository.save(simulation);

        } catch (NullPointerException npe) {
            throw new DiceException("Unable to save due to null value.", npe);
        } catch (ClassCastException cce) {
            throw new DiceException("Unable to cast object to another.", cce);
        } catch (Exception e) {
            throw new DiceException("Unable to save roll dice.", e);
        }
    }
}
