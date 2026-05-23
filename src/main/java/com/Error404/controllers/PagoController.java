package com.Error404.controllers;

import com.Error404.models.ProcesamientoDePagos;
import com.Error404.services.ProcesamientoDePagosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final ProcesamientoDePagosService service;

    public PagoController(ProcesamientoDePagosService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProcesamientoDePagos> listarPagos() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<String> crearPago(@RequestBody ProcesamientoDePagos pago) {
        String id = service.save(pago);
        return ResponseEntity.ok(id);
    }
}
