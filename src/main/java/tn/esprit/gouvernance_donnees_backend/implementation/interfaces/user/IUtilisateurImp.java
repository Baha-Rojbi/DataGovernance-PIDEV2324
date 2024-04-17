package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user;

import java.util.List;

import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.VerifyOldPsswordRequest;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Team;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;

/**
 * IUtilisateurImp
 */
public interface IUtilisateurImp {

     //Utilisateur methodes 
    public Utilisateur getUserInformationByLoggedEmail(String email);
    public List<Utilisateur> getPendingUsersRequests ();
    public Utilisateur affectRoleAndChangeStatus(Long idUtilisateur,Role role,UserStatus userStatus);
    public Utilisateur updateProfile (Utilisateur user);
    public Boolean verifyOldPassword(VerifyOldPsswordRequest verifyOldPsswordRequest);
    public Utilisateur updatePassword(Long utilisateurId,String newPassword);
    public Utilisateur addUserToTeamByEmail(String currentUserEmail, String userToAddEmail) throws Exception;
    public List<Utilisateur> getAllApproveUsers(Long UserId,Long ownedTeamId);
    public List<Utilisateur> getOwnedTeamMemebers(Long ownedTeamId);
    public Team removeMemberFromTeam(Long memberId, Long teamId);
} 