package tn.esprit.gouvernance_donnees_backend.implementation.services.tracelog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.gouvernance_donnees_backend.entities.TraceLog;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.repositories.TraceLogRepository;
import tn.esprit.gouvernance_donnees_backend.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TraceLogService {

    @Autowired
    private TraceLogRepository traceLogRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void saveLog(String action, String fileName, String description, String username) {
        TraceLog traceLog = new TraceLog();
        traceLog.setAction(action);
        traceLog.setFileName(fileName);
        traceLog.setDescription(description);
        traceLog.setTimestamp(LocalDateTime.now());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the username is stored in the principal
            String usernamee = authentication.getName();

            // Check if Utilisateur exists in the database
            Utilisateur utilisateur = utilisateurRepository.findByEmail(usernamee);
            traceLog.setUtilisateur(utilisateur);

        traceLogRepository.save(traceLog);
    }
}}
