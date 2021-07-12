package com.avaloq.backend.repository;

import com.avaloq.backend.dto.SimulationRollReportInterface;
import com.avaloq.backend.model.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollRepository extends JpaRepository<Roll, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT " +
                    "     r.rollTotal AS rollTotal, " +
                    "     COUNT(r.rollTotal) AS rollTotalCount  " +
                    "FROM Roll r " +
                    "  GROUP BY rollTotal "
    )
    List<SimulationRollReportInterface> getRollReport();
}
