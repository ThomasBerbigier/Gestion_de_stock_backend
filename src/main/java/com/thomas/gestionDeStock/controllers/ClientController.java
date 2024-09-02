package com.thomas.gestionDeStock.controllers;

import com.thomas.gestionDeStock.controllers.api.ClientApi;
import com.thomas.gestionDeStock.dto.ClientDto;
import com.thomas.gestionDeStock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    @Autowired
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        clientService.deleteById(id);
    }
}
