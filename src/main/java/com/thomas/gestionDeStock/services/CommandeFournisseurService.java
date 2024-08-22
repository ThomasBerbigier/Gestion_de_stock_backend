package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.CommandeFournisseurDto;

import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    CommandeFournisseurDto findByCode(String code);

    CommandeFournisseurDto findById(Integer id);

    List<CommandeFournisseurDto> findAll();

    void delete(Integer id);
}
