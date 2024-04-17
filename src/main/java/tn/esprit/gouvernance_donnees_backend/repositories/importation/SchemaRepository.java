package tn.esprit.gouvernance_donnees_backend.repositories.importation;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gouvernance_donnees_backend.entities.importation.DataTable;
import tn.esprit.gouvernance_donnees_backend.entities.importation.Schema;

import java.util.List;

public interface SchemaRepository extends JpaRepository<Schema,Long> {
    void deleteByParentDataTable(DataTable fileInfo);
    List<Schema> findBySourceColumn(String sourceColumn);

    List<Schema> findBySourceTable(String sourceTable);
}
