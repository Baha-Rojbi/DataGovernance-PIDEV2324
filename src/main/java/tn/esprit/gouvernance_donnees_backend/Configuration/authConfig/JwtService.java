package tn.esprit.gouvernance_donnees_backend.Configuration.authConfig;



import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final KeyPair keyPair;

    public JwtService() {
        this.keyPair = generateKeyPair();
    }


    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);// L'opérateur :: est utilisé pour référencer une méthode sans
                                                          // l'invoquer immédiatement
    }

    // return type est generic
    // Fucntion est une interface qui prend en entrée claim et qui renvoie en sortie
    // type T
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    // claims sont le subject dans jwt token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(keyPair.getPublic())// signature dans le jwt
                .build()
                .parseClaimsJws(jwtToken)// reccuperer le subject a partir du token
                .getBody();
    }

    public boolean isTokenIsValid(String jwtToken, UserDetails userDetails) {
        String username = extractUserName(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }


    public boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String generateToken(UserDetails userdetail) {
        return generateToken(new HashMap<>(), userdetail);
    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Error generating ECDSA key pair", e);
        }
    }

    public String generateToken(Map<String, Object> extraClaim, UserDetails userdetail) {
        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(userdetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(this.keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

    public String generateTokenResetPassword(Map<String, Object> extraClaim, UserDetails userdetail) {
        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(userdetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + 5 * 60 * 1000))
                .signWith(this.keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

}
