package tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user;

import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;

public interface IAuthImp {
    
    //methode login et signup 
    public AuthenticationResponse registerUtilsateur(Utilisateur user);
    public AuthenticationResponse loginUtilisateur(LoginRequest loginRequest);

    //email confirmation methods 
    public void confirmAccount(String token);
    public Utilisateur sendEmailPasswordForgetConfirmation(String userEmail);
    public void resetPassword(String token,String password) throws Exception;
    public void resetPasswordSMS(String ncin,String password) throws Exception;
    public void sendEmailConfirmation(String userEmail, String jwtToken);

    //SMS confirmation methods 
    public long sendSms(String PhoneNumber);
    public boolean verifyOtp(String PhoneNumber, String otpString);
    public boolean verifyTocken(String token);


    
   

    
}
