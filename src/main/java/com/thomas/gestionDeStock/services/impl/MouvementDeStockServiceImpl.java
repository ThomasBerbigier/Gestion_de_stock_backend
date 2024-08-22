package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.dto.MouvementDeStockDto;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.model.TypeMouvementDeStock;
import com.thomas.gestionDeStock.repository.MouvementDeStockRepository;
import com.thomas.gestionDeStock.services.ArticleService;
import com.thomas.gestionDeStock.services.MouvementDeStockService;
import com.thomas.gestionDeStock.validator.MouvementDeStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MouvementDeStockServiceImpl implements MouvementDeStockService {

    private final MouvementDeStockRepository mouvementDeStockRepository;
    private final ArticleService articleService;

    @Autowired
    public MouvementDeStockServiceImpl(MouvementDeStockRepository mouvementDeStockRepository, ArticleService articleService) {
        this.mouvementDeStockRepository = mouvementDeStockRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if (idArticle == null) {
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mouvementDeStockRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementDeStockDto> mvtStkArticle(Integer idArticle) {
        return mouvementDeStockRepository.findAllByArticleId(idArticle).stream()
                .map(MouvementDeStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MouvementDeStockDto entreeStock(MouvementDeStockDto mouvementDeStockDto) {
        return entreePositive(mouvementDeStockDto, TypeMouvementDeStock.ENTREE);
    }

    @Override
    public MouvementDeStockDto sortieStock(MouvementDeStockDto mouvementDeStockDto) {
        return sortieNegative(mouvementDeStockDto, TypeMouvementDeStock.SORTIE);
    }

    @Override
    public MouvementDeStockDto correctionStockPos(MouvementDeStockDto mouvementDeStockDto) {
        return entreePositive(mouvementDeStockDto, TypeMouvementDeStock.CORRECTION_POS);
    }

    @Override
    public MouvementDeStockDto correctionStockNeg(MouvementDeStockDto mouvementDeStockDto) {
        return sortieNegative(mouvementDeStockDto, TypeMouvementDeStock.CORRECTION_NEG);
    }

    private MouvementDeStockDto entreePositive(MouvementDeStockDto mouvementDeStockDto, TypeMouvementDeStock typeMouvementDeStock) {
        List<String> errors = MouvementDeStockValidator.validate(mouvementDeStockDto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", mouvementDeStockDto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MOUVEMENT_STOCK_NOT_FOUND, errors);
        }
        mouvementDeStockDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mouvementDeStockDto.getQuantite().doubleValue())
                )
        );
        mouvementDeStockDto.setTypeMouvementDeStock(typeMouvementDeStock);
        return MouvementDeStockDto.fromEntity(
                mouvementDeStockRepository.save(MouvementDeStockDto.toEntity(mouvementDeStockDto))
        );
    }

    private MouvementDeStockDto sortieNegative(MouvementDeStockDto mouvementDeStockDto, TypeMouvementDeStock typeMouvementDeStock) {
        List<String> errors = MouvementDeStockValidator.validate(mouvementDeStockDto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", mouvementDeStockDto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MOUVEMENT_STOCK_NOT_VALID, errors);
        }
        mouvementDeStockDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mouvementDeStockDto.getQuantite().doubleValue()) * -1
                )
        );
        mouvementDeStockDto.setTypeMouvementDeStock(typeMouvementDeStock);
        return MouvementDeStockDto.fromEntity(
                mouvementDeStockRepository.save(MouvementDeStockDto.toEntity(mouvementDeStockDto))
        );
    }
}
