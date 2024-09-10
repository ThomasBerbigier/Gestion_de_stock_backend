package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.CommandeClientDto;
import com.thomas.gestionDeStock.dto.LigneCommandeClientDto;
import com.thomas.gestionDeStock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto commandeClientDto);

    CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeClientDto updateClient(Integer idCommande, Integer idClient);

    CommandeClientDto updateArticle(Integer idCommande,Integer idLigneCommande, Integer idArticle);

    // Delete article ==> delete LigneCommandeClient
    CommandeClientDto deleteArticle(Integer idCommande,Integer idLigneCommande);

    CommandeClientDto findByCode(String code);

    CommandeClientDto findById(Integer id);

    List<CommandeClientDto> findAll();

    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

    void delete(Integer id);

}
