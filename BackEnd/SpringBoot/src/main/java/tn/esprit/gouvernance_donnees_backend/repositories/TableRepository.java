package tn.esprit.gouvernance_donnees_backend.repositories;

import tn.esprit.gouvernance_donnees_backend.entities.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<DataTable,Long> {
    Optional<DataTable> findByName(String name);
}
