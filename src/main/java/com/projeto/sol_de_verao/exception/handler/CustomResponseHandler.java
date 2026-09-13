package com.projeto.sol_de_verao.exception.handler;

import com.projeto.sol_de_verao.exception.DatabaseIntegrationException;
import com.projeto.sol_de_verao.exception.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomResponseHandler {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handlerAllException(Exception ex, WebRequest request) {
        String data = sdf.format(new Date());
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),request.getDescription(false), data);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String data = sdf.format(new Date());
        String message = "The CPF is already in use. Please provide a different CPF.";
        ExceptionResponse response = new ExceptionResponse(message,request.getDescription(false), data);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DatabaseIntegrationException.class)
    public final ResponseEntity<ExceptionResponse> handlerForeignKeyException(Exception ex, WebRequest request) {
        String data = sdf.format(new Date());
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),request.getDescription(false), data);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handlerArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String data = sdf.format(new Date());
        ExceptionResponse response = new ExceptionResponse(ex.getFieldError().getDefaultMessage(), request.getDescription(false), data);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
