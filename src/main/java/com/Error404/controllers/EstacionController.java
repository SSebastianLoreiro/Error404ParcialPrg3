package com.Error404.controllers;

import com.Error404.models.EstacionDeAnclaje;
import com.Error404.models.Vehiculo;
import com.Error404.services.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionService service;

    public EstacionController(EstacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstacionResumenResponse> listar() {
        List<EstacionDeAnclaje> estaciones = service.findAll();
        List<EstacionResumenResponse> respuesta = new ArrayList<>();
        for (EstacionDeAnclaje est : estaciones) {
            List<VehiculoResumenResponse> vehiculos = new ArrayList<>();
            if (est.getVehiculosDisponibles() != null) {
                for (Vehiculo v : est.getVehiculosDisponibles()) {
                    vehiculos.add(new VehiculoResumenResponse(
                            v.getNumPatente(), v.getClass().getSimpleName(), v.getPorcentajeBateria(), v.getTarifaBase(), v.getEstado().toString()
                    ));
                }
            }
            respuesta.add(new EstacionResumenResponse(est.getNombreUnico(), vehiculos));
        }
        return respuesta;
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<EstacionResumenResponse> obtener(@PathVariable String nombre) {
        java.util.Optional<EstacionDeAnclaje> optional = service.findByNombre(nombre);
        if (optional.isPresent()) {
            EstacionDeAnclaje est = optional.get();
            List<VehiculoResumenResponse> vehiculos = new ArrayList<>();
            if (est.getVehiculosDisponibles() != null) {
                for (Vehiculo v : est.getVehiculosDisponibles()) {
                    vehiculos.add(new VehiculoResumenResponse(
                            v.getNumPatente(), v.getClass().getSimpleName(), v.getPorcentajeBateria(), v.getTarifaBase(), v.getEstado().toString()
                    ));
                }
            }
            return ResponseEntity.ok(new EstacionResumenResponse(est.getNombreUnico(), vehiculos));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EstacionResumenResponse crear(@RequestBody EstacionRequest request) {
        EstacionDeAnclaje estacion = new EstacionDeAnclaje(request.getNombreUnico());
        EstacionDeAnclaje saved = service.save(estacion);
        return new EstacionResumenResponse(saved.getNombreUnico(), new ArrayList<>());
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombre) {
        return service.deleteByNombre(nombre) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
