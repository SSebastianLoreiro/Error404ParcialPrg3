package com.Error404.models;

public class EnEsperaEstado implements VehiculoEstado {

    @Override
    public String getNombre() {
        return "En Espera";
    }

    @Override
    public VehiculoEstado iniciarViaje(Vehiculo vehiculo) {
        return new EnViajeEstado();
    }

    @Override
    public VehiculoEstado finalizarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede finalizar un viaje cuando el vehículo está en espera.");
    }

    @Override
    public VehiculoEstado enviarAReparacion(Vehiculo vehiculo) {
        return new EnReparacionEstado();
    }

    @Override
    public VehiculoEstado reparar(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo ya está listo para ser retirado.");
    }
}
