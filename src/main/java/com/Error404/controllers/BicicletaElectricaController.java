package com.Error404.controllers;

import com.Error404.models.BicicletaElectrica;
import com.Error404.services.BicicletaElectricaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// DTO in same package; explicit import removed

@RestController
@RequestMapping("/api/bicicletas")
public class BicicletaElectricaController {

    private final BicicletaElectricaService service;

    public BicicletaElectricaController(BicicletaElectricaService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehiculoResumenResponse> listar() {
        List<BicicletaElectrica> encontrados = service.findAll();
        List<VehiculoResumenResponse> respuesta = new ArrayList<>();
        for (BicicletaElectrica b : encontrados) {
            respuesta.add(new VehiculoResumenResponse(b.getNumPatente(), b.getClass().getSimpleName(), b.getPorcentajeBateria(), b.getTarifaBase(), b.getEstado().toString()));
        }
        return respuesta;
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoResumenResponse> obtener(@PathVariable String patente) {
        java.util.Optional<BicicletaElectrica> optional = service.findByPatente(patente);
        if (optional.isPresent()) {
            BicicletaElectrica b = optional.get();
            return ResponseEntity.ok(new VehiculoResumenResponse(b.getNumPatente(), b.getClass().getSimpleName(), b.getPorcentajeBateria(), b.getTarifaBase(), b.getEstado().toString()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public VehiculoResumenResponse crear(@RequestBody BicicletaElectrica bicicletaElectrica) {
        BicicletaElectrica saved = service.save(bicicletaElectrica);
        return new VehiculoResumenResponse(saved.getNumPatente(), saved.getClass().getSimpleName(), saved.getPorcentajeBateria(), saved.getTarifaBase(), saved.getEstado().toString());
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        return service.deleteByPatente(patente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
