package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.ChangerMotDePasseUtilisateurDto;
import com.thomas.gestionDeStock.dto.UtilisateurDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.*;

@Tag(name = "utilisateurs", description = "API pour la gestion des utilisateurs")
public interface UtilisateurApi {

    @PostMapping(CREATE_UTILISATEUR_ENDPOINT)
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateurDto);

    @PostMapping(UTILISATEUR_ENDPOINT + "/update/password")
    UtilisateurDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    @GetMapping(FIND_UTILISATEUR_BY_ID_ENDPOINT)
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(FIND_ALL_UTILISATEUR_ENDPOINT)
    List<UtilisateurDto> findAll();

    @DeleteMapping(DELETE_UTILISATEUR_ENDPOINT)
    void delete(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(FIND_UTILISATEUR_BY_EMAIL_ENDPOINT)
    UtilisateurDto findByEmail(@PathVariable("email") String email);
}
