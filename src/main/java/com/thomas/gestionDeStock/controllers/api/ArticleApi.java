package com.thomas.gestionDeStock.controllers.api;


import com.thomas.gestionDeStock.dto.ArticleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de l'API pour la gestion des articles.
 * Cette interface sert de contrat pour l'implémentation du contrôleur des articles.
 */
@Tag(name = "articles", description = "API pour la gestion des articles")
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
    @Operation(summary = "Enregistrer un article", description = "Cette méthode permet d'enregistrer ou de modifier un article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet article créé ou modifié"),
            @ApiResponse(responseCode = "400", description = "L'objet article n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto articleDto);

    /**
     * Récupère un article par son ID.
     *
     * @param id L'ID de l'article à récupérer.
     * @return L'objet ArticleDto correspondant à l'ID.
     */
    @GetMapping(value = APP_ROOT + "/articles/{idArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher un article par ID", description = " Cette méthode permet de rechercher un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'article a été trouvé dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    /**
     * Récupère un article par son code.
     *
     * @param codeArticle Le code de l'article à récupérer.
     * @return L'objet ArticleDto correspondant au code.
     */
    @GetMapping(value = APP_ROOT + "/articles/{codeArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher un article par CODE", description = " Cette méthode permet de rechercher un article par son CODE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'article a été trouvé dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    /**
     * Récupère la liste de tous les articles.
     *
     * @return Une liste de tous les articles sous forme de DTO.
     */
    @GetMapping(value = APP_ROOT + "/articles/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Renvoi la liste des articles", description = " Cette méthode permet de rechercher et renvoyer la liste des articles existants dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste vide"),
    })
    List<ArticleDto> findAll();

    /**
     * Supprime un article par son ID.
     *
     * @param id L'ID de l'article à supprimer.
     */
    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @Operation(summary = "Supprimer un article", description = " Cette méthode permet de supprimer un article par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'article a été supprimé"),
    })
    void delete(@PathVariable("idArticle") Integer id);

}
