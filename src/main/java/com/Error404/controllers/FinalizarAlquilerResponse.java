package com.Error404.controllers;

public class FinalizarAlquilerResponse {
    private String mensaje;
    private String patente;
    private int minutosTranscurridos;
    private double costoFinal;
    private String faseActual;

    public FinalizarAlquilerResponse(String mensaje, String patente, int minutosTranscurridos, double costoFinal, String faseActual) {
        this.mensaje = mensaje;
        this.patente = patente;
        this.minutosTranscurridos = minutosTranscurridos;
        this.costoFinal = costoFinal;
        this.faseActual = faseActual;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPatente() {
        return patente;
    }

    public int getMinutosTranscurridos() {
        return minutosTranscurridos;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    public String getFaseActual() {
        return faseActual;
    }
}
