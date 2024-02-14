package tn.esprit.gouvernance_donnees_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.IAuthImp;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.IUtilisateurImp;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/gouvernanceDonnees/auth")
@RestController
@Slf4j
public class AuthController {

    private final IAuthImp iAuthImp;
    


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUtilsateur(@RequestBody Utilisateur user) {
        return ResponseEntity.ok(iAuthImp.registerUtilsateur(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUtilisateur(@RequestBody LoginRequest user) {
        return ResponseEntity.ok(iAuthImp.loginUtilisateur(user));
    }

    
    
}
