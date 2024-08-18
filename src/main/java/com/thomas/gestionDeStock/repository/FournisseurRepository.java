package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
}
