package com.thomas.gestionDeStock.controllers;

import com.thomas.gestionDeStock.controllers.api.ArticleApi;
import com.thomas.gestionDeStock.dto.ArticleDto;
import com.thomas.gestionDeStock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des articles.
 * Ce contrôleur expose des points de terminaison (endpoints) pour créer, lire, mettre à jour,
 * et supprimer des articles. Il implémente l'interface {@link ArticleApi} qui définit les
 * méthodes disponibles pour les opérations sur les articles.
 */
@RestController
public class ArticleController implements ArticleApi {

    /**
     * Service de gestion des articles.
     * Utilisé pour effectuer les opérations de logique métier sur les articles.
     */
    private final ArticleService articleService;

    /**
     * Constructeur pour injecter le service de gestion des articles.
     *
     * @param articleService Service de gestion des articles à injecter.
     */
    @Autowired
    public ArticleController(
            ArticleService articleService
    ) {
        this.articleService = articleService;
    }

    /**
     * Création ou mise à jour d'un article.
     * Cette méthode appelle le service pour sauvegarder l'article et retourne l'article sauvegardé.
     *
     * @param articleDto DTO de l'article à sauvegarder.
     * @return L'article sauvegardé.
     */
    @Override
    public ArticleDto save(ArticleDto articleDto) {
        return articleService.save(articleDto);
    }

    /**
     * Recherche d'un article par son identifiant.
     * Cette méthode appelle le service pour trouver l'article en fonction de l'identifiant fourni.
     *
     * @param id Identifiant de l'article à rechercher.
     * @return L'article correspondant à l'identifiant, ou une erreur si l'article n'est pas trouvé.
     */
    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    /**
     * Recherche d'un article par son code.
     * Cette méthode appelle le service pour trouver l'article en fonction du code fourni.
     *
     * @param codeArticle Code de l'article à rechercher.
     * @return L'article correspondant au code, ou une erreur si l'article n'est pas trouvé.
     */
    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    /**
     * Récupération de tous les articles.
     * Cette méthode appelle le service pour obtenir la liste de tous les articles disponibles.
     *
     * @return La liste de tous les articles.
     */
    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    /**
     * Suppression d'un article par son identifiant.
     * Cette méthode appelle le service pour supprimer l'article correspondant à l'identifiant fourni.
     *
     * @param id Identifiant de l'article à supprimer.
     */
    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
