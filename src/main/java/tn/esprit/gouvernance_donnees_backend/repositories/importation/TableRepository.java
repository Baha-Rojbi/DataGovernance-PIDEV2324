package tn.esprit.gouvernance_donnees_backend.repositories.importation;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gouvernance_donnees_backend.entities.importation.DataTable;

import java.util.Optional;

public interface TableRepository extends JpaRepository<DataTable,Long> {
    Optional<DataTable> findByName(String name);

}
