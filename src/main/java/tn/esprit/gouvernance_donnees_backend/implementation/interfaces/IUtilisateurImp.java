package tn.esprit.gouvernance_donnees_backend.implementation.interfaces;

import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;

/**
 * IUtilisateurImp
 */
public interface IUtilisateurImp {

     //Utilisateur methodes 
    public Utilisateur getUserInformationByLoggedEmail(String email);
}