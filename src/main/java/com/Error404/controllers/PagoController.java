package com.Error404.controllers;

import com.Error404.models.ProcesamientoDePagos;
import com.Error404.services.ProcesamientoDePagosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.Error404.controllers.PagoRequest;
import com.Error404.controllers.PagoResponse;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final ProcesamientoDePagosService service;

    public PagoController(ProcesamientoDePagosService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagoResponse> listarPagos() {
        List<ProcesamientoDePagos> pagos = service.findAll();
        List<PagoResponse> respuesta = new ArrayList<>();
        for (ProcesamientoDePagos p : pagos) {
            String metodo = p.getMetodoPago() == null ? null : p.getMetodoPago().name();
            respuesta.add(new PagoResponse(null, p.getUsuarioId(), p.getPatente(), metodo, p.getMonto()));
        }
        return respuesta;
    }

    @PostMapping
    public ResponseEntity<String> crearPago(@RequestBody PagoRequest req) {
        ProcesamientoDePagos p = new ProcesamientoDePagos();
        p.setUsuarioId(req.getUsuarioId());
        p.setPatente(req.getPatente());
        p.setMonto(req.getMonto());
        if (req.getMetodoPago() != null) {
            p.setMetodoPago(ProcesamientoDePagos.TipoDePago.fromString(req.getMetodoPago()));
        }
        String id = service.save(p);
        return ResponseEntity.ok(id);
    }
}
