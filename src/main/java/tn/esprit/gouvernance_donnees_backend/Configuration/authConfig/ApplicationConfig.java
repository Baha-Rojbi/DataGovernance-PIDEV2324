package tn.esprit.gouvernance_donnees_backend.Configuration.authConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import tn.esprit.gouvernance_donnees_backend.repositories.userRepositories.UtilisateurRepository;

@Configuration   
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UtilisateurRepository utilisateurRepository;

    // overirde de la methode deja definie
    @Bean
    public UserDetailsService userDetailsService() {
        
        return username -> utilisateurRepository.findByEmail(username) ;
      
    }

    // responsable de pour reccuperer les details de l'utilisateur
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());// specifier quel user detail a utiliser
        authenticationProvider.setPasswordEncoder(passwordEncoder());// cryptage de mot de passe
        return authenticationProvider;
    }

    //pour introduire les configuration au authenitifcation manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
