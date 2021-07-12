package com.avaloq.backend.repository;

import com.avaloq.backend.dto.SimulationReportInterface;
import com.avaloq.backend.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

    @Query(
         nativeQuery = true,
         value = "SELECT " +
                 "  (SELECT count(s.id) FROM simulation s) AS simulationTotal, " +
                 "  (SELECT count(r.id) FROM roll r) AS rollTotal "
    )
    SimulationReportInterface getTotalSimulationReport();
}
