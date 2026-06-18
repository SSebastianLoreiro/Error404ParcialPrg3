package com.Error404;

import com.Error404.models.GpsReporte;
import com.Error404.services.AlertaGpsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AlertaGpsServiceTest {

    @Test
    public void testDeduplicarAlertasEliminaDuplicados() {
        AlertaGpsService servicio = new AlertaGpsService();
        List<GpsReporte> reportes = new ArrayList<>();
        reportes.add(new GpsReporte(10.0, 20.0));
        reportes.add(new GpsReporte(10.0, 20.0));
        reportes.add(new GpsReporte(15.0, 25.0));
        reportes.add(new GpsReporte(15.0, 25.0));

        List<GpsReporte> depurados = servicio.deduplicarAlertas(reportes);

        Assertions.assertEquals(2, depurados.size());
        Assertions.assertTrue(depurados.contains(new GpsReporte(10.0, 20.0)));
        Assertions.assertTrue(depurados.contains(new GpsReporte(15.0, 25.0)));
    }
}
