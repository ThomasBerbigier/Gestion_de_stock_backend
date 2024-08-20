package com.thomas.gestionDeStock.exception;

import lombok.Getter;

/**
 * Énumération des codes d'erreur utilisés dans l'application.
 * Chaque code d'erreur est associé à un entier unique pour faciliter l'identification et le traitement des erreurs.
 */
@Getter
public enum ErrorCodes {

    ARTICLE_NOT_FOUND(1000),
    ARTICLE_NOT_VALID(1001),
    CATEGORY_NOT_FOUND(2000),
    CATEGORY_NOT_VALID(2001),
    // TODO compléter le reste des codes erreurs
    CLIENT_NOT_FOUND(3000),
    CLIENT_NOT_VALID(3001),
    COMMANDE_CLIENT_NOT_FOUND(4000),
    COMMANDE_FOURNISSEUR_NOT_FOUND(5000),
    ENTREPRISE_NOT_FOUND(6000),
    ENTREPRISE_NOT_VALID(6001),
    FOURNISSEUR_NOT_FOUND(7000),
    FOURNISSEUR_NOT_VALID(7001),
    LIGNE_COMMANDE_CLIENT_NOT_FOUND(8000),
    LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(9000),
    LIGNE_VENTE_NOT_FOUND(10000),
    MOUVEMENT_STOCK_NOT_FOUND(11000),
    UTILISATEUR_NOT_FOUND(12000),
    UTILISATEUR_NOT_VALID(12001),
    VENTE_NOT_FOUND(13000);

    // Le code numérique associé à chaque erreur
    private final int code;

    /**
     * Constructeur de l'énumération `ErrorCodes`.
     *
     * @param code Valeur entière associée au code d'erreur.
     */
    ErrorCodes(int code) {
        this.code = code;
    }

}
