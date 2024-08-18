package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {
}
