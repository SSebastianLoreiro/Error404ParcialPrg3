package com.Error404.models;

public interface TarifaStrategy {
    double calcular(int minutos, double tarifaBase);
    String getNombre();
}
