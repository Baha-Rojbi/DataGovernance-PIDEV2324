package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;



import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LoginRequest  {
    private String email;
    String motDePasse;
} 
