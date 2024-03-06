package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.exportation;

import tn.esprit.gouvernance_donnees_backend.entities.DataTable;
import tn.esprit.gouvernance_donnees_backend.entities.Schema;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDataService {
    public List<DataTable> getAllDataTables();
    public Optional<DataTable> getDataTableById(Long id);
    public DataTable saveOrUpdateDataTable(DataTable dataTable);
    public Optional<DataTable> findById(Long id);
    public List<Schema> getSchemasForTable(Long tableId);
    public Schema updateSchema(Long id, Schema schemaDetails);
    public void updateTags(Long idSchema, Set<String> tags);
}
