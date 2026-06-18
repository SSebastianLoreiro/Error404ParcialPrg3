package com.Error404.models;

public class EnReparacionEstado implements VehiculoEstado {

    @Override
    public String getNombre() {
        return "En Reparación";
    }

    @Override
    public VehiculoEstado iniciarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo está en reparación y no puede iniciar un viaje.");
    }

    @Override
    public VehiculoEstado finalizarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede finalizar un viaje porque el vehículo está en reparación.");
    }

    @Override
    public VehiculoEstado enviarAReparacion(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo ya está en reparación.");
    }

    @Override
    public VehiculoEstado reparar(Vehiculo vehiculo) {
        return new EnEsperaEstado();
    }
}
