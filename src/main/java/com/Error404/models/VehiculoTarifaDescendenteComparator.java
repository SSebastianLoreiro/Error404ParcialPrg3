package com.Error404.models;

import java.util.Comparator;

public class VehiculoTarifaDescendenteComparator implements Comparator<Vehiculo> {

    @Override
    public int compare(Vehiculo v1, Vehiculo v2) {
        if (v1 == null && v2 == null) {
            return 0;
        }
        if (v1 == null) {
            return 1;
        }
        if (v2 == null) {
            return -1;
        }
        return Double.compare(v2.calcularTarifa(), v1.calcularTarifa());
    }
}
