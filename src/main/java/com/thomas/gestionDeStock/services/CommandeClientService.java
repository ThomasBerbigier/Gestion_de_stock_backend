package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.CommandeClientDto;

import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto commandeClientDto);

    CommandeClientDto findByCode(String code);

    CommandeClientDto findById(Integer id);

    List<CommandeClientDto> findAll();

    void delete(Integer id);
}
