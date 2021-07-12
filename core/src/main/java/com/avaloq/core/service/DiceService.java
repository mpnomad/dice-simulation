package com.avaloq.core.service;

import com.avaloq.core.dto.DiceFormInputDto;
import com.avaloq.core.dto.DiceRollDto;
import com.avaloq.core.exception.DiceException;
import org.springframework.stereotype.Service;

@Service
public interface DiceService {

    /**
     * Gets the sum of rolled dice.
     *
     * @param diceCount
     * @param sideCount
     * @param rollCount
     * @return
     */
    DiceRollDto rollDice(Integer diceCount,
                         Integer sideCount,
                         Integer rollCount) throws DiceException;

    /**
     * Dice roll with side count input.
     * @param sideCount
     * @return
     */
    Integer rollDie(Integer sideCount);

    /**
     * Roll dice with input for dto
     * @param diceFormInputDto
     * @return
     */
    DiceRollDto rollDice(DiceFormInputDto diceFormInputDto) throws DiceException;

    /**
     * Save the rolled dice.
     * @param diceRollDto
     */
    void saveRolledDice(DiceRollDto diceRollDto) throws DiceException;
}
