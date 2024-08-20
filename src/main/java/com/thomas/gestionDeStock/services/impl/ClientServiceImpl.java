package com.thomas.gestionDeStock.services.impl;

import com.thomas.gestionDeStock.dto.ClientDto;
import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.model.Client;
import com.thomas.gestionDeStock.repository.ClientRepository;
import com.thomas.gestionDeStock.services.ClientService;
import com.thomas.gestionDeStock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {

        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {

        List<String> errors = ClientValidator.validate(clientDto);
        if (!errors.isEmpty()) {
            log.error("Client invalide: {}", errors);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(clientDto)
                )
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null) {
            log.error("Id n'existe pas");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);

        return client.map(ClientDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun client avec l'ID = " + id + "n'a été trouvé dans la BDD.",
                        ErrorCodes.CATEGORY_NOT_FOUND
                )
        );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            log.error("\"L'identifiant du client à supprimer est nul");
            return;
        }
        clientRepository.deleteById(id);
    }
}
