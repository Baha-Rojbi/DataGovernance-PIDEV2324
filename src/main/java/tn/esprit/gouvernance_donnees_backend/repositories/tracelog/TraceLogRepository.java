package tn.esprit.gouvernance_donnees_backend.repositories.tracelog;


import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.gouvernance_donnees_backend.entities.tracelog.TraceLog;

public interface TraceLogRepository extends JpaRepository<TraceLog,Long> {
}
