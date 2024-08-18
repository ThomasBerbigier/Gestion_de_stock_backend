package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.MouvementDeStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementDeStockRepository extends JpaRepository<MouvementDeStock, Integer> {
}
