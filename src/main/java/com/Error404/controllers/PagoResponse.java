package com.Error404.controllers;

public class PagoResponse {
    private String id;
    private String usuarioId;
    private String patente;
    private String metodoPago;
    private double monto;

    public PagoResponse(String id, String usuarioId, String patente, String metodoPago, double monto) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.patente = patente;
        this.metodoPago = metodoPago;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getPatente() {
        return patente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public double getMonto() {
        return monto;
    }
}
