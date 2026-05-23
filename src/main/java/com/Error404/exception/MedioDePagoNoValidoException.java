package com.Error404.exception;

public class MedioDePagoNoValidoException extends RuntimeException {

    public MedioDePagoNoValidoException(String mensaje) {
        super(mensaje);
    }
}
