package tn.esprit.gouvernance_donnees_backend.entities.tracelog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity


public class TraceLog {
    //emna

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String fileName;

    private String description;

    private LocalDateTime timestamp;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;




}
