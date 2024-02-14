package tn.esprit.gouvernance_donnees_backend.implementation.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.IUtilisateurImp;
import tn.esprit.gouvernance_donnees_backend.repositories.UtilisateurRepository;

@AllArgsConstructor
@Slf4j
@Service
public class UtilisateurServiceImp implements IUtilisateurImp {
    
    private UtilisateurRepository utilisateurRepository;
    
    @Override
    public Utilisateur getUserInformationByLoggedEmail(String email) {
        Utilisateur user = null  ; 
        try {
            user = utilisateurRepository.findByEmail(email);
        } catch (Exception e) {
            log.info(e.toString());
        }
        return user;
         
    }
    
}
