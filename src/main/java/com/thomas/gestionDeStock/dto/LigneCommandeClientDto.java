package com.thomas.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thomas.gestionDeStock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeClientDto {

    private Integer id;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private ArticleDto article;

    @JsonIgnore
    private CommandeClientDto commandeClient;

    private Integer idEntreprise;

    // Entité vers Dto pour la récupération des données
    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null) {
            return null;
        }

        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto) {
        if (ligneCommandeClientDto == null) {
            return null;
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setIdEntreprise(ligneCommandeClient.getIdEntreprise());
        return ligneCommandeClient;
    }
}
