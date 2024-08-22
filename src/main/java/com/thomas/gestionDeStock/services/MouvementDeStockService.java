package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.MouvementDeStockDto;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementDeStockService {

    BigDecimal stockReelArticle(Integer idArticle);

    List<MouvementDeStockDto> mvtStkArticle(Integer idArticle);

    MouvementDeStockDto entreeStock(MouvementDeStockDto mouvementDeStockDto);

    MouvementDeStockDto sortieStock(MouvementDeStockDto mouvementDeStockDto);

    MouvementDeStockDto correctionStockPos(MouvementDeStockDto mouvementDeStockDto);

    MouvementDeStockDto correctionStockNeg(MouvementDeStockDto mouvementDeStockDto);

}
