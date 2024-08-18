package com.thomas.gestionDeStock.validator;

import com.thomas.gestionDeStock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static List<String> validate(ClientDto clientDto) {
        List<String> errors = new ArrayList<>();

        if(clientDto == null) {
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner le prénom du client");
            errors.add("Veuillez renseigner l'Email' du client");
            errors.add("Veuillez renseigner le numéro de téléphone du client");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(clientDto.getNom())) {
            errors.add("Veuillez renseigner le nom du client");
        }
        if(!StringUtils.hasLength(clientDto.getPrenom())) {
            errors.add("Veuillez renseigner le prénom du client");
        }
        if(!StringUtils.hasLength(clientDto.getMail())) {
            errors.add("Veuillez renseigner l'Email' du client");
        }
        if(!StringUtils.hasLength(clientDto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone du client");
        }
        errors.addAll(AdresseValidator.validate(clientDto.getAdresse()));
        return errors;
    }

}
