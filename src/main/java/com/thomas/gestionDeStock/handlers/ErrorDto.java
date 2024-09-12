package com.thomas.gestionDeStock.handlers;

import com.thomas.gestionDeStock.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    /**
     * Code de statut HTTP associé à l'erreur.
     * Par exemple, 404 pour "Not Found" ou 400 pour "Bad Request".
     */
    private Integer httpCode;

    /**
     * Code d'erreur personnalisé pour identifier le type d'erreur spécifique.
     * Utilise un énuméré (enum) ErrorCodes pour représenter les différents types d'erreurs
     * qui peuvent se produire dans l'application.
     */
    private ErrorCodes code;

    /**
     * Message décrivant l'erreur.
     * Ce message donne plus de détails sur l'erreur qui s'est produite, pour aider
     * les développeurs et les utilisateurs à comprendre ce qui s'est mal passé.
     */
    private String message;

    /**
     * Liste des erreurs supplémentaires ou des détails sur l'erreur.
     * Cette liste peut contenir plusieurs messages d'erreur, par exemple pour les erreurs de validation.
     * Elle est initialisée comme une liste vide pour éviter les problèmes de nullité.
     */
    @Builder.Default
    private List<String> errors = new ArrayList<>();

}
