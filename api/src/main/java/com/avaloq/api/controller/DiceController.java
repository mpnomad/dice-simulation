package com.avaloq.api.controller;

import com.avaloq.core.dto.DiceFormInputDto;
import com.avaloq.core.dto.DiceRollDto;
import com.avaloq.core.exception.DiceException;
import com.avaloq.core.service.DiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/")
public class DiceController{
    private static Logger LOGGER = LogManager.getLogger(DiceController.class);

    @Autowired
    private DiceService diceService;

    @RequestMapping(method = RequestMethod.GET, value = "/roll-dice", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> rollDice(
            @RequestParam(value = "diceCount", required = true)
            @Min(value = 1, message = "Dice count must be at least 1.")
            final Integer diceCount,
            @RequestParam(value = "sideCount", required = true)
            @Min(value = 4, message = "Side count must be at least 4.")
            final Integer sideCount,
            @RequestParam(value = "rollCount", required = true)
            @Min(value = 1, message = "Roll count must be at least 1.")
            final Integer rollCount
    ) {
        LOGGER.info("Roll dice.");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            DiceRollDto diceRollDtoList = diceService.rollDice(diceCount, sideCount, rollCount);
            resultMap.put("result", diceRollDtoList);
            resultMap.put("success", "OK");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (NumberFormatException nfe) {
            LOGGER.error("One of the inputs is not a number.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } catch (DiceException dae) {
            LOGGER.error(dae.getMessage(), dae);
            resultMap.put("error", "Failed to roll the dice.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            resultMap.put("error", "Failed to roll the dice.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save-roll-dice", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> saveAndRollDice(
            @RequestBody()
            final DiceFormInputDto diceFormInputDto
    ) {
        LOGGER.info("Save and roll dice.");
        Map<String, Object> resultMap = new HashMap<>();
        try {
            DiceRollDto diceRollDtoList = diceService.rollDice(diceFormInputDto);
            resultMap.put("result", diceRollDtoList);
            resultMap.put("success", "OK");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (DiceException dae) {
            LOGGER.error(dae.getMessage(), dae);
            resultMap.put("error", "Failed to roll the dice.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            resultMap.put("error", "Failed to roll the dice.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }
}
