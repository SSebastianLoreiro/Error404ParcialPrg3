package com.Error404.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Vehiculo implements Comparable<Vehiculo> {
    private String numPatente;
    private int porcentajeBateria;
    private double tarifaBase;
    @Setter(AccessLevel.NONE)
    private VehiculoEstado estado;

    public Vehiculo() {
        this.tarifaBase = 0;
        this.estado = new EnEsperaEstado();
    }

    public Vehiculo(String numPatente, int porcentajeBateria, double tarifaBase) {
        this.numPatente = numPatente;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaBase = tarifaBase;
        this.estado = new EnEsperaEstado();
    }

    public void consumirBateria(int porcentaje) {
        if (porcentaje <= 0) return;
        this.porcentajeBateria = Math.max(0, this.porcentajeBateria - porcentaje);
    }

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

    public void iniciarViaje() {
        setEstado(estado.iniciarViaje(this));
    }

    public void finalizarViaje() {
        setEstado(estado.finalizarViaje(this));
    }

    public void enviarAReparacion() {
        setEstado(estado.enviarAReparacion(this));
    }

    public void reparar() {
        setEstado(estado.reparar(this));
    }

    public String getEstadoActual() {
        if (estado == null) {
            return "Desconocido";
        }
        return estado.getNombre();
    }

    protected void setEstado(VehiculoEstado nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("Estado de vehículo inválido.");
        }
        this.estado = nuevoEstado;
    }

    @Override
    public int compareTo(Vehiculo otra) {
        if (otra == null) {
            return -1;
        }
        return Integer.compare(this.porcentajeBateria, otra.porcentajeBateria);
    }

    public abstract double calcularTarifa();
}
