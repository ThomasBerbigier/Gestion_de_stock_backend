package com.thomas.gestionDeStock.dto;

import com.thomas.gestionDeStock.model.MouvementDeStock;
import com.thomas.gestionDeStock.model.SourceMouvementDeStock;
import com.thomas.gestionDeStock.model.TypeMouvementDeStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MouvementDeStockDto {

    private Integer id;

    private Instant dateMouvement;

    private BigDecimal quantite;

    private TypeMouvementDeStock typeMouvementDeStock;

    private ArticleDto article;

    private SourceMouvementDeStock sourceMouvementDeStock;

    private Integer idEntreprise;

    // Entité vers Dto pour la récupération des données
    public static MouvementDeStockDto fromEntity(MouvementDeStock mouvementDeStock) {
        if (mouvementDeStock == null) {
            return null;
        }

        return MouvementDeStockDto.builder()
                .id(mouvementDeStock.getId())
                .dateMouvement(mouvementDeStock.getDateMouvement())
                .quantite(mouvementDeStock.getQuantite())
                .typeMouvementDeStock(mouvementDeStock.getTypeMouvementDeStock())
                .sourceMouvementDeStock(mouvementDeStock.getSourceMouvementDeStock())
                .article(ArticleDto.fromEntity(mouvementDeStock.getArticle()))
                .idEntreprise(mouvementDeStock.getIdEntreprise())
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static MouvementDeStock toEntity(MouvementDeStockDto mouvementDeStockDto) {
        if (mouvementDeStockDto == null) {
            return null;
        }

        MouvementDeStock mouvementDeStock = new MouvementDeStock();
        mouvementDeStock.setId(mouvementDeStockDto.getId());
        mouvementDeStock.setDateMouvement(mouvementDeStockDto.getDateMouvement());
        mouvementDeStock.setQuantite(mouvementDeStockDto.getQuantite());
        mouvementDeStock.setTypeMouvementDeStock(mouvementDeStockDto.getTypeMouvementDeStock());
        mouvementDeStock.setSourceMouvementDeStock(mouvementDeStockDto.getSourceMouvementDeStock());
        mouvementDeStock.setArticle(ArticleDto.toEntity(mouvementDeStockDto.getArticle()));
        mouvementDeStock.setIdEntreprise(mouvementDeStock.getIdEntreprise());

        return mouvementDeStock;
    }
}
