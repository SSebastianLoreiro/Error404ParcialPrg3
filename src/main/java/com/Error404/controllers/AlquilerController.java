package com.Error404.controllers;

import com.Error404.exception.BateriaInsuficienteException;
import com.Error404.exception.MedioDePagoNoValidoException;
import com.Error404.exception.UsuarioNoEncontradoException;
import com.Error404.exception.VechiculoNoEncontradoException;
import com.Error404.models.ProcesamientoDePagos;
import com.Error404.models.Usuario;
import com.Error404.models.Vehiculo;
import com.Error404.services.EstacionService;
import com.Error404.services.ProcesamientoDePagosService;
import com.Error404.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    public AlquilerController(EstacionService estacionService,
                              UsuarioService usuarioService,
                              ProcesamientoDePagosService pagosService) {
        this.estacionService = estacionService;
        this.usuarioService = usuarioService;
        this.pagosService = pagosService;
    }

    @GetMapping("/desbloquear")
    public ResponseEntity<DesbloqueoResponse> desbloquear(@RequestBody DesbloqueoRequest request) {
        if (request == null || request.getIdUsuario() == null || request.getPatente() == null || request.getMetodoPago() == null) {
            throw new IllegalArgumentException("Debe enviar idUsuario, patente y metodoPago en el cuerpo de la solicitud");
        }

        Optional<Vehiculo> vehiculoOptional = estacionService.buscarVehiculoEnTodasLasEstaciones(request.getPatente());
        Vehiculo vehiculo = vehiculoOptional.orElseThrow(() -> new VechiculoNoEncontradoException("No se encontró el vehículo con patente " + request.getPatente()));

        if (vehiculo.consultarBateria() < NIVEL_MINIMO_BATERIA) {
            throw new BateriaInsuficienteException("El nivel de batería es insuficiente para circular: " + vehiculo.consultarBateria() + "%");
        }

        Usuario usuario = usuarioService.findById(request.getIdUsuario())
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con id " + request.getIdUsuario()));

        double importeFinal = usuario.calcularCosto(vehiculo.calcularTarifa());

        ProcesamientoDePagos.TipoDePago tipoPago = ProcesamientoDePagos.TipoDePago.fromString(request.getMetodoPago());
        if (tipoPago == null) {
            throw new MedioDePagoNoValidoException("Método de pago no válido: " + request.getMetodoPago());
        }

        ProcesamientoDePagos pago = new ProcesamientoDePagos(usuario.getId(), vehiculo.getNumPatente(), tipoPago, importeFinal);
        pagosService.save(pago);

        DesbloqueoResponse response = new DesbloqueoResponse(
                "Vehículo desbloqueado correctamente",
                usuario.getId(),
                vehiculo.getNumPatente(),
                vehiculo.getClass().getSimpleName(),
                importeFinal
        );

        return ResponseEntity.ok(response);
    }
}
