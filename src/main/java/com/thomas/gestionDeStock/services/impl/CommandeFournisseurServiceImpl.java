package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.*;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.model.*;
import com.thomas.gestionDeStock.repository.*;
import com.thomas.gestionDeStock.services.CommandeFournisseurService;
import com.thomas.gestionDeStock.services.MouvementDeStockService;
import com.thomas.gestionDeStock.validator.ArticleValidator;
import com.thomas.gestionDeStock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;
    private final MouvementDeStockService mouvementDeStockService;

    @Autowired
    public CommandeFournisseurServiceImpl(
            CommandeFournisseurRepository commandeFournisseurRepository,
            LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
            FournisseurRepository fournisseurRepository,
            ArticleRepository articleRepository,
            MouvementDeStockService mouvementDeStockService)
    {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mouvementDeStockService = mouvementDeStockService;
    }


    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {

        List<String>  errors = CommandeFournisseurValidator.validate(commandeFournisseurDto);
        if (!errors.isEmpty()) {
            log.error("Erreur de validation commandeFournisseur");
            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getFournisseur().getId());
        if(fournisseur.isEmpty()) {
            log.warn("Ce fournisseur avec l'ID {} n'existe pas", commandeFournisseurDto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur ayant l'ID " + commandeFournisseurDto.getFournisseur().getId() + "n'a été trouvé dans la BDD.", ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();
        if(commandeFournisseurDto.getLigneCommandeFournisseur() != null) {
            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligneCmdFrs -> {
                    if(ligneCmdFrs.getArticle() != null) {
                        Optional<Article> article = articleRepository.findById(ligneCmdFrs.getArticle().getId());
                        if(article.isEmpty()) {
                            articleErrors.add("L'article avec l'ID " + ligneCmdFrs.getArticle().getId() + "n'existe pas");
                        }
                    } else {
                        articleErrors.add("Impossible d'enregistrer la commande avec un article nul");
                    }
            });
        }
        if(!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("L'article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));
        if(commandeFournisseurDto.getLigneCommandeFournisseur() != null) {
            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligneCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligneCmdFrs);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrs);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(savedCmdFrs);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if(!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur est nul");
            throw  new InvalidOperationException("Impossible de modifier l'état de la commande avec un état nul",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if(commandeFournisseur.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if(quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'état de la ligne commande fournisseur n'existe pas");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec une quantitée zéro ou nulle",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {

        checkIdCommande(idCommande);
        if(idFournisseur == null) {
            log.error("L'ID du fournisseur n'existe pas");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec un ID fournisseur nul",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if(fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune fournisseur avec l'id " + idFournisseur + " n'a été trouvée",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur)));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouveau");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun article avec l'id " + idArticle + " n'a été trouvée",
                    ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSave = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSave.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSave);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        // Vérifie LigneCommandeFournisseur et informe le fournisseur si absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Le code de la commande fournisseur n'existe pas");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec le code " + code + " n'a été trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null) {
            log.error("L'id de la commande fournisseur n'existe pas");
            return null;
        }

        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec l'id " + id + " n'a été trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return List.of();
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("L'id de la commande fournisseur n'existe pas");
            return;
        }
        commandeFournisseurRepository.deleteById(id);
    }

    private void checkIdCommande(Integer idCommande) {
        if(idCommande == null) {
            log.error("L'id de la commande fournisseur n'existe pas");
            throw new InvalidOperationException("Impossible de modifier l'état' commande avec un ID nul",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {

        if(idLigneCommande == null) {
            log.error("L'état de la ligne commande fournisseur n'existe pas");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec une ligne de commande nulle",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {

        if(idArticle == null) {
            log.error("L'ID de l'article " + msg + " n'existe pas");
            throw new InvalidOperationException("Impossible de modifier l'article avec un " + msg +" ID nul",
                    ErrorCodes.ARTICLE_NOT_VALID);
        }
    }

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if(commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livrée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseurs.forEach(lig -> {
            effectuerEntree(lig);
        });
    }

    private void effectuerEntree(LigneCommandeFournisseur lig) {
        MouvementDeStockDto mvtStkDto = MouvementDeStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMouvement(Instant.now())
                .typeMouvementDeStock(TypeMouvementDeStock.SORTIE)
                .sourceMouvementDeStock(SourceMouvementDeStock.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mouvementDeStockService.sortieStock(mvtStkDto);
    }
}
