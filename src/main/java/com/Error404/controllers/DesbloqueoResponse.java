package com.Error404.controllers;

public class DesbloqueoResponse {
    private String mensaje;
    private String idUsuario;
    private String patente;
    private String tipoVehiculo;
    private String faseActual;
    private double montoCobrado;

    public DesbloqueoResponse(String mensaje, String idUsuario, String patente, String tipoVehiculo, String faseActual, double montoCobrado) {
        this.mensaje = mensaje;
        this.idUsuario = idUsuario;
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
        this.faseActual = faseActual;
        this.montoCobrado = montoCobrado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getPatente() {
        return patente;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getFaseActual() {
        return faseActual;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }
}
