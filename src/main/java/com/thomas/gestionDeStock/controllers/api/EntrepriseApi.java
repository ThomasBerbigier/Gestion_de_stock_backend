package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.EntrepriseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.*;

@Tag(name = "entreprises", description = "API pour la gestion des entreprises")
public interface EntrepriseApi {

    @PostMapping(CREATE_ENTREPRISE_ENDPOINT)
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(FIND_ENTREPRISE_BY_ID_ENDPOINT)
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(FIND_ALL_ENTREPRISE_ENDPOINT)
    List<EntrepriseDto> findAll();

    @DeleteMapping(DELETE_ENTREPRISE_ENDPOINT)
    void delete(@PathVariable("idEntreprise") Integer id);
}
