package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyOldPsswordRequest {

    private String newPassword;
    private String oldPassword;
}
