package com.Error404.controllers;

// DTOs are in the same package; explicit imports removed to avoid unused-import errors
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
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

   
    
    @PostMapping("/desbloquear")
    public ResponseEntity<DesbloqueoResponse> desbloquear(@RequestBody DesbloqueoRequest request) {
        if (request == null || request.getIdUsuario() == null || request.getIdUsuario().isEmpty()
                || request.getPatente() == null || request.getPatente().isEmpty()
                || request.getMetodoPago() == null || request.getMetodoPago().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar idUsuario, patente y metodoPago en el cuerpo de la petición");
        }

        Optional<Vehiculo> optVehiculo = estacionService.buscarVehiculoPorPatente(request.getPatente());
        if (optVehiculo.isEmpty()) {
            throw new VechiculoNoEncontradoException("No se encontró el vehículo con patente " + request.getPatente());
        }
        Vehiculo vehiculo = optVehiculo.get();

        if (vehiculo.consultarBateria() < NIVEL_MINIMO_BATERIA) {
            throw new BateriaInsuficienteException("El nivel de batería es insuficiente para circular: " + vehiculo.consultarBateria() + "%");
        }

        Optional<Usuario> optUsuario = usuarioService.findById(request.getIdUsuario());
        if (optUsuario.isEmpty()) {
            throw new UsuarioNoEncontradoException("No se encontró el usuario con id " + request.getIdUsuario());
        }
        Usuario usuario = optUsuario.get();

        vehiculo.iniciarViaje();
        double importeEstimado = criterioTarifaService.calcularCosto(vehiculo, 1);

        ProcesamientoDePagos.TipoDePago tipoPago = ProcesamientoDePagos.TipoDePago.fromString(request.getMetodoPago());
        if (tipoPago == null) {
            throw new MedioDePagoNoValidoException("Método de pago no válido: " + request.getMetodoPago());
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

    @PostMapping("/criterio")
    public ResponseEntity<CriterioActivoResponse> establecerCriterio(@RequestBody CriterioTarifaRequest request) {
        if (request == null || request.getTipo() == null || request.getTipo().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar el tipo de criterio en el cuerpo de la petición");
        }
        criterioTarifaService.establecerCriterio(request.getTipo());
        return ResponseEntity.ok(new CriterioActivoResponse("Criterio activo actualizado", criterioTarifaService.getCriterioActivo()));
    }

    @PostMapping("/finalizar")
    public ResponseEntity<FinalizarAlquilerResponse> finalizar(@RequestBody FinalizarAlquilerRequest request) {
        if (request == null || request.getIdUsuario() == null || request.getIdUsuario().isEmpty()
                || request.getPatente() == null || request.getPatente().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar idUsuario, patente y minutos en el cuerpo de la petición");
        }
        if (request.getMinutos() < 0) {
            throw new IllegalArgumentException("Los minutos no pueden ser negativos.");
        }

        Optional<Vehiculo> optVehiculo = estacionService.buscarVehiculoPorPatente(request.getPatente());
        if (optVehiculo.isEmpty()) {
            throw new VechiculoNoEncontradoException("No se encontró el vehículo con patente " + request.getPatente());
        }
        Vehiculo vehiculo = optVehiculo.get();

        if (!"En Viaje".equals(vehiculo.getEstadoActual())) {
            throw new IllegalStateException("El vehículo no está en viaje y no puede finalizarse.");
        }

        Optional<Usuario> optUsuario = usuarioService.findById(request.getIdUsuario());
        if (optUsuario.isEmpty()) {
            throw new UsuarioNoEncontradoException("No se encontró el usuario con id " + request.getIdUsuario());
        }
        Usuario usuario = optUsuario.get();

        double subtotal = criterioTarifaService.calcularCosto(vehiculo, request.getMinutos());
        double importeFinal = usuario.calcularCosto(subtotal);
        vehiculo.finalizarViaje();

        ProcesamientoDePagos pago = new ProcesamientoDePagos(usuario.getId(), vehiculo.getNumPatente(), ProcesamientoDePagos.TipoDePago.TARJETA_CREDITO, importeFinal);
        pagosService.save(pago);

        FinalizarAlquilerResponse response = new FinalizarAlquilerResponse(
                "Alquiler finalizado correctamente",
                vehiculo.getNumPatente(),
                request.getMinutos(),
                importeFinal,
                vehiculo.getEstadoActual()
        );

        return ResponseEntity.ok(response);
    }
}
