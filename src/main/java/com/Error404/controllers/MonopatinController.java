package com.Error404.controllers;

import com.Error404.models.Monopatin;
import com.Error404.services.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// DTO in same package; explicit import removed

@RestController
@RequestMapping("/api/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehiculoResumenResponse> listar() {
        List<Monopatin> encontrados = service.findAll();
        List<VehiculoResumenResponse> respuesta = new ArrayList<>();
        for (Monopatin m : encontrados) {
            respuesta.add(new VehiculoResumenResponse(m.getNumPatente(), m.getClass().getSimpleName(), m.getPorcentajeBateria(), m.getTarifaBase(), m.getEstado().toString()));
        }
        return respuesta;
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoResumenResponse> obtener(@PathVariable String patente) {
        java.util.Optional<Monopatin> optional = service.findByPatente(patente);
        if (optional.isPresent()) {
            Monopatin m = optional.get();
            return ResponseEntity.ok(new VehiculoResumenResponse(m.getNumPatente(), m.getClass().getSimpleName(), m.getPorcentajeBateria(), m.getTarifaBase(), m.getEstado().toString()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public VehiculoResumenResponse crear(@RequestBody Monopatin monopatin) {
        Monopatin saved = service.save(monopatin);
        return new VehiculoResumenResponse(saved.getNumPatente(), saved.getClass().getSimpleName(), saved.getPorcentajeBateria(), saved.getTarifaBase(), saved.getEstado().toString());
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        return service.deleteByPatente(patente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
