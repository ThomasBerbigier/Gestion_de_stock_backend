package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.VentesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.*;

@Tag(name = "ventes", description = "API pour la gestion des ventes")
public interface VentesApi {

    @PostMapping(CREATE_VENTES_ENDPOINT)
    VentesDto save(@RequestBody VentesDto ventesDto);

    @GetMapping(FIND_VENTES_BY_CODE_ENDPOINT)
    VentesDto findByCode(@PathVariable("codeVentes") String code);

    @GetMapping(FIND_VENTES_BY_ID_ENDPOINT)
    VentesDto findById(@PathVariable("idVentes")Integer id);

    @GetMapping(FIND_ALL_VENTES_ENDPOINT)
    List<VentesDto> findAll();

    @DeleteMapping(DELETE_VENTES_ENDPOINT)
    void delete(@PathVariable("idVentes") Integer id);
}
