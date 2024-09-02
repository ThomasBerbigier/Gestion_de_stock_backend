package com.thomas.gestionDeStock.controllers.api;


import com.thomas.gestionDeStock.dto.ClientDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomas.gestionDeStock.utils.Constants.APP_ROOT;

@Tag(name = "clients", description = "API pour la gestion des clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto clientDto);

    @GetMapping(value = APP_ROOT + "/clients/{idClient}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    void deleteById(@PathVariable("idClient") Integer id);
}
