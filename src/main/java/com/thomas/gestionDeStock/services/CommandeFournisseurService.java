package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.CommandeFournisseurDto;
import com.thomas.gestionDeStock.dto.LigneCommandeFournisseurDto;
import com.thomas.gestionDeStock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDto updateArticle(Integer idCommande,Integer idLigneCommande, Integer idArticle);

    // Delete article ==> delete LigneCommandeFournisseur
    CommandeFournisseurDto deleteArticle(Integer idCommande,Integer idLigneCommande);

    CommandeFournisseurDto findByCode(String code);

    CommandeFournisseurDto findById(Integer id);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
