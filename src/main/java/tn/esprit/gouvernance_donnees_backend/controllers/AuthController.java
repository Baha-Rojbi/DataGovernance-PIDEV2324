package tn.esprit.gouvernance_donnees_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.LoginRequest;
import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
import tn.esprit.gouvernance_donnees_backend.implementation.interfaces.IUtilisateurImp;

import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RequestMapping("/gouvernanceDonnées/auth")
@RestController


public class AuthController {

    private IUtilisateurImp iAuthImp;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUtilsateur(@RequestBody Utilisateur user) {
        return ResponseEntity.ok(iAuthImp.registerUtilsateur(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUtilisateur(@RequestBody LoginRequest user) {
        return ResponseEntity.ok(iAuthImp.loginUtilisateur(user));
    }
    
}
