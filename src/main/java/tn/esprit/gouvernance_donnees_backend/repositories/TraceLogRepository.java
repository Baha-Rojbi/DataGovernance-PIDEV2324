package tn.esprit.gouvernance_donnees_backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gouvernance_donnees_backend.entities.TraceLog;

public interface TraceLogRepository extends JpaRepository<TraceLog,Long> {
}
