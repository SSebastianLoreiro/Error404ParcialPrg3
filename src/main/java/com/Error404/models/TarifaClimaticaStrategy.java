package com.Error404.models;

public class TarifaClimaticaStrategy implements TarifaStrategy {

    private static final double RECARGO_PLANO = 150.0;

    @Override
    public double calcular(int minutos, double tarifaBase) {
        return minutos * tarifaBase + RECARGO_PLANO;
    }

    @Override
    public String getNombre() {
        return "Climático";
    }
}
