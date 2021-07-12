package com.avaloq.backend.repository;

import com.avaloq.backend.model.Dice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiceRepository extends JpaRepository<Dice, Long> {
}
