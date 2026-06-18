package com.Error404.services;

import com.Error404.models.TarifaClimaticaStrategy;
import com.Error404.models.TarifaEstandarStrategy;
import com.Error404.models.TarifaHoraPicoStrategy;
import com.Error404.models.TarifaStrategy;
import com.Error404.models.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public class CriterioTarifaService {

    private TarifaStrategy criterioActivo;

    public CriterioTarifaService() {
        this.criterioActivo = new TarifaEstandarStrategy();
    }

    public double calcularCosto(Vehiculo vehiculo, int minutos) {
        if (vehiculo == null || minutos < 0) {
            throw new IllegalArgumentException("Vehículo o minutos inválidos para calcular tarifa.");
        }
        return criterioActivo.calcular(minutos, vehiculo.calcularTarifa());
    }

    public String getCriterioActivo() {
        return criterioActivo.getNombre();
    }

    public void establecerCriterio(String criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("El criterio de tarifa no puede ser nulo.");
        }
        String nombre = criterio.trim().toUpperCase();
        if ("HORA_PICO".equals(nombre) || "HORAPICO".equals(nombre) || "HORA PICO".equals(nombre)) {
            this.criterioActivo = new TarifaHoraPicoStrategy();
        } else if ("CLIMATICO".equals(nombre) || "CLIMÁTICO".equals(nombre) || "CLIMA".equals(nombre)) {
            this.criterioActivo = new TarifaClimaticaStrategy();
        } else if ("ESTANDAR".equals(nombre) || "ESTÁNDAR".equals(nombre) || "NORMAL".equals(nombre)) {
            this.criterioActivo = new TarifaEstandarStrategy();
        } else {
            throw new IllegalArgumentException("Criterio de tarifa desconocido: " + criterio);
        }
    }
}
