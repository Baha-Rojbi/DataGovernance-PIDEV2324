package tn.esprit.gouvernance_donnees_backend.implementation.services.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.Configuration.JwtService;
import tn.esprit.gouvernance_donnees_backend.entities.Adresse;
import tn.esprit.gouvernance_donnees_backend.entities.UserStatus;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.user.IAuthImp;
import tn.esprit.gouvernance_donnees_backend.repositories.AdresseRepository;
import tn.esprit.gouvernance_donnees_backend.repositories.UtilisateurRepository;

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

    // register user

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
        sendEmailConfirmation(user.getEmail(), jwtToken);

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
            throw new BadCredentialsException("Invalid email or password");

        }
        var user = utilisateurRepository.findByEmail(loginRequest.getEmail());
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private void sendEmailConfirmation(String userEmail, String jwtToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:4200/ConfirmEmail?token=" + jwtToken);
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
        // Update user status to PENDING
        existingUser.setStatus(UserStatus.PENDING);
        utilisateurRepository.save(existingUser);
    }

}
