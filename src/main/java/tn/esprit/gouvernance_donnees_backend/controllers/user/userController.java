package tn.esprit.gouvernance_donnees_backend.controllers.user;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.VerifyOldPsswordRequest;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Team;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IUtilisateurImp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:4200")

@AllArgsConstructor
@Slf4j
@RequestMapping("/usercontroller")
@RestController
public class userController {
    private final IUtilisateurImp iUtilisateurImp;

    @PostMapping("/updateProfile")
    public Utilisateur updateProfile(@RequestBody Utilisateur utilisateur) {
        log.info(utilisateur.toString());
        return iUtilisateurImp.updateProfile(utilisateur);
    }
    
    @GetMapping("/getUserInformationByLoggedEmail/{email}")
    public Utilisateur getUserInformationByLoggedEmail(@PathVariable String email) {
       
        return iUtilisateurImp.getUserInformationByLoggedEmail(email);
    }
    
    @PostMapping("/verifyOldPassword")
    public Boolean postMethodName(@RequestBody VerifyOldPsswordRequest verifyOldPsswordRequest) {
        return iUtilisateurImp.verifyOldPassword(verifyOldPsswordRequest);
    }

    @PostMapping("/updatePassword/{userId}")
    public Utilisateur postMethodName(@PathVariable Long userId,@RequestBody String newPassword) {
        return iUtilisateurImp.updatePassword(userId, newPassword);
    }

    @GetMapping("/getAllApprovedUsers/{userId}/{ownedTeamId}")
    public List<Utilisateur> getAllApprovedUsers(@PathVariable Long userId,@PathVariable Long ownedTeamId) {
        return iUtilisateurImp.getAllApproveUsers(userId,ownedTeamId);
    }

    @PostMapping("/addUserToTeam/{leaderMail}/{memberMail}")
    public Utilisateur addUserToTeam(@PathVariable String leaderMail, @PathVariable String memberMail) {     
        Utilisateur user = null; 
        try {
            user =  this.iUtilisateurImp.addUserToTeamByEmail(leaderMail, memberMail);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    @GetMapping("/getOwnedTeamMemebers/{ownedTeamId}")
    public List<Utilisateur> getOwnedTeamMemebers(@PathVariable Long ownedTeamId) {
        return this.iUtilisateurImp.getOwnedTeamMemebers(ownedTeamId);
    }

    @DeleteMapping("/removeMemberFromTeam/{teamId}/{memberId}")
    public Team removeMemberFromTeam(@PathVariable Long teamId, @PathVariable Long memberId) {
        return iUtilisateurImp.removeMemberFromTeam(memberId, teamId);
        
    }

    

    
    
    
    
    

    
}
