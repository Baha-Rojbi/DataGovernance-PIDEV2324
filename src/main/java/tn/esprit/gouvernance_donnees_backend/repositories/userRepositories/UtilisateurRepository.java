package tn.esprit.gouvernance_donnees_backend.repositories.userRepositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

   public Utilisateur findByEmail(String email);

   @Query("select u from Utilisateur u where u.NCIN=?1 or u.email=?1")
   public Utilisateur checkEmailOrNcin(String value);

   @Query("select u from Utilisateur u where u.numTel=?1 and u.NCIN=?2")
   public Utilisateur getUserByPhoneAndNcin(String numTel,String NCIN);

   @Query("select u from Utilisateur u where u.status=PENDING")
   public List<Utilisateur> getPendingUsersRequests ( );

   @Query("select u from Utilisateur u where u.status = 'APPROVED' and u.idUtilisateur != ?1 " +
   "and u not in (select m from Team t join t.members m where t.id = ?2)")
   public List<Utilisateur> getAllApproveUsers (Long UserId, Long ownedTeamId);
   
   @Query("SELECT u FROM Utilisateur u JOIN Team t ON t.members = u WHERE t.id = ?1")
   public List<Utilisateur> getOwnedTeamMemebers(Long ownedTeamId);
    
}
