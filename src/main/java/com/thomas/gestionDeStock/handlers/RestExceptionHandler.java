package com.thomas.gestionDeStock.handlers;

import com.thomas.gestionDeStock.exception.EntityNotFoundException;
import com.thomas.gestionDeStock.exception.InvalidEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErroDto> handleException(EntityNotFoundException exception, WebRequest webrequest) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;

        ErroDto errorDto = ErroDto.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

                return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler({InvalidEntityException.class})
    public ResponseEntity<ErroDto> handleException(InvalidEntityException exception, WebRequest webrequest) {

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ErroDto errorDto = ErroDto.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }
}
