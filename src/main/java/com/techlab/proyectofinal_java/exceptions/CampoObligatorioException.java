package com.techlab.proyectofinal_java.exceptions;

public class CampoObligatorioException extends RuntimeException {
  public CampoObligatorioException(String mensaje) {
    super(mensaje);
  }
}