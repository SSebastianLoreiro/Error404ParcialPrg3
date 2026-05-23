package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Usuario {
    private String id;
    private String nombre_completo;

    public Usuario() {
    }

    public Usuario(String id, String nombre_completo) {
        this.id = id;
        this.nombre_completo = nombre_completo;
    }

    /**
     * Calcula el costo que debe pagar el usuario dado el costo base del vehículo.
     * Subclases pueden sobrescribir para aplicar cargos/beneficios.
     */
    public double calcularCosto(double tarifaVehiculo) {
        return tarifaVehiculo;
    }

    /**
     * Tipo de usuario (para logging/decisiones rápidas).
     */
    public String getTipo() {
        return "Usuario";
    }


    
}

class UsuarioRegular extends Usuario {
    private double costoEstandart;

    public UsuarioRegular(String id, String nombre_completo, double costoEstandart) {
        super(id, nombre_completo);
        this.costoEstandart = costoEstandart;
    }

    @Override
    public double calcularCosto(double tarifaVehiculo) {
        return tarifaVehiculo + costoEstandart;
    }

    @Override
    public String getTipo() {
        return "UsuarioRegular";
    }
}

class UsuarioPremium extends Usuario {
    private double costoPremium;

    public UsuarioPremium(String id, String nombre_completo, double costoPremium) {
        super(id, nombre_completo);
        this.costoPremium = costoPremium;
    }

    @Override
    public double calcularCosto(double tarifaVehiculo) {
        // Interpretemos costoPremium como descuento fijo sobre la tarifa base
        return Math.max(0, tarifaVehiculo - costoPremium);
    }

    @Override
    public String getTipo() {
        return "UsuarioPremium";
    }
}
