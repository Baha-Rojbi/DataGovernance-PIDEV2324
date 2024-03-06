package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.tracelog;

import tn.esprit.gouvernance_donnees_backend.entities.TraceLog;

import java.util.List;

public interface Itracelog {
    public void saveLog(String action, String fileName, String description, String username);
    public List<TraceLog> getAllLogs() ;


}
