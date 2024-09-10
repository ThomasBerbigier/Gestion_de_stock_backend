package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.ArticleDto;
import com.thomas.gestionDeStock.dto.LigneCommandeClientDto;
import com.thomas.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.thomas.gestionDeStock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {

    /**
     * Enregistre ou met à jour un article dans la base de données.
     *
     * @param articleDto l'article à enregistrer ou à mettre à jour.
     * @return l'article enregistré ou mis à jour.
     */
    ArticleDto save(ArticleDto articleDto);

    /**
     * Recherche un article par son identifiant.
     *
     * @param id l'identifiant de l'article à rechercher.
     * @return l'article correspondant à l'identifiant, ou null s'il n'existe pas.
     */
    ArticleDto findById(Integer id);

    /**
     * Recherche un article par son code.
     *
     * @param codeArticle le code de l'article à rechercher.
     * @return l'article correspondant au code, ou null s'il n'existe pas.
     */
    ArticleDto findByCodeArticle(String codeArticle);

    /**
     * Retourne la liste de tous les articles dans la base de données.
     *
     * @return une liste de tous les articles.
     */
    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategorie(Integer idCategorie);



    /**
     * Supprime un article par son identifiant.
     *
     * @param id l'identifiant de l'article à supprimer.
     */
    void delete(Integer id);

}
