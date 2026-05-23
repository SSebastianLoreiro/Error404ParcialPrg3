package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BicicletaElectrica extends Vehiculo {
    private double tamanoDeCanasto;

    public BicicletaElectrica() {
        super();
    }

    public BicicletaElectrica(String numPatente, int porcentajeBateria, double tarifaBase, double tamanoDeCanasto) {
        super(numPatente, porcentajeBateria, tarifaBase);
        this.tamanoDeCanasto = tamanoDeCanasto;
    }

    @Override
    public double calcularTarifa() {
        double discount = tamanoDeCanasto > 0 ? 0.05 : 0.0;
        return getTarifaBase() * (1 - discount);
    }
}
