package com.thomas.gestionDeStock.validator;

import com.thomas.gestionDeStock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto) {
        List<String> errors = new ArrayList<String>();

        if (utilisateurDto == null) {
            errors.add("Veuillez renseigner le Nom d'utilisateur");
            errors.add("Veuillez renseigner le Prénom d'utilisateur");
            errors.add("Veuillez renseigner l'Email d'utilisateur");
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
            errors.add("Veuillez renseigner l'adresse d'utilisateur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veuillez renseigner le Nom d'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veuillez renseigner le Prénom d'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuillez renseigner l'Email d'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
        }
        if(utilisateurDto.getDateDeNaissance() == null){
            errors.add("Veuillez renseigner la date de naissance d'utilisateur");
        }
        errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));
        return errors;
    }

}
