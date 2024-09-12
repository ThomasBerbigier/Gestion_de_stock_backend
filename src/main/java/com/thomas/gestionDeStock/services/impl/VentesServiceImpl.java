package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.*;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import com.thomas.gestionDeStock.model.*;
import com.thomas.gestionDeStock.repository.ArticleRepository;
import com.thomas.gestionDeStock.repository.LigneVenteRepository;
import com.thomas.gestionDeStock.repository.VentesRepository;
import com.thomas.gestionDeStock.services.MouvementDeStockService;
import com.thomas.gestionDeStock.services.VentesService;
import com.thomas.gestionDeStock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private final ArticleRepository articleRepository;
    private final VentesRepository ventesRepository;
    private final LigneVenteRepository ligneVenteRepository;
    private final MouvementDeStockService mouvementDeStockService;

    @Autowired
    public VentesServiceImpl(
            ArticleRepository articleRepository,
            VentesRepository ventesRepository,
            LigneVenteRepository ligneVenteRepository,
            MouvementDeStockService mouvementDeStockService
    ) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementDeStockService = mouvementDeStockService;
    }


    @Override
    public VentesDto save(VentesDto ventesDto) {
        List<String> errors = VentesValidator.validate(ventesDto);
        if(!errors.isEmpty()) {
            log.error("Ventes n'est pas valide");
            throw  new InvalidEntityException("Ventes n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }
        List<String> articleErrors = new ArrayList<>();
        ventesDto.getLigneVentes().forEach(ligneVDto -> {
            Optional<Article> article = articleRepository.findById(ligneVDto.getArticle().getId());
            if(article.isEmpty()) {
                articleErrors.add("Aucun article avec l'ID" + ligneVDto.getArticle().getId() + "n'a été trouvé dans la BDD");
            }
        });
        if(!articleErrors.isEmpty()) {
            log.error("Un ou plusieurs article non trouvés en BDD", errors);
            throw  new InvalidEntityException("Un ou plusieurs articles non trouvés en BDD", ErrorCodes.VENTE_NOT_VALID, errors);
        }
        Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(ventesDto));
        ventesDto.getLigneVentes().forEach(ligneVDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVDto);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });
        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Le code de la commande client n'existe pas");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente avec le code " + code + " n'a été trouvée",
                        ErrorCodes.VENTE_NOT_FOUND
                ));
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null) {
            log.warn("ID Ventes n'existe pas");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a été trouvée dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null) {
            log.error("L'id de la vente n'existe pas");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une vente en cours",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig) {
        MouvementDeStockDto mvtStkDto = MouvementDeStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMouvement(Instant.now())
                .typeMouvementDeStock(TypeMouvementDeStock.SORTIE)
                .sourceMouvementDeStock(SourceMouvementDeStock.VENTE)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mouvementDeStockService.sortieStock(mvtStkDto);
    }
}
