package com.Error404.controllers;

import com.Error404.models.Vehiculo;
import com.Error404.models.VehiculoTarifaDescendenteComparator;
import com.Error404.services.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final EstacionService estacionService;

    public VehiculoController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @GetMapping("/prioridad-carga")
    public ResponseEntity<List<VehiculoResumenResponse>> listarPorPrioridadCarga() {
        List<Vehiculo> vehiculos = new ArrayList<>(estacionService.findAllVehiculos());
        Collections.sort(vehiculos);
        List<VehiculoResumenResponse> respuesta = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            respuesta.add(new VehiculoResumenResponse(
                    vehiculo.getNumPatente(),
                    vehiculo.getClass().getSimpleName(),
                    vehiculo.consultarBateria(),
                    vehiculo.getTarifaBase(),
                    vehiculo.getEstadoActual()
            ));
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/tarifa-descendente")
    public ResponseEntity<List<VehiculoResumenResponse>> listarPorTarifaDescendente() {
        List<Vehiculo> vehiculos = new ArrayList<>(estacionService.findAllVehiculos());
        Collections.sort(vehiculos, new VehiculoTarifaDescendenteComparator());
        List<VehiculoResumenResponse> respuesta = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            respuesta.add(new VehiculoResumenResponse(
                    vehiculo.getNumPatente(),
                    vehiculo.getClass().getSimpleName(),
                    vehiculo.consultarBateria(),
                    vehiculo.getTarifaBase(),
                    vehiculo.getEstadoActual()
            ));
        }
        return ResponseEntity.ok(respuesta);
    }
}
