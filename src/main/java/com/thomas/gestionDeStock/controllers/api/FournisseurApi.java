package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.FournisseurDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.*;

@Tag(name = "fournisseurs", description = "API pour la gestion des fournisseurs")
public interface FournisseurApi {

    @PostMapping( CREATE_FOURNISSEUR_ENDPOINT)
    FournisseurDto save(@RequestBody FournisseurDto fournisseurDto);

    @GetMapping(FIND_FOURNISSEUR_BY_ID_ENDPOINT)
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(FIND_ALL_FOURNISSEUR_ENDPOINT)
    List<FournisseurDto> findAll();

    @DeleteMapping(DELETE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idFournisseur") Integer id);
}
