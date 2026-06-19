package com.Error404.strategies.tarifa;

public class TarifaEstandarStrategy implements TarifaStrategy {

    @Override
    public double calcular(int minutos, double tarifaBase) {
        return minutos * tarifaBase;
    }

    @Override
    public String getNombre() {
        return "Estándar";
    }
}
