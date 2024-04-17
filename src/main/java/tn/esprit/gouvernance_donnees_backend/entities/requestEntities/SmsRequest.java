package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SmsRequest {
    private String phoneNumber;
    private String message;
}
