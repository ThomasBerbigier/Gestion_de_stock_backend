package com.thomas.gestionDeStock.validator;

import com.thomas.gestionDeStock.dto.ArticleDto;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    /**
     * Valide les informations d'un article.
     *
     * @param articleDto L'objet ArticleDto à valider.
     * @return Une liste d'erreurs sous forme de chaînes de caractères. Si la liste est vide, cela signifie que l'article est valide.
     */
    public static List<String> validate(ArticleDto articleDto) {
        List<String> errors = new ArrayList<>();

        // Vérifie si l'objet articleDto est null, auquel cas toutes les propriétés obligatoires sont manquantes.
        if(articleDto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la désignation de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
            errors.add("Veuillez renseigner le taux TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez sélectionner une catégorie d'article");
            return errors; // Retourne les erreurs car aucun champ ne peut être validé si l'objet est null.
        }
        // Vérifie que le code de l'article est renseigné.
        if(!StringUtils.hasLength(articleDto.getCodeArticle())){
            errors.add("Veuillez renseigner le code de l'article");
        }
        // Vérifie que la désignation de l'article est renseignée.
        if(!StringUtils.hasLength(articleDto.getDesignation())){
            errors.add("Veuillez renseigner la désignation de l'article");
        }
        // Vérifie que le prix unitaire hors taxes de l'article est renseigné.
        if(articleDto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
        }
        // Vérifie que le taux de TVA est renseigné.
        if(articleDto.getTauxTva() == null){
            errors.add("Veuillez renseigner le taux TVA de l'article");
        }
        // Vérifie que le prix unitaire toutes taxes comprises est renseigné.
        if(articleDto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }
        // Vérifie que l'article est associé à une catégorie.
        if(articleDto.getCategorie() == null){
            errors.add("Veuillez sélectionner une catégorie d'article");
        }
        // Retourne la liste des erreurs. Si elle est vide, cela signifie que l'article est valide.
        return errors;
    }
}
