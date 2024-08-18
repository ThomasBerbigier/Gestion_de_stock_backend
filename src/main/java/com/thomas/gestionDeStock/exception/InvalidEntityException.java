package com.thomas.gestionDeStock.exception;

import lombok.Getter;
import java.util.List;

@Getter
public class InvalidEntityException extends RuntimeException{

    private ErrorCodes errorCode;

    private List<String> errors;

    // Exception avec message d'erreur
    public InvalidEntityException(String message) {
        super(message);
    }

    // Message avec throwable pour logger la cause
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    //  Message, cause et code erreur
    public InvalidEntityException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // Message et code erreur
    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InvalidEntityException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
