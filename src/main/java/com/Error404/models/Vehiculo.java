package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Vehiculo {
    private String numPatente;
    private int porcentajeBateria;
    private double tarifaBase;

    public Vehiculo() {
        this.tarifaBase = 0;
    }

    public Vehiculo(String numPatente, int porcentajeBateria, double tarifaBase) {
        this.numPatente = numPatente;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaBase = tarifaBase;
    }

    public void consumirBateria(int porcentaje) {
        if (porcentaje <= 0) return;
        this.porcentajeBateria = Math.max(0, this.porcentajeBateria - porcentaje);
    }


    // Método para cargar batería, asegurando que no exceda el 100%
    public void cargarBateria(int porcentaje) {
        if (porcentaje <= 0) return;
        this.porcentajeBateria = Math.min(100, this.porcentajeBateria + porcentaje);
    }

    public int consultarBateria() {
        return porcentajeBateria;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public abstract double calcularTarifa();
}
