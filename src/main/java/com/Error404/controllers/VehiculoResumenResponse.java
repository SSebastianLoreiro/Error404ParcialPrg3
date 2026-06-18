package com.Error404.controllers;

public class VehiculoResumenResponse {
    private String patente;
    private String tipoVehiculo;
    private int porcentajeBateria;
    private double tarifaBase;
    private String estadoActual;

    public VehiculoResumenResponse(String patente, String tipoVehiculo, int porcentajeBateria, double tarifaBase, String estadoActual) {
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaBase = tarifaBase;
        this.estadoActual = estadoActual;
    }

    public String getPatente() {
        return patente;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public int getPorcentajeBateria() {
        return porcentajeBateria;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public String getEstadoActual() {
        return estadoActual;
    }
}
