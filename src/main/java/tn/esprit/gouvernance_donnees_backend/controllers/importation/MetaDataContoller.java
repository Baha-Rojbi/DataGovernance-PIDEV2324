package tn.esprit.gouvernance_donnees_backend.controllers.importation;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gouvernance_donnees_backend.entities.importation.DataTable;
import tn.esprit.gouvernance_donnees_backend.entities.importation.Schema;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.importation.IDataService;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.importation.IFileProcessService;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.importation.IPdfService;
import tn.esprit.gouvernance_donnees_backend.implementation.services.imortation.DataService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class MetaDataContoller {
    @Autowired
    private IFileProcessService fileProcessService;
    @Autowired
    private IDataService dataTableService;
    @Autowired
    private IPdfService pdfService;
    @Autowired
    public MetaDataContoller(DataService dataTableService) {
        this.dataTableService = dataTableService;
    }

    @GetMapping("/tables")
    public List<DataTable> getAllDataTables() {
        return dataTableService.getAllDataTables();
    }

    @GetMapping("/tables/{id}")
    public ResponseEntity<DataTable> getDataTableById(@PathVariable Long id) {
        return dataTableService.getDataTableById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tables")
    public DataTable createDataTable(@RequestBody DataTable dataTable) {
        return dataTableService.saveOrUpdateDataTable(dataTable);
    }

    @PutMapping("/tables/{id}")
    public ResponseEntity<DataTable> updateDataTable(@PathVariable Long id, @RequestBody DataTable dataTable) {
        dataTable.setIdTable(id); // Make sure the ID is set correctly
        DataTable updatedDataTable = dataTableService.saveOrUpdateDataTable(dataTable);
        return ResponseEntity.ok(updatedDataTable);
    }


    // Add other endpoints as needed
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("description") String description) {
        try {
            String message = fileProcessService.processFile(file, description); // Adjust your service method to accept description
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not upload the file: " + e.getMessage());
        }
    }
    @GetMapping("/tables/{id}/schemas")
    public ResponseEntity<List<Schema>> getSchemasForTable(@PathVariable Long id) {
        List<Schema> schemas = dataTableService.getSchemasForTable(id);
        return ResponseEntity.ok(schemas);
    }
    @PutMapping("/schemas/{id}")
    public ResponseEntity<Schema> updateSchema(@PathVariable Long id, @RequestBody Schema schema) {
        Schema updatedSchema = dataTableService.updateSchema(id, schema);
        return new ResponseEntity<>(updatedSchema, HttpStatus.OK);
    }
    @PutMapping("/schemas/{idSchema}/tags")
    public ResponseEntity<?> updateSchemaTags(@PathVariable Long idSchema, @RequestBody Set<String> tags) {
        try {
            dataTableService.updateTags(idSchema, tags);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/tables/{tableId}/schemas")
    public ResponseEntity<Schema> createSchema(@PathVariable Long tableId, @RequestBody Schema schema) {
        Schema createdSchema = dataTableService.createSchema(tableId, schema);
        return new ResponseEntity<>(createdSchema, HttpStatus.CREATED);
    }

    @DeleteMapping("/schemas/{schemaId}")
    public ResponseEntity<?> deleteSchema(@PathVariable Long schemaId) {
        try {
            dataTableService.deleteSchema(schemaId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/tables/{id}/download")
    public ResponseEntity<byte[]> downloadDataTablePdf(@PathVariable Long id) {
        byte[] pdfContent = pdfService.generateDataTablePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Suggests download rather than inline display
        headers.setContentDispositionFormData("filename", "datatable-details.pdf");
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
    @PutMapping("/tables/{id}/toggle-archive")
    public ResponseEntity<?> toggleArchiveStatus(@PathVariable Long id) {
        Optional<DataTable> optionalDataTable = dataTableService.findById(id);
        if (optionalDataTable.isPresent()) {
            DataTable dataTable = optionalDataTable.get();
            dataTable.setArchived(!dataTable.isArchived());
            dataTableService.saveOrUpdateDataTable(dataTable);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/tables/create")
    public ResponseEntity<DataTable> createDataTable(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String description = payload.get("description");
        DataTable dataTable = dataTableService.createDataTable(name, description);
        return new ResponseEntity<>(dataTable, HttpStatus.CREATED);
    }



}
