package com.avaloq.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="simulation")
public class Simulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "roll_count")
    private Integer rollCount;
    @Column(name = "dice_count")
    private Integer diceCount;
    @Column(name = "side_count")
    private Integer sideCount;

    @OneToMany(mappedBy="simulation", cascade = CascadeType.ALL)
    private Set<Roll> rollSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRollCount() {
        return rollCount;
    }

    public void setRollCount(Integer rollCount) {
        this.rollCount = rollCount;
    }

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public Integer getSideCount() {
        return sideCount;
    }

    public void setSideCount(Integer sideCount) {
        this.sideCount = sideCount;
    }

    public Set<Roll> getRollSet() {
        return rollSet;
    }

    public void setRollSet(Set<Roll> rollSet) {
        this.rollSet = rollSet;
    }
}
