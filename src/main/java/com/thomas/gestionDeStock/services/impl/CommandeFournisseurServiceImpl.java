package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.CommandeFournisseurDto;
import com.thomas.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.model.Article;
import com.thomas.gestionDeStock.model.CommandeFournisseur;
import com.thomas.gestionDeStock.model.Fournisseur;
import com.thomas.gestionDeStock.model.LigneCommandeFournisseur;
import com.thomas.gestionDeStock.repository.*;
import com.thomas.gestionDeStock.services.CommandeFournisseurService;
import com.thomas.gestionDeStock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    public CommandeFournisseurServiceImpl(
            CommandeFournisseurRepository commandeFournisseurRepository,
            LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
            FournisseurRepository fournisseurRepository,
            ArticleRepository articleRepository)
    {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
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
    public void delete(Integer id) {
        if(id == null) {
            log.error("L'id de la commande fournisseur n'existe pas");
            return;
        }
        commandeFournisseurRepository.deleteById(id);
    }
}
