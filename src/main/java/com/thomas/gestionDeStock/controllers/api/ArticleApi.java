package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.ArticleDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de l'API pour la gestion des articles.
 * Cette interface sert de contrat pour l'implémentation du contrôleur des articles.
 */
public interface ArticleApi {

    /**
     * Crée un nouvel article ou met à jour un article existant.
     *
     * @param articleDto L'objet DTO de l'article à créer ou mettre à jour.
     * @return L'objet ArticleDto sauvegardé.
     */
    @PostMapping(value = APP_ROOT + "/articles/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto save(@RequestBody ArticleDto articleDto);

    /**
     * Récupère un article par son ID.
     *
     * @param id L'ID de l'article à récupérer.
     * @return L'objet ArticleDto correspondant à l'ID.
     */
    @GetMapping(value = APP_ROOT + "/articles/{idArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    /**
     * Récupère un article par son code.
     *
     * @param codeArticle Le code de l'article à récupérer.
     * @return L'objet ArticleDto correspondant au code.
     */
    @GetMapping(value = APP_ROOT + "/articles/{codeArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    /**
     * Récupère la liste de tous les articles.
     *
     * @return Une liste de tous les articles sous forme de DTO.
     */
    @GetMapping(value = APP_ROOT + "/articles/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAll();

    /**
     * Supprime un article par son ID.
     *
     * @param id L'ID de l'article à supprimer.
     */
    @DeleteMapping(value = APP_ROOT + "/article/delete/{idArticle}")
    void delete(@PathVariable("idArticle") Integer id);

}
