package com.thomas.gestionDeStock.dto;

import com.thomas.gestionDeStock.model.CommandeFournisseur;
import com.thomas.gestionDeStock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeFournisseurDto {

    private Integer id;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private ArticleDto article;

    private CommandeFournisseurDto commandeFournisseur;

    private Integer idEntreprise;

    // Entité vers Dto pour la récupération des données
    public static LigneCommandeFournisseurDto fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur) {
        if (ligneCommandeFournisseur == null) {
            return null;
        }

        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCommandeFournisseur.getArticle()))
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
        if (ligneCommandeFournisseurDto == null) {
            return null;
        }

        LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();
        ligneCommandeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCommandeFournisseur.setArticle(ArticleDto.toEntity(ligneCommandeFournisseurDto.getArticle()));
        ligneCommandeFournisseur.setIdEntreprise(ligneCommandeFournisseur.getIdEntreprise());

        return ligneCommandeFournisseur;
    }
}
