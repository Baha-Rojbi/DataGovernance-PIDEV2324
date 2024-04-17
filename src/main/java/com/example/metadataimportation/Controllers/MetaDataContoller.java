package com.example.metadataimportation.Controllers;

import com.example.metadataimportation.Entities.Metadata;
import com.example.metadataimportation.Services.DataService;
import com.example.metadataimportation.Services.FileProcessService;
import com.example.metadataimportation.Services.MetadataService;
import com.example.metadataimportation.Services.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class MetaDataContoller {
    @Autowired
    private FileProcessService fileProcessService;
    @Autowired
    private DataService dataTableService;
    @Autowired
    private StatService statService;
    @Autowired
    private MetadataService metadataService;

    @Autowired
    public MetaDataContoller(DataService dataTableService) {
        this.dataTableService = dataTableService;
    }

    @PostMapping("/Metadata/{dataTableId}")
    public ResponseEntity<Metadata> createMetadata(@PathVariable Long dataTableId, @RequestBody Metadata metadata) {
        Metadata createdMetadata = metadataService.createMetadata(dataTableId, metadata);
        return new ResponseEntity<>(createdMetadata, HttpStatus.CREATED);
    }
    @GetMapping("/datatable/{id}")
    public ResponseEntity<?> getMetadataByDataTableId(@PathVariable Long id) {
        Optional<Metadata> metadata = metadataService.findMetadataByDataTableId(id);
        if (metadata.isPresent()) {
            return new ResponseEntity<>(metadata.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Metadata not found for dataTableId: " + id, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<Metadata>> getAllMetadata() {
        List<Metadata> allMetadata = metadataService.getAllMetadata();
        return new ResponseEntity<>(allMetadata, HttpStatus.OK);
    }
}