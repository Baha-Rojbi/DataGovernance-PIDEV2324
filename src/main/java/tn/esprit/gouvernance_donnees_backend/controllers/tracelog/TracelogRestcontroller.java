package tn.esprit.gouvernance_donnees_backend.controllers.tracelog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.gouvernance_donnees_backend.entities.tracelog.TraceLog;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.tracelog.Itracelog;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class TracelogRestcontroller {
    @Autowired
   Itracelog itracelog;



    @GetMapping("/getAlllogs")
    public List<TraceLog> getAllLogs() {
        return itracelog.getAllLogs();
    }
}
