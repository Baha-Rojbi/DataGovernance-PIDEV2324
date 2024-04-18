package tn.esprit.gouvernance_donnees_backend.controllers.user;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.afffectRoleStatusRequest;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IUtilisateurImp;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.UserStatus;




@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/AdminController")
@RestController
public class AdminController {
    
    private final IUtilisateurImp iUtilisateurImp ;
    
    


    @GetMapping("/getPendingUsersRequests")
    public  List<Utilisateur> getPendingUsersRequests() {
        return iUtilisateurImp.getPendingUsersRequests();
    }

    @PutMapping("/affectRoleAndChangeStatus")
    public Utilisateur affectRoleAndChangeStatus(@RequestBody afffectRoleStatusRequest affectRoleAndChangeStatus) {
       return iUtilisateurImp.affectRoleAndChangeStatus(affectRoleAndChangeStatus.getIdUtilisateur(),affectRoleAndChangeStatus.getRole(),affectRoleAndChangeStatus.getStatus());
    }

    @GetMapping("/getUserStatusCount")
    public Map<UserStatus, Long> getUserStatusCount() {
        return iUtilisateurImp.countByStatus();
    }
    
    



}