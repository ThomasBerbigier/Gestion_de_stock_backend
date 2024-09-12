package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

    List<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByVenteId(Integer id);
}
