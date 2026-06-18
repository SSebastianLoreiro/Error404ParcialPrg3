package com.Error404.controllers;

import com.Error404.models.GpsReporte;
import com.Error404.services.AlertaGpsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class GpsController {

    private final AlertaGpsService alertaGpsService;

    public GpsController(AlertaGpsService alertaGpsService) {
        this.alertaGpsService = alertaGpsService;
    }

    @PostMapping("/deduplicar")
    public ResponseEntity<List<GpsReporte>> deduplicar(@RequestBody List<GpsReporte> reportes) {
        return ResponseEntity.ok(alertaGpsService.deduplicarAlertas(reportes));
    }
}
