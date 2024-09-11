package com.thomas.gestionDeStock.controllers;

import com.thomas.gestionDeStock.controllers.api.MouvementDeStockApi;
import com.thomas.gestionDeStock.dto.MouvementDeStockDto;
import com.thomas.gestionDeStock.services.MouvementDeStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MouvementDeStockController implements MouvementDeStockApi {

    private final MouvementDeStockService mouvementDeStockService;

    @Autowired
    public MouvementDeStockController(MouvementDeStockService mouvementDeStockService) {
        this.mouvementDeStockService = mouvementDeStockService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return mouvementDeStockService.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementDeStockDto> mvtStkArticle(Integer idArticle) {
        return mouvementDeStockService.mvtStkArticle(idArticle);
    }

    @Override
    public MouvementDeStockDto entreeStock(MouvementDeStockDto mouvementDeStockDto) {
        return mouvementDeStockService.entreeStock(mouvementDeStockDto);
    }

    @Override
    public MouvementDeStockDto sortieStock(MouvementDeStockDto mouvementDeStockDto) {
        return mouvementDeStockService.sortieStock(mouvementDeStockDto);
    }

    @Override
    public MouvementDeStockDto correctionStockPos(MouvementDeStockDto mouvementDeStockDto) {
        return mouvementDeStockService.correctionStockPos(mouvementDeStockDto);
    }

    @Override
    public MouvementDeStockDto correctionStockNeg(MouvementDeStockDto mouvementDeStockDto) {
        return mouvementDeStockService.correctionStockNeg(mouvementDeStockDto);
    }
}
