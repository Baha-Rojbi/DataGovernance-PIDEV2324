package tn.esprit.gouvernance_donnees_backend.implementation.interfaces;

import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;

public interface IUtilisateurImp {
    
    //methode login et signup 
    public AuthenticationResponse registerUtilsateur(Utilisateur user);
    public AuthenticationResponse loginUtilisateur(LoginRequest loginRequest);

    
}
