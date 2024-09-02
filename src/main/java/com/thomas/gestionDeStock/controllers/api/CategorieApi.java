package com.thomas.gestionDeStock.controllers.api;


import com.thomas.gestionDeStock.dto.CategorieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

@Tag(name = "categories", description = "API pour la gestion des catégories")
public interface CategorieApi {

    @PostMapping(value = APP_ROOT + "/categories/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Enregistrer une catégorie", description = "Cette méthode permet d'enregistrer ou de modifier une catégorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet catégorie créé ou modifié"),
            @ApiResponse(responseCode = "400", description = "L'objet catégorie n'est pas valide")
    })
    CategorieDto save(@RequestBody CategorieDto categorieDto);

    @GetMapping(value = APP_ROOT + "/categories/{codeCategorie}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher une catégorie par CODE", description = " Cette méthode permet de rechercher une catégorie par son CODE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La catégorie a été trouvé dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune catégorie n'existe dans la BDD avec le CODE fourni")
    })
    CategorieDto findByCode(@PathVariable("codeCategorie") String codeCategorie);

    @GetMapping(value = APP_ROOT + "/categories/{idCategorie}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher une catégorie par ID", description = " Cette méthode permet de rechercher une catégorie par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Une catégorie a été trouvé dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune catégorie n'existe dans la BDD avec l'ID fourni")
    })
    CategorieDto findById(@PathVariable("idCategorie") Integer idCategorie);

    @GetMapping(value = APP_ROOT + "/categories/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Renvoi la liste des catégories", description = " Cette méthode permet de rechercher et renvoyer la liste des catégories existantes dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste vide"),
    })
    List<CategorieDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategorie}")
    @Operation(summary = "Supprimer une catégorie", description = " Cette méthode permet de supprimer une catégorie par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La catégorie a été supprimée"),
    })
    void delete(@PathVariable("idCategorie") Integer id);
}
