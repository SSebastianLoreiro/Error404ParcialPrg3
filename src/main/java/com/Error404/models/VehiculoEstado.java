package com.Error404.models;

public interface VehiculoEstado {
    String getNombre();

    VehiculoEstado iniciarViaje(Vehiculo vehiculo);

    VehiculoEstado finalizarViaje(Vehiculo vehiculo);

    VehiculoEstado enviarAReparacion(Vehiculo vehiculo);

    VehiculoEstado reparar(Vehiculo vehiculo);
}
