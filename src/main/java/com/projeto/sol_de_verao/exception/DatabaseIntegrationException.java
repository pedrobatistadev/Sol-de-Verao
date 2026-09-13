package com.projeto.sol_de_verao.exception;

import java.time.format.DateTimeFormatter;

public class DatabaseIntegrationException extends RuntimeException{

    public DatabaseIntegrationException(String message) {
        super(message);
    }
}
