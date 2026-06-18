package com.Error404.controllers;

import com.Error404.exception.BateriaInsuficienteException;
import com.Error404.exception.MedioDePagoNoValidoException;
import com.Error404.exception.UsuarioNoEncontradoException;
import com.Error404.exception.VechiculoNoEncontradoException;
import com.Error404.models.ProcesamientoDePagos;
import com.Error404.models.Usuario;
import com.Error404.models.Vehiculo;
import com.Error404.services.CriterioTarifaService;
import com.Error404.services.EstacionService;
import com.Error404.services.ProcesamientoDePagosService;
import com.Error404.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private static final int NIVEL_MINIMO_BATERIA = 20;

    private final EstacionService estacionService;
    private final UsuarioService usuarioService;
    private final ProcesamientoDePagosService pagosService;
    private final CriterioTarifaService criterioTarifaService;

    public AlquilerController(EstacionService estacionService,
                              UsuarioService usuarioService,
                              ProcesamientoDePagosService pagosService,
                              CriterioTarifaService criterioTarifaService) {
        this.estacionService = estacionService;
        this.usuarioService = usuarioService;
        this.pagosService = pagosService;
        this.criterioTarifaService = criterioTarifaService;
    }

   
    
    @GetMapping("/desbloquear")
    public ResponseEntity<DesbloqueoResponse> desbloquear(@RequestParam("idUsuario") String idUsuario,
                                                          @RequestParam("patente") String patente,
                                                          @RequestParam("metodoPago") String metodoPago) {
        if (idUsuario == null || idUsuario.isEmpty() || patente == null || patente.isEmpty() || metodoPago == null || metodoPago.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar idUsuario, patente y metodoPago como query params");
        }

        Vehiculo vehiculo = estacionService.buscarVehiculoPorPatente(patente)
                .orElseThrow(() -> new VechiculoNoEncontradoException("No se encontró el vehículo con patente " + patente));

        if (vehiculo.consultarBateria() < NIVEL_MINIMO_BATERIA) {
            throw new BateriaInsuficienteException("El nivel de batería es insuficiente para circular: " + vehiculo.consultarBateria() + "%");
        }

        Usuario usuario = usuarioService.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con id " + idUsuario));

        vehiculo.iniciarViaje();
        double importeEstimado = criterioTarifaService.calcularCosto(vehiculo, 1);

        ProcesamientoDePagos.TipoDePago tipoPago = ProcesamientoDePagos.TipoDePago.fromString(metodoPago);
        if (tipoPago == null) {
            throw new MedioDePagoNoValidoException("Método de pago no válido: " + metodoPago);
        }

        ProcesamientoDePagos pago = new ProcesamientoDePagos(usuario.getId(), vehiculo.getNumPatente(), tipoPago, importeEstimado);
        pagosService.save(pago);

        DesbloqueoResponse response = new DesbloqueoResponse(
                "Vehículo desbloqueado correctamente",
                usuario.getId(),
                vehiculo.getNumPatente(),
                vehiculo.getClass().getSimpleName(),
                vehiculo.getEstadoActual(),
                importeEstimado
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/criterio")
    public ResponseEntity<String> establecerCriterio(@RequestParam("tipo") String tipo) {
        criterioTarifaService.establecerCriterio(tipo);
        return ResponseEntity.ok("Criterio activo: " + criterioTarifaService.getCriterioActivo());
    }

    @GetMapping("/finalizar")
    public ResponseEntity<FinalizarAlquilerResponse> finalizar(@RequestParam("idUsuario") String idUsuario,
                                                                 @RequestParam("patente") String patente,
                                                                 @RequestParam("minutos") int minutos) {
        if (idUsuario == null || idUsuario.isEmpty() || patente == null || patente.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar idUsuario, patente y minutos como query params");
        }
        if (minutos < 0) {
            throw new IllegalArgumentException("Los minutos no pueden ser negativos.");
        }

        Vehiculo vehiculo = estacionService.buscarVehiculoPorPatente(patente)
                .orElseThrow(() -> new VechiculoNoEncontradoException("No se encontró el vehículo con patente " + patente));

        if (!"En Viaje".equals(vehiculo.getEstadoActual())) {
            throw new IllegalStateException("El vehículo no está en viaje y no puede finalizarse.");
        }

        Usuario usuario = usuarioService.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con id " + idUsuario));

        double subtotal = criterioTarifaService.calcularCosto(vehiculo, minutos);
        double importeFinal = usuario.calcularCosto(subtotal);
        vehiculo.finalizarViaje();

        ProcesamientoDePagos pago = new ProcesamientoDePagos(usuario.getId(), vehiculo.getNumPatente(), ProcesamientoDePagos.TipoDePago.TARJETA_CREDITO, importeFinal);
        pagosService.save(pago);

        FinalizarAlquilerResponse response = new FinalizarAlquilerResponse(
                "Alquiler finalizado correctamente",
                vehiculo.getNumPatente(),
                minutos,
                importeFinal,
                vehiculo.getEstadoActual()
        );

        return ResponseEntity.ok(response);
    }
}
