package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.importation;



import tn.esprit.gouvernance_donnees_backend.entities.importation.DataTable;
import tn.esprit.gouvernance_donnees_backend.entities.importation.Schema;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDataService {
    public List<DataTable> getAllDataTables();
    public DataTable saveOrUpdateDataTable(DataTable dataTable);
    public Optional<DataTable> getDataTableById(Long id);
    public Optional<DataTable> findById(Long id);
    public List<Schema> getSchemasForTable(Long tableId);
    public Schema updateSchema(Long id, Schema schemaDetails);
    public void updateTags(Long idSchema, Set<String> tags);
    public Schema createSchema(Long tableId, Schema schema);
    public void deleteSchema(Long schemaId);
    public DataTable toggleArchiveStatus(Long id);
    public DataTable createDataTable(String name, String description);
}
