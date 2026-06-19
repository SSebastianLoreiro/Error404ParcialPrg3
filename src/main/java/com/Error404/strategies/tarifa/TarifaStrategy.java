package com.Error404.strategies.tarifa;

public interface TarifaStrategy {
    double calcular(int minutos, double tarifaBase);
    String getNombre();
}
