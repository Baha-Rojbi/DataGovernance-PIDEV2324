package tn.esprit.gouvernance_donnees_backend.entities.userEntities;


import jakarta.persistence.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;
import tn.esprit.gouvernance_donnees_backend.Configuration.GrantedAuthorityDeserializer;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements  UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    private String nom;

    private LocalDate dateNaissance;

    private LocalDate dateEmbauche;

    private String NCIN;

    private String motDePasse;

    private String numTel;

    private String email;

    private String sexe;

    private String avatar;

    private String description;

    private String societe;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Poste poste;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne
    private Adresse adresse;





    @OneToOne
    private Team ownedTeam;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Team> teams;


    
    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
       return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (status!=UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return (status==UserStatus.APPROVED);
    }

}