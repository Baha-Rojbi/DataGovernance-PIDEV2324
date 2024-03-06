package tn.esprit.gouvernance_donnees_backend.repositories;

import tn.esprit.gouvernance_donnees_backend.entities.DataTable;
import tn.esprit.gouvernance_donnees_backend.entities.Schema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaRepository extends JpaRepository<Schema,Long> {
    void deleteByParentDataTable(DataTable fileInfo);
}
