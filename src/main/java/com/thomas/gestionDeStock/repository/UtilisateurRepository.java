package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
}
