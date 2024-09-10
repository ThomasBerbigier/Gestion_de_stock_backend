package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.CommandeClientDto;
import com.thomas.gestionDeStock.dto.LigneCommandeClientDto;
import com.thomas.gestionDeStock.model.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

@Tag(name = "commandesclients", description = "API pour la gestion des commandes clients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandesclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto commandeClientDto);

    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                         @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                             @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande,
                                                   @PathVariable("idClient") Integer idClient);

    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande")Integer idLigneCommande,
                                                    @PathVariable("idArticle")Integer idArticle);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande")Integer idCommande,
                                                    @PathVariable("idLigneCommande")Integer idLigneCommande);

    @GetMapping(APP_ROOT + "/commandesclients/{codeCommandeClient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(APP_ROOT + "/commandesclients/{idCommandeClient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer idCommandeClient);

    @GetMapping(APP_ROOT + "/commandesclients/all")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandesByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idCommandeClient}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}
