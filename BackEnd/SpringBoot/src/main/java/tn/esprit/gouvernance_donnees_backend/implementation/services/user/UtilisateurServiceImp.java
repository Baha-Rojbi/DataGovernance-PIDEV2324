package tn.esprit.gouvernance_donnees_backend.implementation.services.user;

import java.util.List;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IUtilisateurImp;
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

    @Override
    public List<Utilisateur> getPendingUsersRequests() {
        return utilisateurRepository.getPendingUsersRequests();
    }

    @Override
    public Utilisateur affectRoleAndChangeStatus(Long idUtilisateur, Role role, UserStatus userStatus) {
        Utilisateur utilisateur = null;
        try {
             utilisateur = utilisateurRepository.findById(idUtilisateur)
                    .orElseThrow(() -> new Exception("User not found with id: " + idUtilisateur));
                    utilisateur.setStatus(userStatus);
                    utilisateur.setRole(role);
        } catch (Exception ex) {
            // Handle the exception or rethrow it as needed
            System.out.println("User not found: " + ex.getMessage());
        }
        return utilisateurRepository.save(utilisateur);
    }


    
    
}
