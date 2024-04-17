package tn.esprit.gouvernance_donnees_backend.repositories.userRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Team;
import tn.esprit.gouvernance_donnees_backend.entities.userEntities.Utilisateur;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select m.members from Team m where id=?1")
    public List<Utilisateur> getOwnedTeamMemebers(Long ownedTeamId);

}
