package com.Error404.controllers;

import java.util.List;

public class EstacionResumenResponse {
    private String nombreUnico;
    private List<VehiculoResumenResponse> vehiculosDisponibles;

    public EstacionResumenResponse(String nombreUnico, List<VehiculoResumenResponse> vehiculosDisponibles) {
        this.nombreUnico = nombreUnico;
        this.vehiculosDisponibles = vehiculosDisponibles;
    }

    public String getNombreUnico() {
        return nombreUnico;
    }

    public List<VehiculoResumenResponse> getVehiculosDisponibles() {
        return vehiculosDisponibles;
    }
}
