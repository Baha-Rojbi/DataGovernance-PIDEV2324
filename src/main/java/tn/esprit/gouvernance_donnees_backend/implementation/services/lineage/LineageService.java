package tn.esprit.gouvernance_donnees_backend.implementation.services.lineage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.gouvernance_donnees_backend.entities.importation.Schema;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.lineage.ILineage;

import tn.esprit.gouvernance_donnees_backend.repositories.importation.SchemaRepository;

import java.util.List;
@Service

public class LineageService implements ILineage {
    @Autowired
    SchemaRepository schemaRepository;
    @Override
    public List<Schema> getLineageForColumn(String columnName) {
        return schemaRepository.findBySourceColumn(columnName);
    }

    @Override
    public List<Schema> getLineageForTable(String tableName) {
        return schemaRepository.findBySourceTable(tableName);
    }
}
