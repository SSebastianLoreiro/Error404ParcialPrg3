package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Monopatin extends Vehiculo {
    private boolean amortiguacionReforzada;

    public Monopatin(String numPatente, int porcentajeBateria, double tarifaBase, boolean amortiguacionReforzada) {
        super(numPatente, porcentajeBateria, tarifaBase);
        this.amortiguacionReforzada = amortiguacionReforzada;
    }

    @Override
    public double calcularTarifa() {
        double extra = amortiguacionReforzada ? 0.15 : 0.0;
        return getTarifaBase() * (1 + extra);
    }

    public void aplicarFrenadoRegenerativo(int porcentajeRecarga) {
        cargarBateria(porcentajeRecarga);
    }
}
