package tn.esprit.gouvernance_donnees_backend.implementation.interfaces;

import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;

public interface IAuthImp {
    
    //methode login et signup 
    public AuthenticationResponse registerUtilsateur(Utilisateur user);
    public AuthenticationResponse loginUtilisateur(LoginRequest loginRequest);

    //email confirmation methods 
    public void confirmAccount(String token);

   

    
}
