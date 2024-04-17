package tn.esprit.gouvernance_donnees_backend.Configuration.aspectConfig;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tn.esprit.gouvernance_donnees_backend.entities.responseEntities.AuthenticationResponse;
@Aspect
@Component
public class AuthAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthAspect.class);

    @Before("execution(* tn.esprit.gouvernance_donnees_backend.implementation.services.user.AuthServiceImp.registerUtilsateur(..))")
    public void logBeforeRegister() {
        logger.info("Attempting to register a user...");
    }

    @AfterReturning(
            pointcut = "execution(* tn.esprit.gouvernance_donnees_backend.implementation.services.user.AuthServiceImp.registerUtilsateur(..))",
            returning = "result")
    public void logAfterRegister(AuthenticationResponse result) {
        logger.info("User registered successfully with JWT token: {}", result.getJwtToken());
    }

    @Before("execution(* tn.esprit.gouvernance_donnees_backend.implementation.services.user.AuthServiceImp.loginUtilisateur(..))")
    public void logBeforeLogin() {
        logger.info("Attempting to login a user...");
    }

    @AfterReturning(
            pointcut = "execution(* tn.esprit.gouvernance_donnees_backend.implementation.services.user.AuthServiceImp.loginUtilisateur(..))",
            returning = "result")
    public void logAfterLogin(AuthenticationResponse result) {
        logger.info("User logged in successfully with JWT token: {}", result.getJwtToken());
    }
}
