package com.Error404.models;

public class EnViajeEstado implements VehiculoEstado {

    @Override
    public String getNombre() {
        return "En Viaje";
    }

    @Override
    public VehiculoEstado iniciarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo ya está en viaje y no puede iniciar otro viaje.");
    }

    @Override
    public VehiculoEstado finalizarViaje(Vehiculo vehiculo) {
        return new EnEsperaEstado();
    }

    @Override
    public VehiculoEstado enviarAReparacion(Vehiculo vehiculo) {
        throw new IllegalStateException("Un vehículo en viaje no puede ser enviado a reparación hasta que finalice el viaje.");
    }

    @Override
    public VehiculoEstado reparar(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede reparar un vehículo que está en viaje.");
    }
}
