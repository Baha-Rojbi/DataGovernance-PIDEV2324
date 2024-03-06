package tn.esprit.gouvernance_donnees_backend.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.gouvernance_donnees_backend.entities.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

   public Utilisateur findByEmail(String email);
   @Query("select u from Utilisateur u where u.status=PENDING")
   public List<Utilisateur> getPendingUsersRequests ( );

   
    
}
