package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.MouvementDeStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementDeStockRepository extends JpaRepository<MouvementDeStock, Integer> {

    @Query("select sum(m.quantite) from MouvementDeStock m where m.article.id = :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);

    List<MouvementDeStock> findAllByArticleId(Integer idArticle);
}
