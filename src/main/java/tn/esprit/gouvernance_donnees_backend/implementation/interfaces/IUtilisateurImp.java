package tn.esprit.gouvernance_donnees_backend.implementation.interfaces;

import java.util.List;

import tn.esprit.gouvernance_donnees_backend.entities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;

/**
 * IUtilisateurImp
 */
public interface IUtilisateurImp {

     //Utilisateur methodes 
    public Utilisateur getUserInformationByLoggedEmail(String email);
    public List<Utilisateur> getPendingUsersRequests ();
    public Utilisateur affectRoleAndChangeStatus(Long idUtilisateur,Role role,UserStatus userStatus);

}