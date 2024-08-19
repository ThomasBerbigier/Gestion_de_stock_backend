package com.thomas.gestionDeStock.exception;

import lombok.Getter;
import java.util.List;

/**
 * Exception personnalisée pour gérer les cas d'entités invalides dans l'application.
 * Cette classe étend RuntimeException pour créer une exception non vérifiée.
 */
@Getter
public class InvalidEntityException extends RuntimeException{

    /**
     * Code d'erreur associé à cette exception.
     * Utilisé pour identifier le type d'erreur spécifique qui a provoqué cette exception.
     * Les codes d'erreur sont définis dans l'énumération `ErrorCodes`.
     */
    private ErrorCodes errorCode;

    /**
     * Liste des erreurs spécifiques liées à l'entité invalide.
     * Cette liste contient des messages d'erreur supplémentaires pour fournir des détails
     * sur les problèmes de validation ou d'autres erreurs liées à l'entité. Elle peut être nulle si
     * il n'y a pas d'erreurs supplémentaires.
     */
    private List<String> errors;

    /**
     * Constructeur de l'exception avec un message d'erreur.
     *
     * @param message Message décrivant l'erreur.
     */
    public InvalidEntityException(String message) {
        super(message);
    }

    /**
     * Constructeur de l'exception avec un message d'erreur et une cause.
     *
     * @param message Message décrivant l'erreur.
     * @param cause La cause de l'erreur, souvent utilisée pour le logging et la traçabilité.
     */
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur de l'exception avec un message d'erreur, une cause et un code d'erreur.
     *
     * @param message Message décrivant l'erreur.
     * @param cause La cause de l'erreur, souvent utilisée pour le logging et la traçabilité.
     * @param errorCode Code d'erreur associé à cette exception.
     */
    public InvalidEntityException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur de l'exception avec un message d'erreur et un code d'erreur.
     *
     * @param message Message décrivant l'erreur.
     * @param errorCode Code d'erreur associé à cette exception.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur de l'exception avec un message d'erreur, un code d'erreur et une liste d'erreurs supplémentaires.
     *
     * @param message Message décrivant l'erreur.
     * @param errorCode Code d'erreur associé à cette exception.
     * @param errors Liste des erreurs spécifiques liées à l'entité invalide.
     */
    public InvalidEntityException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
