package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;






import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Role;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.UserStatus;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class afffectRoleStatusRequest  {
    private Long idUtilisateur;
    private Role role;
    private UserStatus status;
} 