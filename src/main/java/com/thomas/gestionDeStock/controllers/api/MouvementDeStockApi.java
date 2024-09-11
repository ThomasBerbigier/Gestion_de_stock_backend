package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.MouvementDeStockDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

@Tag(name = "MouvementDeStock", description = "API pour la gestion des mouvements de stock")
public interface MouvementDeStockApi {

    @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(APP_ROOT + "/mvtstk/filter/article/{idArticle}")
    List<MouvementDeStockDto> mvtStkArticle(@PathVariable("idArticle")Integer idArticle);

    @PostMapping(APP_ROOT + "/mvtstk/entree")
    MouvementDeStockDto entreeStock(@RequestBody MouvementDeStockDto mouvementDeStockDto);

    @PostMapping(APP_ROOT + "/mvtstk/sortie")
    MouvementDeStockDto sortieStock(@RequestBody MouvementDeStockDto mouvementDeStockDto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionpos")
    MouvementDeStockDto correctionStockPos(@RequestBody MouvementDeStockDto mouvementDeStockDto);

    @PostMapping(APP_ROOT + "/mvtstk/correctionneg")
    MouvementDeStockDto correctionStockNeg(@RequestBody MouvementDeStockDto mouvementDeStockDto);



}
