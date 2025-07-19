package com.techlab.proyectofinal_java.exceptions;

public class StockNegativoException extends RuntimeException {
    public StockNegativoException(String mensaje) {
        super(mensaje);
    }
}
