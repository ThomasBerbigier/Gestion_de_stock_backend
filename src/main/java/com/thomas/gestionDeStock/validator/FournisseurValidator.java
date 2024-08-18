package com.thomas.gestionDeStock.validator;

import com.thomas.gestionDeStock.dto.ClientDto;
import com.thomas.gestionDeStock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {
    public static List<String> validate(FournisseurDto fournisseurDto) {
        List<String> errors = new ArrayList<String>();

        if(fournisseurDto == null) {
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prénom du fournisseur");
            errors.add("Veuillez renseigner l'Email' du fournisseur");
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(fournisseurDto.getNom())) {
            errors.add("Veuillez renseigner le nom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())) {
            errors.add("Veuillez renseigner le prénom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getMail())) {
            errors.add("Veuillez renseigner l'Email' du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
        }
        errors.addAll(AdresseValidator.validate(fournisseurDto.getAdresse()));

        return errors;
    }

}
