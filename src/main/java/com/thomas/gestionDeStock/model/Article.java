package com.thomas.gestionDeStock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {

    /**
     * Code unique de l'article.
     * Utilisé pour identifier chaque article de manière unique.
     */
    @Column(name = "code_article")
    private String codeArticle;

    /**
     * Désignation ou nom de l'article.
     */
    @Column(name = "designation")
    private String designation;

    /**
     * Prix unitaire hors taxes de l'article.
     */
    @Column(name = "prix_unitaire_ht")
    private BigDecimal prixUnitaireHt;

    /**
     * Taux de TVA applicable à l'article.
     */
    @Column(name = "taux_tva")
    private BigDecimal tauxTva;

    /**
     * Prix unitaire TTC de l'article.
     * Ce champ peut être calculé en fonction du prix HT et du taux de TVA.
     */
    @Column(name = "prix_unitaire_ttc")
    private BigDecimal prixUnitaireTtc;

    /**
     * Chemin ou URL de la photo de l'article.
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Catégorie à laquelle l'article appartient.
     * Relation ManyToOne avec l'entité Categorie.
     */
    @ManyToOne
    @JoinColumn(name = "idcategorie")
    private Categorie categorie;

    /**
     * Identifiant de l'entreprise associée à l'article.
     * Utilisé pour lier l'article à une entreprise spécifique.
     */
    @Column(name = "identreprise")
    private Integer idEntreprise;

    /**
     * Liste des ventes contenant cet article.
     * Relation OneToMany avec l'entité LigneVente.
     */
    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;

    /**
     * Liste des lignes de commande client contenant cet article.
     * Relation OneToMany avec l'entité LigneCommandeClient.
     */
    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients;

    /**
     * Liste des lignes de commande fournisseur contenant cet article.
     * Relation OneToMany avec l'entité LigneCommandeFournisseur.
     */
    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    /**
     * Liste des mouvements de stock associés à cet article.
     * Relation OneToMany avec l'entité MouvementDeStock.
     */
    @OneToMany(mappedBy = "article")
    private List<MouvementDeStock> mouvementDeStock;
}