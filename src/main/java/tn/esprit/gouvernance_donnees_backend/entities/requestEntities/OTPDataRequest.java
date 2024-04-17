package tn.esprit.gouvernance_donnees_backend.entities.requestEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class OTPDataRequest {
    private final String otp;
    private final long expirationTime;
}
