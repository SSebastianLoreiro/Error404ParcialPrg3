package com.Error404.controllers;

public class CriterioActivoResponse {
    private String mensaje;
    private String criterioActivo;

    public CriterioActivoResponse(String mensaje, String criterioActivo) {
        this.mensaje = mensaje;
        this.criterioActivo = criterioActivo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCriterioActivo() {
        return criterioActivo;
    }
}
