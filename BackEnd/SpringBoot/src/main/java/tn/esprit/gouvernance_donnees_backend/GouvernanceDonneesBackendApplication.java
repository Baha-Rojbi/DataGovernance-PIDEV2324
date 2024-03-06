package tn.esprit.gouvernance_donnees_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@EnableAspectJAutoProxy
@SpringBootApplication
public class GouvernanceDonneesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GouvernanceDonneesBackendApplication.class, args);
	}

}
