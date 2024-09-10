package com.thomas.gestionDeStock.handlers;

import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.ErrorCodes;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import com.thomas.gestionDeStock.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gère les exceptions de type EntityNotFoundException.
     * Cette méthode est déclenchée lorsqu'une exception EntityNotFoundException est levée
     * dans l'application. Elle retourne une réponse avec le statut HTTP 404 (Not Found)
     * et un objet ErrorDto contenant les détails de l'erreur.
     *
     * @param exception L'exception levée.
     * @param webrequest L'objet WebRequest permettant d'obtenir des informations supplémentaires sur la requête.
     * @return Une ResponseEntity contenant un ErrorDto et un statut HTTP 404.
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webrequest) {
        // Définir le statut HTTP à 404 (Not Found)
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        // Construire l'objet ErroDto avec les détails de l'exception
        ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        // Retourner une réponse avec l'objet ErroDto et le statut HTTP 404
        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler({InvalidOperationException.class})
    public ResponseEntity<ErrorDto> handleException(InvalidOperationException exception, WebRequest webrequest) {
        // Définir le statut HTTP à 404 (Not Found)
        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        // Construire l'objet ErroDto avec les détails de l'exception
        ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        // Retourner une réponse avec l'objet ErroDto et le statut HTTP 404
        return new ResponseEntity<>(errorDto, notFound);
    }

    /**
     * Gère les exceptions de type InvalidEntityException.
     * Cette méthode est déclenchée lorsqu'une exception InvalidEntityException est levée
     * dans l'application. Elle retourne une réponse avec le statut HTTP 400 (Bad Request)
     * et un objet ErrorDto contenant les détails de l'erreur, y compris une liste d'erreurs spécifiques.
     *
     * @param exception L'exception levée.
     * @param webrequest L'objet WebRequest permettant d'obtenir des informations supplémentaires sur la requête.
     * @return Une ResponseEntity contenant un ErrorDto et un statut HTTP 400.
     */
    @ExceptionHandler({InvalidEntityException.class})
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webrequest) {
        // Définir le statut HTTP à 400 (Bad Request)
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        // Construire l'objet ErroDto avec les détails de l'exception, y compris les erreurs spécifiques
        ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
        // Retourner une réponse avec l'objet ErroDto et le statut HTTP 400
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
                .code(ErrorCodes.BAD_CREDENTIALS)
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(Collections.singletonList("Login et / ou mot de passe incorrecte"))
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }
}
