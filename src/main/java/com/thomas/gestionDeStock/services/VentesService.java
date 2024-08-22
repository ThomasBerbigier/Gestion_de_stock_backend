package com.thomas.gestionDeStock.services;

import com.thomas.gestionDeStock.dto.VentesDto;
import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto ventesDto);

    VentesDto findByCode(String code);

    VentesDto findById(Integer id);

    List<VentesDto> findAll();

    void delete(Integer id);
}
