package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentesRepository extends JpaRepository<Ventes, Integer> {
}
