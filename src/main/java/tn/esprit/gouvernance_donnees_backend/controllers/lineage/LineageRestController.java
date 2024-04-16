package tn.esprit.gouvernance_donnees_backend.controllers.lineage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gouvernance_donnees_backend.entities.Schema;
import tn.esprit.gouvernance_donnees_backend.implementation.services.lineage.LineageService;

import java.util.List;

@RestController
@RequestMapping("/api/lineage")
public class LineageRestController {
    @Autowired
    private LineageService lineageService;

    @GetMapping("/column/{columnName}")
    public ResponseEntity<List<Schema>> getLineageForColumn(@PathVariable String columnName) {
        List<Schema> lineage = lineageService.getLineageForColumn(columnName);
        return ResponseEntity.ok(lineage);
    }

    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<Schema>> getLineageForTable(@PathVariable String tableName) {
        List<Schema> lineage = lineageService.getLineageForTable(tableName);
        return ResponseEntity.ok(lineage);
    }
}
