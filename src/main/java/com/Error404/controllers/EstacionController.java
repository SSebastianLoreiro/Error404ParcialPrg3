package com.Error404.controllers;

import com.Error404.models.EstacionDeAnclaje;
import com.Error404.models.Vehiculo;
import com.Error404.services.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionService service;

    public EstacionController(EstacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstacionDeAnclaje> listar() {
        return service.findAll();
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<EstacionDeAnclaje> obtener(@PathVariable String nombre) {
        return service.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EstacionDeAnclaje crear(@RequestBody EstacionDeAnclaje estacion) {
        return service.save(estacion);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombre) {
        return service.deleteByNombre(nombre) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
