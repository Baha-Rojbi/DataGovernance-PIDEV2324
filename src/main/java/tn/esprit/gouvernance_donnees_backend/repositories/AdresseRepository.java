package tn.esprit.gouvernance_donnees_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.gouvernance_donnees_backend.entities.Adresse;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse,Long> {

}
