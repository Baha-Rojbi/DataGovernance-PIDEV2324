package tn.esprit.gouvernance_donnees_backend.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.EmailConfirmationRequest;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IAuthImp;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.UtilisateurRepository;




@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/gouvernanceDonnees/auth")
@RestController
public class AuthController {

    private final IAuthImp iAuthImp;
    private UtilisateurRepository utilisateurRepository;



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUtilsateur(@RequestBody Utilisateur user) {
        return ResponseEntity.ok(iAuthImp.registerUtilsateur(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUtilisateur(@RequestBody LoginRequest user) {
        return ResponseEntity.ok(iAuthImp.loginUtilisateur(user));
    }
    
    @GetMapping("/confirm-account")
    public String confirmAccount(@RequestParam("token") String token) {
        iAuthImp.confirmAccount(token);
        return "Account confirmed successfully!";
    }

  
    @GetMapping("/sendMailForgetPassword/{email}")
    public Utilisateur sendMailForgetPassword(@PathVariable String email) {
        return iAuthImp.sendEmailPasswordForgetConfirmation(email);
    }

    @PutMapping("/resetPassword/{token}/{password}")
    public void resetPassword(@PathVariable String token, @PathVariable String password){

        try {
            this.iAuthImp.resetPassword(token, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    @PutMapping("/resetPasswordSMS/{ncin}/{password}")
    public void resetPasswordSMS(@PathVariable String ncin, @PathVariable String password){

        try {
            this.iAuthImp.resetPasswordSMS(ncin, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @PostMapping("/send-email-confirmation")
    public void sendEmailConfirmation(@RequestBody EmailConfirmationRequest request) {
        String userEmail = request.getEmail();
        String jwtToken = request.getJwtToken();
        // Call the sendEmailConfirmation method
        this.iAuthImp.sendEmailConfirmation(userEmail, jwtToken);
    }
    
    @PostMapping("/send-SMS-confirmation/{phoneNumber}")
    public long sendSmsConfirmation(@PathVariable String  phoneNumber) {
        return this.iAuthImp.sendSms(phoneNumber);
    }

    @GetMapping("/getNcinOrEmail/{value}")
    public Utilisateur getNcinOrEmail(@PathVariable String value) {
        return this.utilisateurRepository.checkEmailOrNcin(value);
    }

    @PostMapping("/verifyOTP/{phoneNumber}/{OTP}")
    public boolean verifyOTP(@PathVariable String phoneNumber,@PathVariable String OTP) {        
        return this.iAuthImp.verifyOtp(phoneNumber, OTP);
    }

    
    @PostMapping("/verifyToken")
    public Boolean verifyToken(@RequestBody String token) {
        return this.iAuthImp.verifyTocken(token);
    }
    
    


    
    
}
