package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
