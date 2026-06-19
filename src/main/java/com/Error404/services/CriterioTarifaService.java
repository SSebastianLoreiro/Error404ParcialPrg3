package com.Error404.services;

import com.Error404.models.Vehiculo;
import com.Error404.strategies.tarifa.TarifaStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CriterioTarifaService {

    private final Map<String, TarifaStrategy> criterios;
    private TarifaStrategy criterioActivo;

    public CriterioTarifaService(Map<String, TarifaStrategy> criterios) {
        this.criterios = criterios;
        TarifaStrategy primera = null;
        for (TarifaStrategy t : criterios.values()) {
            primera = t;
            break;
        }
        if (primera == null) {
            throw new IllegalStateException("No hay criterios de tarifa registrados.");
        }
        this.criterioActivo = criterios.getOrDefault("tarifaEstandarStrategy", primera);
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
        String clave = normalizarCriterio(criterio);
        TarifaStrategy estrategia = seleccionarEstrategia(clave);
        if (estrategia == null) {
            throw new IllegalArgumentException("Criterio de tarifa desconocido: " + criterio);
        }
        this.criterioActivo = estrategia;
    }

    private String normalizarCriterio(String criterio) {
        return criterio
                .trim()
                .toUpperCase()
                .replaceAll("\\s+", "_")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");
    }

    private TarifaStrategy seleccionarEstrategia(String clave) {
        if (clave.contains("HORA") && clave.contains("PICO")) {
            return criterios.get("tarifaHoraPicoStrategy");
        }
        if (clave.contains("CLIMA")) {
            return criterios.get("tarifaClimaticaStrategy");
        }
        if (clave.contains("ESTANDAR") || clave.contains("NORMAL")) {
            return criterios.get("tarifaEstandarStrategy");
        }
        return null;
    }
}
