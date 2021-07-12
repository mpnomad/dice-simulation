package com.avaloq.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dice")
public class Dice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sideCount;
    private Integer diceCount;
    private Integer rollValue;

    @ManyToOne
    @JoinColumn(name="roll_id", nullable=false)
    @JsonIgnore
    private Roll roll;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSideCount() {
        return sideCount;
    }

    public void setSideCount(Integer sideCount) {
        this.sideCount = sideCount;
    }

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public Integer getRollValue() {
        return rollValue;
    }

    public void setRollValue(Integer rollValue) {
        this.rollValue = rollValue;
    }

    public Roll getRoll() {
        return roll;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }
}
