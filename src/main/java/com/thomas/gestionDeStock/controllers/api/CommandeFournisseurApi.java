package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.CommandeFournisseurDto;
import com.thomas.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.thomas.gestionDeStock.model.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.*;

@Tag(name = "commandesfournisseurs", description = "API pour la gestion des commandes fournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto commandeFournisseurDto);

    @PatchMapping(UPDATE_ETAT_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande")Integer idCommande,
                                              @PathVariable("etatCommande")EtatCommande etatCommande);

    @PatchMapping(UPDATE_QUANTITE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande")Integer idCommande,
                                                  @PathVariable("idLigneCommande")Integer idLigneCommande,
                                                  @PathVariable("quantite")BigDecimal quantite);

    @PatchMapping(UPDATE_FOURNISSEUR_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande")Integer idCommande,
                                             @PathVariable("idFournisseur")Integer idFournisseur);

    @PatchMapping(UPDATE_ARTICLE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande")Integer idCommande,
                                         @PathVariable("idLigneCommande")Integer idLigneCommande,
                                         @PathVariable("idArticle")Integer idArticle);

    @DeleteMapping(DELETE_ARTICLE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande")Integer idCommande,
                                         @PathVariable("idLigneCommande")Integer idLigneCommande);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    List<CommandeFournisseurDto> findAll();

    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{idCommande}")
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}
