package com.Error404.services;

import com.Error404.models.GpsReporte;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlertaGpsService {

    public List<GpsReporte> deduplicarAlertas(List<GpsReporte> reportes) {
        if (reportes == null) {
            return new ArrayList<>();
        }
        Set<GpsReporte> vistos = new HashSet<>();
        List<GpsReporte> depurados = new ArrayList<>();
        for (GpsReporte reporte : reportes) {
            if (reporte == null) {
                continue;
            }
            if (!vistos.contains(reporte)) {
                vistos.add(reporte);
                depurados.add(reporte);
            }
        }
        return depurados;
    }
}
