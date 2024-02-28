package tn.esprit.gouvernance_donnees_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.afffectRoleStatusRequest;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.IUtilisateurImp;



@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/utilisateurcontroller")
@RestController
@Slf4j
public class UtilisateurController {
    
    private final IUtilisateurImp iUtilisateurImp ;
    
    
    @GetMapping("/getUserInformationByLoggedEmail/{email}")
    public Utilisateur getUserInformationByLoggedEmail(@PathVariable String email) {
       
        return iUtilisateurImp.getUserInformationByLoggedEmail(email);
    }

    @GetMapping("/getPendingUsersRequests")
    public  List<Utilisateur> getPendingUsersRequests() {
        return iUtilisateurImp.getPendingUsersRequests();
    }

    @PostMapping("/affectRoleAndChangeStatus")
    public Utilisateur affectRoleAndChangeStatus(@RequestBody afffectRoleStatusRequest affectRoleAndChangeStatus) {
       return iUtilisateurImp.affectRoleAndChangeStatus(affectRoleAndChangeStatus.getIdUtilisateur(),affectRoleAndChangeStatus.getRole(),affectRoleAndChangeStatus.getStatus());
    }
    
    



}