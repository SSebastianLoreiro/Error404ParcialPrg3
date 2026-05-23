package com.Error404.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String mensaje;
    private String nivel;

    public ErrorResponse(String mensaje, String nivel) {
        this.timestamp = LocalDateTime.now();
        this.mensaje = mensaje;
        this.nivel = nivel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getNivel() {
        return nivel;
    }
}
