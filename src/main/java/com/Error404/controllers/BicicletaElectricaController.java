package com.Error404.controllers;

import com.Error404.models.BicicletaElectrica;
import com.Error404.services.BicicletaElectricaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bicicletas")
public class BicicletaElectricaController {

    private final BicicletaElectricaService service;

    public BicicletaElectricaController(BicicletaElectricaService service) {
        this.service = service;
    }

    @GetMapping
    public List<BicicletaElectrica> listar() {
        return service.findAll();
    }

    @GetMapping("/{patente}")
    public ResponseEntity<BicicletaElectrica> obtener(@PathVariable String patente) {
        return service.findByPatente(patente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BicicletaElectrica crear(@RequestBody BicicletaElectrica bicicletaElectrica) {
        return service.save(bicicletaElectrica);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        return service.deleteByPatente(patente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
