package tn.esprit.gouvernance_donnees_backend.entities.responseEntities;



import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthenticationResponse  {
    
    private String jwtToken;

    
} 
