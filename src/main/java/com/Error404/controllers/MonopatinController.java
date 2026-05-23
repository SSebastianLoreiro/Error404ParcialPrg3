package com.Error404.controllers;

import com.Error404.models.Monopatin;
import com.Error404.services.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @GetMapping
    public List<Monopatin> listar() {
        return service.findAll();
    }

    @GetMapping("/{patente}")
    public ResponseEntity<Monopatin> obtener(@PathVariable String patente) {
        return service.findByPatente(patente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Monopatin crear(@RequestBody Monopatin monopatin) {
        return service.save(monopatin);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        return service.deleteByPatente(patente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
