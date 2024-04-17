package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class EmailConfirmationRequest  {
    private String email;
    private String jwtToken;
}
