package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.lineage;

import tn.esprit.gouvernance_donnees_backend.entities.Schema;

import java.util.List;

public interface ILineage {
    public List<Schema> getLineageForColumn(String columnName);
    public List<Schema> getLineageForTable(String tableName);
}
