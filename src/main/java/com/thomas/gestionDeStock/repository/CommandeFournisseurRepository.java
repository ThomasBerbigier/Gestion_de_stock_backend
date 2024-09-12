package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.CommandeClient;
import com.thomas.gestionDeStock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List<CommandeFournisseur> findAllByFournisseurId(Integer id);
}

