package com.thomas.gestionDeStock.exception;

import lombok.Getter;

/**
 * Exception lancée lorsque une entité demandée n'est pas trouvée dans la base de données.
 * Cette exception est utilisée pour signaler que l'entité demandée (par exemple, un article,
 * un client, etc.) n'a pas été trouvée, ce qui est souvent dû à une recherche avec un identifiant
 * invalide ou inexistant.
 */
@Getter
public class EntityNotFoundException extends RuntimeException{

    /**
     * Code d'erreur associé à cette exception.
     * Utilisé pour fournir des informations supplémentaires sur le type d'erreur rencontré.
     */
    private ErrorCodes errorCode;

    /**
     * Constructeur pour créer une instance de `EntityNotFoundException` avec un message d'erreur.
     *
     * @param message Message d'erreur à associer à cette exception.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur pour créer une instance de `EntityNotFoundException` avec un message d'erreur
     * et une cause sous-jacente.
     *
     * @param message Message d'erreur à associer à cette exception.
     * @param cause   Cause sous-jacente de l'exception, utile pour le logging et le débogage.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur pour créer une instance de `EntityNotFoundException` avec un message d'erreur,
     * une cause sous-jacente, et un code d'erreur spécifique.
     *
     * @param message   Message d'erreur à associer à cette exception.
     * @param cause     Cause sous-jacente de l'exception, utile pour le logging et le débogage.
     * @param errorCode Code d'erreur associé à cette exception.
     */
    public EntityNotFoundException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur pour créer une instance de `EntityNotFoundException` avec un message d'erreur
     * et un code d'erreur spécifique.
     *
     * @param message   Message d'erreur à associer à cette exception.
     * @param errorCode Code d'erreur associé à cette exception.
     */
    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
