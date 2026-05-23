package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Vehiculo {
    private String numPatente;
    private int porcentajeBateria;
    private final double tarifaBase;

    public Vehiculo(String numPatente, int porcentajeBateria, double tarifaBase) {
        this.numPatente = numPatente;
        this.porcentajeBateria = porcentajeBateria;
        this.tarifaBase = tarifaBase;
    }
}

@Getter
@Setter
@ToString
class Monopatin extends Vehiculo {
    private boolean amortiguacionReforzada;

    public Monopatin(String numPatente, int porcentajeBateria, double tarifaBase, boolean amortiguacionReforzada) {
        super(numPatente, porcentajeBateria, tarifaBase);
        this.amortiguacionReforzada = amortiguacionReforzada;
    }
}

@Getter
@Setter
@ToString
class BicicletaElectrica extends Vehiculo {
    private double tamañoDeCanasto;

    public BicicletaElectrica(String numPatente, int porcentajeBateria, double tarifaBase, double tamañoDeCanasto) {
        super(numPatente, porcentajeBateria, tarifaBase);
        this.tamañoDeCanasto = tamañoDeCanasto;
    }
}