package com.thomas.gestionDeStock.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{

    private ErrorCodes errorCode;

    // Exception avec message d'erreur
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Message avec throwable pour logger la cause
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    //  Message, cause et code erreur
    public EntityNotFoundException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // Message et code erreur
    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
