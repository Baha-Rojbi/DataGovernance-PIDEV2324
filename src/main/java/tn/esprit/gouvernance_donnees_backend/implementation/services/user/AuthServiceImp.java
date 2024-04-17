package tn.esprit.gouvernance_donnees_backend.implementation.services.user;

import java.util.HashMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.Configuration.authConfig.JwtService;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Adresse;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IAuthImp;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IUtilisateurImp;
import tn.esprit.gouvernance_donnees_backend.implementation.services.user.Sms.OtpManager;
import tn.esprit.gouvernance_donnees_backend.implementation.services.user.Sms.TwilioSmsSenderService;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.AdresseRepository;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.UtilisateurRepository;

@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceImp implements IAuthImp {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private AdresseRepository adresseRepository;
    private EmailService emailService;
    private IUtilisateurImp iUtilisateurImp;
    private OtpManager otpManager;
    private TwilioSmsSenderService twilioSmsSenderService;

    // register user

    @SuppressWarnings("null")
    @Override
    public AuthenticationResponse registerUtilsateur(Utilisateur user) {
        if (utilisateurRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        Adresse adresse = user.getAdresse();
        adresseRepository.save(adresse);
        user.setAdresse(adresse);
        user.setStatus(UserStatus.REJECTED);
        utilisateurRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        
        // Send email confirmation
        //sendEmailConfirmation(user.getEmail(), jwtToken);

        // Log confirmation token
        System.out.println("Confirmation Token: " + jwtToken);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse loginUtilisateur(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        } catch (Exception e) {
            log.info("Authentication failed for user: {}", loginRequest.getEmail());
            log.info(e.getMessage());
            throw new BadCredentialsException("Invalid email or password");


        }
        var user = utilisateurRepository.findByEmail(loginRequest.getEmail());
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public void sendEmailConfirmation(String userEmail, String jwtToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:4200/confirmation-validation?token=" + jwtToken);
        emailService.sendEmail(mailMessage);
    }

    public void confirmAccount(String token) {
        // Validate token and extract user details
        String userEmail = jwtService.extractUserName(token);
        // Check if user exists
        Utilisateur existingUser = utilisateurRepository.findByEmail(userEmail);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        try {
            // Update user status to PENDING
            existingUser.setStatus(UserStatus.PENDING);
            utilisateurRepository.save(existingUser);
    
            
        } catch (Exception e) {
            // Handle any exceptions that occur during team creation or user status update
            // You can log the error or throw a custom exception
            throw new RuntimeException("Error confirming account and creating team", e);
        }
    }
    

    public Utilisateur sendEmailPasswordForgetConfirmation(String userEmail) {
      
        Utilisateur utilisateur = this.iUtilisateurImp.getUserInformationByLoggedEmail(userEmail);
        if(utilisateur!=null){
            var jwtToken = jwtService.generateTokenResetPassword(new HashMap<>(),utilisateur);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Forget Password!");
            mailMessage.setText("To reset your password, please click here : "
                + "http://localhost:4200/reset-password?token=" + jwtToken);
            emailService.sendEmail(mailMessage);
        }
        return utilisateur;
        
    }

    public void resetPassword(String token,String password) {
        // Validate token and extract user details
        String userEmail = jwtService.extractUserName(token);
        // Check if user exists
        Utilisateur existingUser = utilisateurRepository.findByEmail(userEmail);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        existingUser.setMotDePasse(passwordEncoder.encode(password));
        utilisateurRepository.save(existingUser);
    }

    public void resetPasswordSMS(String ncin,String password) {
        // Check if user exists
        Utilisateur existingUser = utilisateurRepository.checkEmailOrNcin(ncin);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        existingUser.setMotDePasse(passwordEncoder.encode(password));
        utilisateurRepository.save(existingUser);
    }

    @Override
    public long sendSms(String phoneNumber) {
        if (phoneNumber != null) {
            otpManager.setOtpExpirationDurationMillis(2);
            String otp = otpManager.generateOTP(6);
            otpManager.storeOTP(phoneNumber, otp);
            //this.twilioSmsSenderService.sendSms(new SmsRequest(phoneNumber,otp));
            return otpManager.getOtpExpirationDurationMillis(); // SMS sent successfully
        } else {
            return -1; // Phone number not found in repository, SMS not sent
        }
    }

    @Override
    public boolean verifyOtp(String phoneNumber,String otpString) {
        return otpManager.verifyOTP(phoneNumber, otpString);    
    }

    @Override
    public boolean verifyTocken(String token) {
        boolean verified = true;
        // Validate token and extract user details
        if(!this.jwtService.isTokenExpired(token)){
            String userEmail = jwtService.extractUserName(token);
            // Check if user exists
            Utilisateur existingUser = utilisateurRepository.findByEmail(userEmail);
            if (existingUser == null) {
                 verified = false;
            }
        }
        else{
            verified = false;
        }
        return verified;
    }





    



}
