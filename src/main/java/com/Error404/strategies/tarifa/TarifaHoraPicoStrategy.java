package com.Error404.strategies.tarifa;

public class TarifaHoraPicoStrategy implements TarifaStrategy {

    private static final double RECARGO = 0.40;

    @Override
    public double calcular(int minutos, double tarifaBase) {
        double subtotal = minutos * tarifaBase;
        return subtotal + subtotal * RECARGO;
    }

    @Override
    public String getNombre() {
        return "Hora Pico";
    }
}
